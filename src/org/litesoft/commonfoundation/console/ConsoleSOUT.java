package org.litesoft.commonfoundation.console;

public class ConsoleSOUT implements Console {
    public static final Console INSTANCE = new ConsoleSOUT();

    @Override
    public void print( String pText ) {
        System.out.print( pText );
    }

    @Override
    public void println( String pLine ) {
        System.out.println( pLine );
    }

    @Override
    public void close() {
    }
}
