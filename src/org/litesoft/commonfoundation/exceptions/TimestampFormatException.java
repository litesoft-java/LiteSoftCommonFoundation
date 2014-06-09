package org.litesoft.commonfoundation.exceptions;

public class TimestampFormatException extends IllegalArgumentException {
    public TimestampFormatException( String s ) {
        super( s );
    }

    public TimestampFormatException( String message, Throwable cause ) {
        super( message, cause );
    }
}
