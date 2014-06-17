// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;

import java.util.*;

public class Objects<ArrayType> {
    public static final Object[] EMPTY_ARRAY = new Object[0];

    public static final String OBJECT_CLASS_NAME = Object.class.getName();

    private final int mArrayLength;
    private final ArrayType[] mArray;

    private Objects( int pArrayLength, ArrayType[] pArray ) {
        mArrayLength = pArrayLength;
        mArray = pArray;
    }

    public boolean hasArray() {
        return (mArrayLength == -1);
    }

    public int getArrayLength() {
        return mArrayLength;
    }

    public ArrayType[] getArray() {
        return mArray;
    }

    // TODO: ||||||||||||||||||||||||||||||||||||||||||||||||||||||| :ODOT \\

    public static <T> Objects<T> prepAppend( T[] pCurrent, T[] pAdditional ) {
        int zAddLength = length( pAdditional );
        if ( (pCurrent != null) && (0 == zAddLength) ) {
            return new Objects<T>( -1, pCurrent );
        }
        int zCurLength = length( pCurrent );
        if ( (pAdditional != null) && (0 == zCurLength) ) {
            return new Objects<T>( -1, pAdditional );
        }
        return new Objects<T>( zCurLength + zAddLength, null );
    }

    public static int length( Object[] pArray ) {
        return (pArray == null) ? 0 : pArray.length;
    }

    public static <T> T[] appendTo( T[] pTo, T[] pCurrent, T[] pAdditional ) {
        Confirm.isNotNull( "To", pTo );
        int zCurLength = length( pCurrent );
        int zAddLength = length( pAdditional );
        if ( pTo.length < (zCurLength + zAddLength) ) {
            throw new IllegalArgumentException( "'To' array too short" );
        }
        if ( zCurLength != 0 ) {
            System.arraycopy( pCurrent, 0, pTo, 0, zCurLength );
        }
        if ( zAddLength != 0 ) {
            System.arraycopy( pAdditional, 0, pTo, zCurLength, zAddLength );
        }
        return pTo;
    }

    public static RuntimeException nullValueException( String pWhat ) {
        return new IllegalArgumentException( pWhat + " Not allowed to be null!" );
    }

    public static boolean areNonArraysEqual( Object pThis, Object pThat ) {
        return (pThis == pThat) || ((pThis != null) && pThis.equals( pThat ));
    }

    public static boolean isNullOrEmpty( Object[] pArrayToCheck ) {
        return ((pArrayToCheck == null) || (pArrayToCheck.length == 0));
    }

    public static <T> boolean oneOf( T toTest, T... options ) {
        if ( (toTest != null) && (options != null) ) {
            for ( T option : options ) {
                if ( toTest.equals( option ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> T oneOfToString( String toTest, T... options ) {
        if ( (toTest != null) && (options != null) ) {
            for ( T option : options ) {
                if ( option != null && toTest.equals( option.toString() ) ) {
                    return option;
                }
            }
        }
        return null;
    }

    public static <T> T oneOfToStringIgnoreCase( String toTest, T... options ) {
        if ( (toTest != null) && (options != null) ) {
            for ( T option : options ) {
                if ( (option != null) && toTest.equalsIgnoreCase( option.toString() ) ) {
                    return option;
                }
            }
        }
        return null;
    }

    public static String toString( Object pObject ) {
        return (pObject != null) ? pObject.toString() : null;
    }

    public static int hashCodeFor( Object pObject ) {
        return (pObject != null) ? pObject.hashCode() : 0;
    }

    // TODO: vvvvvvvvvvvvvvvvvvvvvvvv  NEW  vvvvvvvvvvvvvvvvvvvvvvvv :ODOT \\

    public static final String NOT_ALLOWED_TO_BE_NULL = ": Not allowed to be null";

    public static Object[] appendObjectArrays( Object[] pArray1, Object[] pArray2 ) {
        if ( Currently.isNullOrEmpty( pArray2 ) ) {
            return pArray1;
        }
        if ( Currently.isNullOrEmpty( pArray1 ) ) {
            return pArray2;
        }
        Object[] joined = new Object[pArray1.length + pArray2.length];
        System.arraycopy( pArray1, 0, joined, 0, pArray1.length );
        System.arraycopy( pArray2, 0, joined, pArray1.length, pArray2.length );
        return joined;
    }

    public static Object[] prependObject( Object pNewFirst, Object[] pTheRest ) {
        return appendObjectArrays( new Object[]{pNewFirst}, pTheRest );
    }

    public static Object[] appendObject( Object[] pCurArray, Object pNewLast ) {
        return appendObjectArrays( pCurArray, new Object[]{pNewLast} );
    }

    public static int getNonNullEntryCount( Object[] pArrayToCheck ) {
        int rv = 0;
        if ( pArrayToCheck != null ) {
            for ( int i = pArrayToCheck.length; --i >= 0; ) {
                if ( pArrayToCheck[i] != null ) {
                    rv++;
                }
            }
        }
        return rv;
    }

    public static boolean hasEntries( Object[] pArrayToCheck ) {
        if ( pArrayToCheck != null ) {
            for ( int i = pArrayToCheck.length; --i >= 0; ) {
                if ( pArrayToCheck[i] != null ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param pCount     - < 1 means copy nothing
     * @param pTo        - is NOT extended
     * @param pFromIndex - 0 based, < 0 indicates from end (eg -1 == Last)
     * @param pToIndex   - 0 based, < 0 indicates from end (eg -1 == Last)
     *
     * @return pTo
     */
    public static Object[] copySubArrayTo( int pCount, //
                                           Object[] pFrom, int pFromIndex, //
                                           Object[] pTo, int pToIndex ) {
        if ( (pCount > 0) && //
             Currently.isNotNullOrEmpty( pFrom ) && (pFromIndex < pFrom.length) && //
             Currently.isNotNullOrEmpty( pTo ) && (pToIndex < pTo.length) ) {
            pCount = lesserOf( pCount, pTo, pToIndex = unNegateIndex( pTo, pToIndex ) );
            pCount = lesserOf( pCount, pFrom, pFromIndex = unNegateIndex( pFrom, pFromIndex ) );
            while ( pCount-- > 0 ) {
                pTo[pToIndex++] = pFrom[pFromIndex++];
            }
        }
        return pTo;
    }

    private static int lesserOf( int pCount, Object[] pObjects, int pIndex ) {
        if ( pCount > 0 ) {
            int zMoveLen = pObjects.length - pIndex;
            if ( zMoveLen < pCount ) {
                return zMoveLen;
            }
        }
        return pCount;
    }

    private static int unNegateIndex( Object[] pFrom, int pFromIndex ) {
        if ( pFromIndex < 0 ) {
            if ( (pFromIndex = pFrom.length - pFromIndex) < 0 ) {
                pFromIndex = 0;
            }
        }
        return pFromIndex;
    }

    /**
     * @param pTo      - is NOT extended
     * @param pToIndex - 0 based, < 0 indicates from end (eg -1 == Last)
     *
     * @return pTo
     */
    public static Object[] copyArrayTo( Object[] pFrom, Object[] pTo, int pToIndex ) {
        if ( (pFrom != null) && (pTo != null) ) {
            copySubArrayTo( Math.min( pFrom.length, pTo.length - pToIndex ), pFrom, 0, pTo, pToIndex );
        }
        return pTo;
    }

    /**
     * @param pTo - is NOT extended
     *
     * @return pTo
     */
    public static Object[] copyArrayTo( Object[] pFrom, Object[] pTo ) {
        if ( (pFrom != null) && (pTo != null) ) {
            copySubArrayTo( Math.min( pFrom.length, pTo.length ), pFrom, 0, pTo, 0 );
        }
        return pTo;
    }

    public static boolean isEmptyString( Object pObject ) {
        return pObject == null || ((pObject instanceof String) && Strings.isBlank( (String) pObject ));
    }

    public static boolean areBothEmptyStrings( Object pThis, Object pThat ) {
        return isEmptyString( pThis ) && isEmptyString( pThat );
    }

    public static boolean areBothBooleanNotTrue( Object pThis, Object pThat ) {
        return Booleans.isBooleanNotTrue( pThis ) && Booleans.isBooleanNotTrue( pThat );
    }

    public static boolean isNullEquivalent( Object pObject ) {
        return isEmptyString( pObject ) || Booleans.isBooleanNotTrue( pObject );
    }

    public static boolean areBothNullEquivalent( Object pThis, Object pThat ) {
        return isNullEquivalent( pThis ) && isNullEquivalent( pThat );
    }

    public static String deNullToString( Object value, Object defaultValue ) {
        return ConstrainTo.firstNonNull( noEmptyToString( value ), noEmptyToString( defaultValue ) );
    }

    public static String noEmptyToString( Object value ) {
        return ConstrainTo.significantOrNull( Strings.nullOKtoString( value ) );
    }

    public static String combine( String pSeparator, List<?> pObjects ) {
        return combine( pSeparator, pObjects.toArray() );
    }

    public static String combine( String pSeparator, Object... pObjects ) {
        if ( Currently.isNullOrEmpty( pObjects ) ) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        sb.append( pObjects[0] );

        for ( int i = 1; i < pObjects.length; i++ ) {
            sb.append( pSeparator );
            sb.append( pObjects[i] );
        }
        return sb.toString();
    }

    /**
     * Assert that "value" is not null or when converted to a String is not
     * empty and then return resulting "value" trimmed.
     *
     * @param what  "field" was the problem on
     * @param value to check
     *
     * @return value converted to a String minus any leading and trailing spaces
     */
    public static String assertNoEmptyToString( String what, Object value ) {
        String strValue = noEmptyToString( value );
        if ( strValue == null ) {
            Strings.errorNullOrEmpty( what, String.valueOf( ClassName.simple( value ) ) );
        }
        return strValue;
    }

    /**
     * Assert that "value" is not null or empty (if it is a String) and return
     * "value" trimmed (if it is a String).
     *
     * @param what  "field" was the problem on
     * @param value to check
     *
     * @return value minus any leading and trailing spaces
     */
    public static <T> T assertNotNullOrEmpty( String what, T value ) {
        if ( value == null ) {
            Strings.errorNullOrEmpty( what, null );
        }
        if ( value instanceof String ) {
            value = Cast.it( Confirm.significant( what, value.toString() ) );
        }
        return value;
    }

    public static String toStringNullToEmpty( Object pObject ) {
        if ( pObject != null ) {
            String rv = pObject.toString();
            if ( rv != null ) {
                return rv;
            }
        }
        return "";
    }

    public static String toStringOrNull( Object pObject ) {
        return ConstrainTo.significantOrNull( (pObject != null) ? pObject.toString() : null );
    }

    /**
     * Not used where T is itself an array!
     */
    public static <T> boolean isOneOf( T pToFind, T... pToSearch ) {
        if ( pToSearch != null ) {
            for ( int i = pToSearch.length; --i >= 0; ) {
                if ( Currently.areEqual( pToFind, pToSearch[i] ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Not used where T is itself an array!
     */
    public static <T> T assertOneOf( T pToFind, T... pToSearch ) {
        if ( isOneOf( pToFind, pToSearch ) ) {
            return pToFind;
        }
        throw new IllegalArgumentException( "Expected one of (" + optionsToString( pToSearch ) + "), but was '" + pToFind + "'" );
    }

    public static String optionsToString( Object[] pObjects ) {
        List<Object> zObjects = ConstrainTo.notNullImmutableList( pObjects );
        if ( zObjects.isEmpty() ) {
            return "None Available";
        }
        StringBuilder sb = new StringBuilder();
        sb.append( zObjects.get( 0 ) );
        for ( int i = 1; i < pObjects.length; i++ ) {
            sb.append( ", " ).append( pObjects[i] );
        }
        return sb.toString();
    }

    public static String toString( Object[] pObjects ) {
        if ( pObjects == null ) {
            return "null";
        }
        return "<" + pObjects.length + ':' + toString( pObjects, "," ) + '>';
    }

    public static String toString( Object[] pObjects, String separator ) {
        if ( pObjects == null ) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        if ( pObjects.length > 0 ) {
            sb.append( pObjects[0] );
            for ( int i = 1; i < pObjects.length; i++ ) {
                sb.append( separator ).append( pObjects[i] );
            }
        }
        return sb.toString();
    }

    public static String padIt( int pMinDesiredLength, Object pIt ) {
        return Strings.padIt( pMinDesiredLength, "" + pIt );
    }

    public static String iTpad( Object pIt, int pMinDesiredLength ) {
        return Strings.iTpad( "" + pIt, pMinDesiredLength );
    }

    public static boolean hasText( Object pValue ) {
        return (pValue != null) && (pValue.toString().trim().length() != 0);
    }

    // TODO: ^^^^^^^^^^^^^^^^^^^^^^^^  NEW  ^^^^^^^^^^^^^^^^^^^^^^^^ :ODOT \\
}
