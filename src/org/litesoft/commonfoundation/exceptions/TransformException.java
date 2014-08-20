package org.litesoft.commonfoundation.exceptions;

public class TransformException extends RuntimeException {
    public TransformException( String message ) {
        super( message );
    }

    public TransformException( String message, Throwable cause ) {
        super( message, cause );
    }
}
