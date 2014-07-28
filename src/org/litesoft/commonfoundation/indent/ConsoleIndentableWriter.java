package org.litesoft.commonfoundation.indent;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.console.*;

public class ConsoleIndentableWriter extends AbstractIndentableWriter {
    private final Console mConsole;

    public ConsoleIndentableWriter( String pDefaultIndentWith, Console pConsole ) {
        super( pDefaultIndentWith );
        mConsole = ConstrainTo.firstNonNull( pConsole, Console.NULL );
    }

    @Override
    public void close() {
        mConsole.close();
    }

    @Override
    protected void addIndentIgnorant( char pToAdd ) {
        mConsole.print( Character.toString( pToAdd ) );
    }

    @Override
    protected void newLine() {
        mConsole.println( "" );
    }
}
