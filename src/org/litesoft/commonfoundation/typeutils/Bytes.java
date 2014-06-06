package org.litesoft.commonfoundation.typeutils;

public class Bytes
{
    public static boolean areArraysEqual( byte[] pThis, byte[] pThat )
    {
        if ( pThis == pThat ) // handles if both are null
        {
            return true;
        }
        if ( (pThis != null) && (pThat != null) && (pThis.length == pThat.length) )
        {
            for ( int i = pThis.length; --i >= 0; )
            {
                if ( pThis[i] != pThat[i] )
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
