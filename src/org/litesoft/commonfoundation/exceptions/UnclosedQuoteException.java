// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.exceptions;

public class UnclosedQuoteException extends QuoteException {
    public UnclosedQuoteException() {
        super();
    }

    public UnclosedQuoteException( String s ) {
        super( s );
    }

    public UnclosedQuoteException( String message, Throwable cause ) {
        super( message, cause );
    }

    public UnclosedQuoteException( Throwable cause ) {
        super( cause );
    }
}
