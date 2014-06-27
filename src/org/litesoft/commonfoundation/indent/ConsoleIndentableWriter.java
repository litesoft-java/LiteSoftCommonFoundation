package org.litesoft.commonfoundation.indent;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.console.*;

public class ConsoleIndentableWriter extends AbstractIndentableWriter {
    private final Console mConsole;
    private int mCurrentLineLength;

    public ConsoleIndentableWriter( String pIndentWith, Console pConsole ) {
        super( pIndentWith );
        mConsole = ConstrainTo.firstNonNull( pConsole, Console.NULL );
    }

    @Override
    public int currentLineOffset() {
        return mCurrentLineLength;
    }

    @Override
    public void close() {
        mConsole.close();
    }

    @Override
    protected void addIndentIgnorant( Object pToAdd ) {
        String zText = ConstrainTo.firstNonNull( (pToAdd != null) ? pToAdd.toString() : null, "null" );
        mConsole.print( zText );
        mCurrentLineLength += zText.length();
    }

    @Override
    protected void newLine() {
        mConsole.println( "" );
        mCurrentLineLength = 0;
    }
}
