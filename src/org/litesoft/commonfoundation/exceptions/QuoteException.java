// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.exceptions;

public class QuoteException extends IllegalArgumentException
{
    public QuoteException()
    {
        super();
    }

    public QuoteException( String s )
    {
        super( s );
    }

    public QuoteException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public QuoteException( Throwable cause )
    {
        super( cause );
    }
}
