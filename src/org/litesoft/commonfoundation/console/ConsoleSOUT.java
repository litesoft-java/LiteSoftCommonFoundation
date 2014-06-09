package org.litesoft.commonfoundation.console;

public class ConsoleSOUT implements Console {
    @Override
    public void println( String pLine ) {
        System.out.println( pLine );
    }

    @Override
    public void close() {
    }
}
