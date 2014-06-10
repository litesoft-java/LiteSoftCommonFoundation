package org.litesoft.commonfoundation.indent;

import org.litesoft.commonfoundation.base.*;

public class StringIndentableWriter implements IndentableWriter {
    private final String mIndentWith;
    private final StringBuffer mPreviousLines = new StringBuffer();
    private StringBuffer mCurrentLine = new StringBuffer();
    private int mIndent = 0;

    public StringIndentableWriter( String pIndentWith ) {
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

    @Override
    public void close() {
        appendCurrentLine();
    }

    @Override
    public int currentLineOffset() {
        return mCurrentLine.length();
    }

    @Override
    public String toString() {
        return mPreviousLines.toString() + mCurrentLine;
    }

    private void addArray( Object[] pToAdd ) {
        if ( (pToAdd != null) && (pToAdd.length > 0) ) {
            add( pToAdd[0] );
            for ( int i = 1; i < pToAdd.length; i++ ) {
                addSafe( pToAdd[i] );
            }
        }
    }

    private void add( Object pToAdd ) {
        if ( mCurrentLine.length() == 0 ) {
            for ( int i = mIndent; 0 < i; i-- ) {
                addSafe( mIndentWith );
            }
        }
        addSafe( pToAdd );
    }

    private void addSafe( Object pToAdd ) {
        mCurrentLine.append( pToAdd );
    }

    private void newLine() {
        appendCurrentLine().append( '\n' );
    }

    private StringBuffer appendCurrentLine() {
        mPreviousLines.append( mCurrentLine );
        mCurrentLine = new StringBuffer();
        return mPreviousLines;
    }
}
