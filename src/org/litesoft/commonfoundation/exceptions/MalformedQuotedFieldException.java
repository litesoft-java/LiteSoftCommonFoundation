// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.exceptions;

public class MalformedQuotedFieldException extends QuoteException {
    public MalformedQuotedFieldException() {
        super();
    }

    public MalformedQuotedFieldException( String s ) {
        super( s );
    }

    public MalformedQuotedFieldException( String message, Throwable cause ) {
        super( message, cause );
    }

    public MalformedQuotedFieldException( Throwable cause ) {
        super( cause );
    }
}
