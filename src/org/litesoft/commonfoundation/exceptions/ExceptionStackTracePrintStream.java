// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.exceptions;

import java.io.*;

/**
 * A print stream which can be passed to
 * {@link Throwable#printStackTrace(PrintStream)}. Only the minimally necessary
 * methods for this use are implemented.
 */
public class ExceptionStackTracePrintStream extends PrintStream
{
    private final StringBuilder mStringBuilder = new StringBuilder();

    public ExceptionStackTracePrintStream()
    {
        super( OutputStreamStub.INSTANCE );
    }

    @Override
    public void println( Object x )
    {
        mStringBuilder.append( x );
        mStringBuilder.append( "\r\n" );
    }

    @Override
    public void println( String x )
    {
        mStringBuilder.append( x );
        mStringBuilder.append( "\r\n" );
    }

    @Override
    public String toString()
    {
        return mStringBuilder.toString();
    }

    private static class OutputStreamStub extends OutputStream
    {
        public static final OutputStreamStub INSTANCE = new OutputStreamStub();

        private static final String MESSAGE = "FIXME: this method should never be called";

        @Override
        public void write( int b )
        {
            throw new AssertionError( MESSAGE );
        }

        @Override
        public void write( byte[] b )
        {
            throw new AssertionError( MESSAGE );
        }

        @Override
        public void write( byte[] b, int off, int len )
        {
            throw new AssertionError( MESSAGE );
        }

        @Override
        public void flush()
        {
            throw new AssertionError( MESSAGE );
        }

        @Override
        public void close()
        {
            throw new AssertionError( MESSAGE );
        }
    }
}
