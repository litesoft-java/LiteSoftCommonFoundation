// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;

public class Longs extends Numerics {
    public static final long ZERO = 0;
    public static final Long OBJECT_ZERO = ZERO;
    public static final long[] PRIMITIVE_EMPTY_ARRAY = new long[0];
    public static final Long[] EMPTY_ARRAY = new Long[0];
    public static final TypeTransformer<Long> TYPE_TRANSFORMER = new TypeTransformer<Long>() {
        @Override
        public Long transformNonNull( Object pObject ) {
            return Longs.toLong( pObject );
        }
    };

    public static Long toLong( Object pObject ) {
        if ( pObject instanceof Long ) {
            return Cast.it( pObject );
        }
        if ( pObject instanceof Number ) {
            return ((Number) pObject).longValue();
        }
        if ( pObject != null ) {
            String zString = ConstrainTo.significantOrNull( pObject.toString() );
            if ( zString != null ) {
                try {
                    return Long.valueOf( zString );
                }
                catch ( NumberFormatException e ) {
                    // Fall Thru
                }
            }
        }
        return null;
    }

    public static String toStringPadZero( long pValue, int pMinLength ) {
        boolean zNegative = (pValue < 0);
        String zStr = Strings.padLeft( '0', Long.toString( Math.abs( pValue ) ), pMinLength );
        return zNegative ? "-" + zStr : zStr;
    }

    public static long roundUp( double pValue ) {
        return Math.round( pValue + 0.5 );
    }

    // TODO: ||||||||||||||||||||||||||||||||||||||||||||||||||||||| :ODOT \\

    public static long assertPositive( String pWhat, long pLong ) {
        return assertAtLeast( pWhat, pLong, 1 );
    }

    public static long assertAtLeast( String pWhat, long pLong, long pAtLeast ) {
        if ( pLong < pAtLeast ) {
            throw new IllegalArgumentException( pWhat + " (" + pLong + ") Must be at least " + pAtLeast );
        }
        return pLong;
    }

    // TODO: vvvvvvvvvvvvvvvvvvvvvvvv  NEW  vvvvvvvvvvvvvvvvvvvvvvvv :ODOT \\

    public static Long assertNonNegative( String pName, Long pValue )
            throws IllegalArgumentException {
        if ( pValue != null ) {
            assertNonNegative( pName, pValue.longValue() );
        }
        return pValue;
    }

    public static long assertNonNegative( String pName, long pValue )
            throws IllegalArgumentException {
        if ( pValue < 0 ) {
            throw new IllegalArgumentException( pName + CANNOT_BE_NEGATIVE + ", but was: " + pValue );
        }
        return pValue;
    }

    public static void assertNotEqual( String pObjectName, long pNotExpected, long pActual )
            throws IllegalArgumentException {
        if ( pNotExpected == pActual ) {
            throw new IllegalArgumentException( pObjectName + ": '" + pNotExpected + "' Not allowed" );
        }
    }

    // TODO: ^^^^^^^^^^^^^^^^^^^^^^^^  NEW  ^^^^^^^^^^^^^^^^^^^^^^^^ :ODOT \\
}
