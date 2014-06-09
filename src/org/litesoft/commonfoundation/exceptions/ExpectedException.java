package org.litesoft.commonfoundation.exceptions;

public class ExpectedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ExpectedException() {
    }

    public ExpectedException( String message ) {
        super( message );
    }

    public ExpectedException( String message, Throwable cause ) {
        super( message, cause );
    }

    public ExpectedException( Throwable cause ) {
        super( cause );
    }
}
