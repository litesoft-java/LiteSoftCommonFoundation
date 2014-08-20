package org.litesoft.commonfoundation.exceptions;

public interface ExceptionOccurredCallback {
    void exceptionOccurred( Object pSource, Exception pException );

    public static final ExceptionOccurredCallback TO_CONSOLE = new ExceptionOccurredCallback() {
        @Override
        public void exceptionOccurred( Object pSource, Exception pException ) {
            pException.printStackTrace();
        }
    };
}