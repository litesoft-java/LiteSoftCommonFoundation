// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.exceptions;

public class NotImplementedYetException extends RuntimeException
{
    public NotImplementedYetException()
    {
        this( "NotImplementedYet" );
    }

    public NotImplementedYetException( String pMessage )
    {
        super( pMessage );
    }
}
