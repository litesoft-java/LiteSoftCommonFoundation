package org.litesoft.commonfoundation.indent;

public class StringIndentableWriter extends AbstractIndentableWriter {
    private final StringBuffer mPreviousLines = new StringBuffer();
    private StringBuffer mCurrentLine = new StringBuffer();

    public StringIndentableWriter( String pIndentWith ) {
        super( pIndentWith );
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

    protected void addIndentIgnorant( Object pToAdd ) {
        mCurrentLine.append( pToAdd );
    }

    protected void newLine() {
        appendCurrentLine().append( '\n' );
    }

    private StringBuffer appendCurrentLine() {
        mPreviousLines.append( mCurrentLine );
        mCurrentLine = new StringBuffer();
        return mPreviousLines;
    }
}
