// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.base;

public class Hex
{
    private static final String HEX = "0123456789ABCDEF";

    public static char toChar( int pZeroTo15 )
    {
        pZeroTo15 &= 15;
        return (pZeroTo15 < 0) || (15 < pZeroTo15) ? '?' : HEX.charAt( pZeroTo15 );
    }

    /**
     * Attempt to interpret (char) pHex as a Hex Character
     *
     * @return -1 if not a HEX character, otherwise the 0-15
     */
    public static int fromChar( char pHex )
    {
        return HEX.indexOf( Character.toUpperCase( pHex ) );
    }

    public static int fromCharChecked( char pHex )
            throws IllegalArgumentException
    {
        int rv = fromChar( pHex );
        if ( rv == -1 )
        {
            throw new IllegalArgumentException( "Not a Hex digit '" + pHex + "': " + (int) pHex );
        }
        return rv;
    }

    public static char[] to4Chars( char c )
    {
        char[] rv = new char[4];
        int cv = c;
        for ( int i = 3; i >= 0; i-- )
        {
            rv[i] = toChar( cv );
            cv /= 16;
        }
        return rv;
    }

    public static char from4Chars( char[] pHex )
            throws IllegalArgumentException
    {
        int rv = 0;
        for ( char c : pHex )
        {
            rv = (rv * 16) + fromCharChecked( c );
        }
        return (char) rv;
    }
}
