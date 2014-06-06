// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

public class Enums
{
    public static <T extends Enum<T>> T fromString( Class<T> pClass, String pEnumAsString )
    {
        if ( pClass != null )
        {
            if ( null != (pEnumAsString = Strings.noEmpty( pEnumAsString )) )
            {
                try
                {
                    return Enum.valueOf( pClass, pEnumAsString );
                }
                catch ( IllegalArgumentException ex )
                {
                    // Bad String
                }
            }
        }
        return null;
    }
}
