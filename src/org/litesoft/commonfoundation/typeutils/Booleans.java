// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

public class Booleans
{
    public static boolean areNonArraysEqual( boolean pThis, boolean pThat )
    {
        return (pThis == pThat);
    }

    public static boolean isBooleanNotTrue( Object pObject )
    {
        return pObject == null || Boolean.FALSE.equals( pObject );
    }

    public static Boolean fromString( String pInput )
    {
        if ( "YES".equalsIgnoreCase( pInput ) || "Y".equalsIgnoreCase( pInput ) || "TRUE".equalsIgnoreCase( pInput ) || "T".equalsIgnoreCase( pInput ) )
        {
            return Boolean.TRUE;
        }
        if ( "NO".equalsIgnoreCase( pInput ) || "N".equalsIgnoreCase( pInput ) || "FALSE".equalsIgnoreCase( pInput ) || "F".equalsIgnoreCase( pInput ) )
        {
            return Boolean.FALSE;
        }
        return null;
    }

    public static void assertTrue( String pValueDescription, boolean pActual )
            throws IllegalArgumentException
    {
        if ( !pActual )
        {
            throw new IllegalArgumentException( pValueDescription + ": Expected to be true" );
        }
    }

    public static String displayFormatFlag( boolean pFlag, String pTrueString )
    {
        return pFlag ? pTrueString : "";
    }

    public static boolean isAnyTrue( Boolean... pToSearch )
    {
        if ( pToSearch != null )
        {
            for ( Boolean zBoolean : pToSearch )
            {
                if ( Boolean.TRUE.equals( zBoolean ) )
                {
                    return true;
                }
            }
        }
        return false;
    }
}
