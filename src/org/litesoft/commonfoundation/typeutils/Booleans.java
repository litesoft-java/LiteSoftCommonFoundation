// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;

public class Booleans {
    public static final TypeTransformer<Boolean> TYPE_TRANSFORMER = new TypeTransformer<Boolean>() {
        @Override
        public Boolean transformNonNull( Object pObject ) {
            return Booleans.toBoolean( pObject );
        }
    };

    public static Boolean toBoolean( Object pObject ) {
        if ( pObject instanceof Boolean ) {
            return Cast.it( pObject );
        }
        if ( pObject != null ) {
            return fromString( ConstrainTo.significantOrNull( pObject.toString() ) );
        }
        return null;
    }

    public static Boolean fromString( String pInput ) {
        if ( "YES".equalsIgnoreCase( pInput ) || "Y".equalsIgnoreCase( pInput ) || "TRUE".equalsIgnoreCase( pInput ) || "T".equalsIgnoreCase( pInput ) ) {
            return Boolean.TRUE;
        }
        if ( "NO".equalsIgnoreCase( pInput ) || "N".equalsIgnoreCase( pInput ) || "FALSE".equalsIgnoreCase( pInput ) || "F".equalsIgnoreCase( pInput ) ) {
            return Boolean.FALSE;
        }
        return null;
    }

    public static boolean areNonArraysEqual( boolean pThis, boolean pThat ) {
        return (pThis == pThat);
    }

    public static boolean isBooleanNotTrue( Object pObject ) {
        return pObject == null || Boolean.FALSE.equals( pObject );
    }

    public static String displayFormatFlag( boolean pFlag, String pTrueString ) {
        return pFlag ? pTrueString : "";
    }

    public static boolean isAnyTrue( Boolean... pToSearch ) {
        if ( pToSearch != null ) {
            for ( Boolean zBoolean : pToSearch ) {
                if ( Boolean.TRUE.equals( zBoolean ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int toBinarySwitchValue( Boolean... pValues ) {
        int zValue = 0;
        if ( pValues != null ) {
            int zBit = 1;
            for ( Boolean zBoolean : pValues ) {
                if ( Boolean.TRUE == zBoolean ) {
                    zValue += zBit;
                }
                zBit += zBit; // Next Bit
            }
        }
        return zValue;
    }

    public static String toDisplayString( Boolean pBoolean ) {
        return Boolean.valueOf( ConstrainTo.notNull( pBoolean ) ).toString();
    }
}
