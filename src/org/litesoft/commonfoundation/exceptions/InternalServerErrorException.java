package org.litesoft.commonfoundation.exceptions;

import org.litesoft.commonfoundation.exceptions.*;

public class InternalServerErrorException extends AbstractServiceException {
    public static final int RESULT_CODE = 500;

    public InternalServerErrorException( Throwable cause ) {
        super( RESULT_CODE, cause );
    }

    public InternalServerErrorException( String message ) {
        super( RESULT_CODE, message );
    }

    public InternalServerErrorException( String message, Throwable cause ) {
        super( RESULT_CODE, message, cause );
    }
}
