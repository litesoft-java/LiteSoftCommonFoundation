// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.typeutils.proxies.*;

public class Enums {
    // TODO: vvvvvvvvvvvvvvvvvvvvvvvv  NEW  vvvvvvvvvvvvvvvvvvvvvvvv :ODOT \\

    public static <T extends Enum<T>> T fromString( Class<T> pClass, String pEnumAsString ) {
        if ( pClass != null ) {
            if ( null != (pEnumAsString = ConstrainTo.significantOrNull( pEnumAsString )) ) {
                try {
                    return Enum.valueOf( pClass, pEnumAsString );
                }
                catch ( IllegalArgumentException ex ) {
                    // Bad String
                }
            }
        }
        return null;
    }

    // TODO: ^^^^^^^^^^^^^^^^^^^^^^^^  NEW  ^^^^^^^^^^^^^^^^^^^^^^^^ :ODOT \\

    public static <T extends Enum<T>> T fromString( String pValue, T[] pValues ) {
        for ( T zValue : pValues ) {
            if ( (zValue instanceof HasAlternateNames) ? ((HasAlternateNames) zValue).isMatchingName( pValue ) : zValue.toString().equalsIgnoreCase( pValue ) ) {
                return zValue;
            }
        }
        return null;
    }

    public static <E extends Enum<E>> E valueOf( String enumName, Class<E> enumClass ) {
        if ( !Currently.significant( enumName ) ) {
            try {
                return Enum.valueOf( enumClass, enumName );
            }
            catch ( Exception e ) {
                // Fall thru to null!
            }
        }
        return null;
    }
}
