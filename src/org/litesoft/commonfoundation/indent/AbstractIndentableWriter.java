package org.litesoft.commonfoundation.indent;

import org.litesoft.commonfoundation.base.*;

public abstract class AbstractIndentableWriter implements IndentableWriter {
    protected final String mIndentWith;
    private int mIndent = 0;

    protected AbstractIndentableWriter( String pIndentWith ) {
        mIndentWith = Confirm.isNotNull( "IndentWith", pIndentWith );
    }

    @Override
    public void indent() {
        mIndent++;
    }

    @Override
    public void outdent() {
        mIndent = Math.max( 0, mIndent - 1 );
    }

    @Override
    public void print( Object pToPrint0, Object... pToPrintN ) {
        add( pToPrint0 );
        addArray( pToPrintN );
    }

    @Override
    public void printLn( Object... pToPrint ) {
        addArray( pToPrint );
        newLine();
    }

    private void addArray( Object[] pToAdd ) {
        if ( (pToAdd != null) && (pToAdd.length > 0) ) {
            add( pToAdd[0] );
            for ( int i = 1; i < pToAdd.length; i++ ) {
                addIndentIgnorant( pToAdd[i] );
            }
        }
    }

    private void add( Object pToAdd ) {
        if ( currentLineOffset() == 0 ) {
            for ( int i = mIndent; 0 < i; i-- ) {
                addIndentIgnorant( mIndentWith );
            }
        }
        addIndentIgnorant( pToAdd );
    }

    abstract protected void addIndentIgnorant( Object pToAdd );

    abstract protected void newLine();
}
