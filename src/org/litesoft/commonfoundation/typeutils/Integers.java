// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;

public class Integers extends Numerics {
    public static final int ZERO = 0;
    public static final Integer OBJECT_ZERO = ZERO;
    public static final int[] PRIMITIVE_EMPTY_ARRAY = new int[0];
    public static final Integer[] EMPTY_ARRAY = new Integer[0];
    public static final TypeTransformer<Integer> TYPE_TRANSFORMER = new TypeTransformer<Integer>() {
        @Override
        public Integer transformNonNull( Object pObject ) {
            return Integers.toInteger( pObject );
        }
    };

    public static Integer toInteger( Object pObject ) {
        if ( pObject instanceof Integer ) {
            return Cast.it( pObject );
        }
        if ( pObject instanceof Number ) {
            return ((Number) pObject).intValue();
        }
        if ( pObject != null ) {
            String zString = ConstrainTo.significantOrNull( pObject.toString() );
            if ( zString != null ) {
                try {
                    return Integer.valueOf( zString );
                }
                catch ( NumberFormatException e ) {
                    // Fall Thru
                }
            }
        }
        return null;
    }

    public static String zeroPadIt( int pMinDesiredLength, int pIt ) {
        String rv = String.valueOf( pIt );
        while ( rv.length() < pMinDesiredLength ) {
            rv = "0" + rv;
        }
        return rv;
    }

    public static String toStringPadZero( int pValue, int pMinLength ) {
        boolean zNegative = (pValue < 0);
        String zStr = Strings.padLeft( '0', Integer.toString( Math.abs( pValue ) ), pMinLength );
        return zNegative ? "-" + zStr : zStr;
    }

    public static int constrainBetween( int pMin, int pMax, int pValue ) {
        return (pValue < pMin) ? pMin : ((pMax < pValue) ? pMax : pValue);
    }

    public static int roundUp( double pValue ) {
        Long zValue = Math.round( pValue + 0.5 );
        return zValue.intValue();
    }

    // TODO: ||||||||||||||||||||||||||||||||||||||||||||||||||||||| :ODOT \\

    public static Integer assertOptionalLength( String pWhat, Integer pLength ) {
        return (pLength == null) ? null : assertLength( pWhat, pLength );
    }

    public static Integer assertOptionalLength( String pWhat, Integer pLength, int pMinLength ) {
        return (pLength == null) ? null : assertLength( pWhat, pLength, pMinLength );
    }

    public static int assertPositive( String pWhat, int pInt ) {
        return assertAtLeast( pWhat, pInt, 1 );
    }

    public static int assertLength( String pWhat, int pLength ) {
        return assertAtLeast( pWhat, pLength, 1 );
    }

    public static int assertLength( String pWhat, int pLength, int pMinLength ) {
        return assertAtLeast( pWhat, pLength, pMinLength );
    }

    public static int assertAtLeast( String pWhat, int pInt, int pAtLeast ) {
        if ( pInt < pAtLeast ) {
            throw new IllegalArgumentException( pWhat + " (" + pInt + ") Must be at least " + pAtLeast );
        }
        return pInt;
    }

    // TODO: vvvvvvvvvvvvvvvvvvvvvvvv  NEW  vvvvvvvvvvvvvvvvvvvvvvvv :ODOT \\

    public static Integer assertNonNegative( String pName, Integer pValue )
            throws IllegalArgumentException {
        if ( pValue != null ) {
            assertNonNegative( pName, pValue.intValue() );
        }
        return pValue;
    }

    public static int assertNonNegative( String pName, int pValue )
            throws IllegalArgumentException {
        if ( pValue < 0 ) {
            throw new IllegalArgumentException( pName + CANNOT_BE_NEGATIVE + ", but was: " + pValue );
        }
        return pValue;
    }

    public static int assertFromThru( String pName, int pValue, int pFrom, int pThru )
            throws IllegalArgumentException {
        if ( (pValue < pFrom) || (pThru < pValue) ) {
            throw new IllegalArgumentException( pName + "must be (" + pFrom + " <= x <= " + pThru + "), but was: " + pValue );
        }
        return pValue;
    }

    public static void assertNotEqual( String pObjectName, int pNotExpected, int pActual )
            throws IllegalArgumentException {
        if ( pNotExpected == pActual ) {
            throw new IllegalArgumentException( pObjectName + ": '" + pNotExpected + "' Not allowed" );
        }
    }

    public static void assertEquals( int pExpected, int pActual ) {
        assertEquals( "Values Mismatch", pExpected, pActual );
    }

    public static void assertEquals( String pWhat, int pExpected, int pActual ) {
        if ( pExpected != pActual ) {
            throw new IllegalStateException( pWhat + "\n" +
                                             "    Expected: " + pExpected + "\n" +
                                             "     but was: " + pActual );
        }
    }

    public static String padIt( int pMinDesiredLength, int pIt ) {
        return Strings.padIt( pMinDesiredLength, "" + pIt );
    }

    public static String iTpad( int pIt, int pMinDesiredLength ) {
        return Strings.iTpad( "" + pIt, pMinDesiredLength );
    }

    public static boolean checkIndex( int pIndex, int pMax, boolean pInclusive ) {
        return ((0 <= pIndex) && (pIndex < pMax)) || (pInclusive && (pIndex == pMax));
    }

    public static void validateIndex( int pIndex, int pMax, boolean pInclusive )
            throws IllegalArgumentException {
        if ( !checkIndex( pIndex, pMax, pInclusive ) ) {
            throw new IllegalArgumentException( "!( 0 <= " + pIndex + " " + (pInclusive ? "<=" : "<") + " " + pMax + " )" );
        }
    }

    /**
     * return pIndex constrained between 0 (inclusive) and pMax (pInclusive).  If the effective 'max' is less than 0, then 0 will be returned.
     */
    public static int constrainIndex( int pIndex, int pMax, boolean pInclusive ) {
        if ( (pMax < pIndex) || (!pInclusive && (pIndex == pMax)) ) {
            pIndex = pInclusive ? pMax : pMax - 1;
        }
        return (pIndex < 0) ? 0 : pIndex;
    }

    public static String getMax2PlacePercentage( int pPortions ) {
        if ( pPortions == 0 ) {
            return null;
        }
        int portion = 10000 / pPortions;
        String s = "" + portion;
        while ( s.length() < 3 ) {
            s = "0" + s;
        }
        int wholeLen = s.length() - 2;
        s = s.substring( 0, wholeLen ) + "." + s.substring( wholeLen );
        while ( s.charAt( s.length() - 1 ) == '0' ) {
            s = s.substring( 0, s.length() - 1 );
        }
        if ( s.charAt( s.length() - 1 ) == '.' ) {
            s = s.substring( 0, s.length() - 1 );
        }
        return s + "%";
    }

    public static boolean areNonArraysEqual( int pThis, int pThat ) {
        return (pThis == pThat);
    }

    /**
     * Returns a String that represents <i>pThis</i> with english code tail,
     * such as "3rd".<p>
     * <p/>
     * If <i>pThis</i> is non-negative, then append on the appropriate two
     * letter <i>english</i> code:<p>
     * <pre>
     *      st,nd,rd,th
     * </pre><p>
     *
     * @param pThis the int to convert.<p>
     *
     * @return the String representation for <i>pThis</i> and if not negative,
     * then appended with the appropriate two letter <i>english</i>
     * junk.<p>
     */
    public static String toNth( int pThis ) {
        String rv = Integer.toString( pThis );
        if ( pThis < 0 ) {
            return rv;
        }

        if ( (pThis < 10) || ('1' != rv.charAt( rv.length() - 2 )) ) // not: 10-19 & 110-119, 210-219, ... 1010-1019, ...
        {
            switch ( rv.charAt( rv.length() - 1 ) ) {
                case '1':
                    return rv + "st";
                case '2':
                    return rv + "nd";
                case '3':
                    return rv + "rd";
                default: // 0th and n0th and 4th - 9th and n4th - n9th
                    break;
            }
        }
        return rv + "th";
    }

    public static int insureNonNegative( int pValue ) {
        return (pValue < 0) ? 0 : pValue;
    }

    public static int intValue( Integer pInteger, int pDefault ) {
        return (pInteger != null) ? pInteger : pDefault;
    }

    public static int intValue( long pValue ) {
        if ( (Integer.MIN_VALUE <= pValue) && (pValue <= Integer.MAX_VALUE) ) {
            return (int) pValue;
        }
        throw new IllegalArgumentException( "Value (" + pValue + ") is outside the acceptable Integer Range" );
    }

    // TODO: ^^^^^^^^^^^^^^^^^^^^^^^^  NEW  ^^^^^^^^^^^^^^^^^^^^^^^^ :ODOT \\
}
