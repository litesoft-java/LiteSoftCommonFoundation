package org.litesoft.commonfoundation.indent;

public abstract class AbstractCollectingIndentableWriter extends AbstractIndentableWriter {
    protected StringBuffer mCurrentLine = new StringBuffer();

    public AbstractCollectingIndentableWriter( String pDefaultIndentWith ) {
        super( pDefaultIndentWith );
    }

    @Override
    public void close() {
        if ( currentLineOffset() != 0 ) {
            appendCurrentLine();
        }
    }

    @Override
    public String toString() {
        return collectorToString() + mCurrentLine;
    }

    protected void addIndentIgnorant( char pToAdd ) {
        mCurrentLine.append( pToAdd );
    }

    protected void newLine() {
        appendCurrentLine();
    }

    protected void appendCurrentLine() {
        collectLine( mCurrentLine.toString() );
        mCurrentLine = new StringBuffer();
    }

    abstract protected void collectLine( String pLine );

    abstract protected String collectorToString();
}
