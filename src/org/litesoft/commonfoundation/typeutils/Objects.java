// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;

import java.util.*;

public class Objects {
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final String NOT_ALLOWED_TO_BE_NULL = ": Not allowed to be null";

    public static void assertNull( String pObjectName, Object pToBeAssert )
            throws IllegalArgumentException {
        if ( pToBeAssert != null ) {
            throw new IllegalArgumentException( pObjectName + ": Expected null, but was'" + pToBeAssert + "'" );
        }
    }

    public static <T> T assertNotNull( String pObjectName, T pToBeAssert )
            throws IllegalArgumentException {
        if ( pToBeAssert == null ) {
            throw new IllegalArgumentException( pObjectName + NOT_ALLOWED_TO_BE_NULL );
        }
        return pToBeAssert;
    }

    public static void assertEqual( String pObjectName, Object pExpected, Object pActual )
            throws IllegalArgumentException {
        if ( null == pExpected && null == pActual ) {
            return;
        }
        if ( null != pExpected && !pExpected.equals( pActual ) ) {
            throw new IllegalArgumentException( pObjectName + ": Expected '" + pExpected + "', but was '" + pActual + "'" );
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean areNonArraysEqual( Object pThis, Object pThat ) {
        if ( pThis == pThat ) // Same or both null
        {
            return true;
        }
        // Both CAN'T be null
        return (pThis != null) ? pThis.equals( pThat ) : pThat.equals( pThis );
    }

    public static <T> T deNull( T pToCheck, T pDefault ) {
        return (pToCheck != null) ? pToCheck : pDefault;
    }

    public static Object[] appendObjectArrays( Object[] pArray1, Object[] pArray2 ) {
        if ( isNullOrEmpty( pArray2 ) ) {
            return pArray1;
        }
        if ( isNullOrEmpty( pArray1 ) ) {
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

    public static boolean isNotNullOrEmpty( Object[] pArrayToCheck ) {
        return ((pArrayToCheck != null) && (pArrayToCheck.length != 0));
    }

    public static boolean isNullOrEmpty( Object[] pArrayToCheck ) {
        return ((pArrayToCheck == null) || (pArrayToCheck.length == 0));
    }

    public static boolean areArraysEqual( Object[] pThis, Object[] pThat ) {
        if ( pThis == pThat ) // handles if both are null
        {
            return true;
        }
        if ( (pThis != null) && (pThat != null) && (pThis.length == pThat.length) ) {
            for ( int i = pThis.length; --i >= 0; ) {
                if ( !areEqual( pThis[i], pThat[i] ) ) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static void assertElementsNotNull( String pErrorMessage, Object[] pArrayToAssert )
            throws IllegalArgumentException {
        for ( int i = pArrayToAssert.length; --i >= 0; ) {
            if ( pArrayToAssert[i] == null ) {
                Strings.errorNullOrEmpty( pErrorMessage, "Object[" + i + "]" );
            }
        }
    }

    public static void assertNotNullAndElementsNotNull( String pErrorMessage, Object[] pArrayToAssert )
            throws IllegalArgumentException {
        if ( pArrayToAssert == null ) {
            Strings.errorNullOrEmpty( pErrorMessage, "Object[]" );
        }
        assertElementsNotNull( pErrorMessage, pArrayToAssert );
    }

    public static void assertNotNullNotEmptyAndElementsNotNull( String pErrorMessage, Object[] pArrayToAssert )
            throws IllegalArgumentException {
        assertNotNullNotEmpty( pErrorMessage, pArrayToAssert );
        assertElementsNotNull( pErrorMessage, pArrayToAssert );
    }

    public static void assertNotNullNotEmpty( String pErrorMessage, Object[] pArrayToAssert )
            throws IllegalArgumentException {
        if ( isNullOrEmpty( pArrayToAssert ) ) {
            Strings.errorNullOrEmpty( pErrorMessage, "Object[]" );
        }
    }

    public static boolean isNotNull( Object pToCheck ) {
        return (pToCheck != null);
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
             isNotNullOrEmpty( pFrom ) && (pFromIndex < pFrom.length) && //
             isNotNullOrEmpty( pTo ) && (pToIndex < pTo.length) ) {
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

    public static boolean areEqual( Object pThis, Object pThat ) {
        if ( areNonArraysEqual( pThis, pThat ) ) {
            return true;
        }
        // Both CAN'T be null
        if ( (pThis instanceof Object[]) && (pThat instanceof Object[]) ) {
            return areArraysEqual( (Object[]) pThis, (Object[]) pThat );
        }
        return (pThis instanceof int[]) && (pThat instanceof int[]) && Integers.areArraysEqual( (int[]) pThis, (int[]) pThat );
    }

    public static String deNullToString( Object value, Object defaultValue ) {
        return Strings.deNull( noEmptyToString( value ), noEmptyToString( defaultValue ) );
    }

    public static String noEmptyToString( Object value ) {
        return Strings.noEmpty( Strings.nullOKtoString( value ) );
    }

    public static String combine( String pSeparator, List<?> pObjects ) {
        return combine( pSeparator, pObjects.toArray() );
    }

    public static String combine( String pSeparator, Object... pObjects ) {
        if ( isNullOrEmpty( pObjects ) ) {
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
            value = Cast.it( Strings.assertNotNullNotEmpty( what, value.toString() ) );
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
        return Strings.noEmpty( (pObject != null) ? pObject.toString() : null );
    }

    public static String toString( Object pObject ) {
        if ( pObject != null ) {
            String rv = pObject.toString();
            if ( rv != null ) {
                return rv;
            }
        }
        return null;
    }

    public static boolean isOneOf( Object pToFind, Object[] pToSearch ) {
        if ( pToSearch != null ) {
            for ( int i = pToSearch.length; --i >= 0; ) {
                if ( areEqual( pToFind, pToSearch[i] ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> T assertOneOf( T pToFind, T[] pToSearch ) {
        if ( isOneOf( pToFind, pToSearch ) ) {
            return pToFind;
        }
        throw new IllegalArgumentException( "Expected one of (" + optionsToString( pToSearch ) + "), but was '" + pToFind + "'" );
    }

    public static String optionsToString( Object[] pObjects ) {
        if ( pObjects == null ) {
            return "No Options Available";
        }
        StringBuilder sb = new StringBuilder( "Valid Options are" );
        String prefix = ": ";
        //noinspection ForLoopReplaceableByForEach
        for ( int i = 0; i < pObjects.length; i++ ) {
            sb.append( prefix ).append( pObjects[i] );
            prefix = ", ";
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

    public static boolean areNotNull( Object... pToChecks ) {
        if ( pToChecks == null ) {
            return false;
        }
        for ( Object toCheck : pToChecks ) {
            if ( toCheck == null ) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasText( Object pValue ) {
        return (pValue != null) && (pValue.toString().trim().length() != 0);
    }
}
