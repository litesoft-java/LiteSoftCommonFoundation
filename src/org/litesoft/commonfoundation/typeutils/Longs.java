// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

public class Longs extends Numerics {
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
}
