package org.litesoft.commonfoundation.exceptions;

public abstract class AbstractServiceException extends RuntimeException {
    protected final int mResultCode;

    public AbstractServiceException( int pResultCode ) {
        mResultCode = pResultCode;
    }

    public AbstractServiceException( int pResultCode, Throwable cause ) {
        super( cause );
        mResultCode = pResultCode;
    }

    public AbstractServiceException( int pResultCode, String message ) {
        super( message );
        mResultCode = pResultCode;
    }

    public AbstractServiceException( int pResultCode, String message, Throwable cause ) {
        super( message, cause );
        mResultCode = pResultCode;
    }

    public int getResultCode() {
        return mResultCode;
    }

    @Override
    public String getMessage() {
        String zTrailer = "\nCode: " + getResultCode();
        String zMsg = super.getMessage();
        if ( (zMsg != null) && zMsg.endsWith( zTrailer ) ) {
            return zMsg;
        }
        return zMsg + zTrailer;
    }
}
