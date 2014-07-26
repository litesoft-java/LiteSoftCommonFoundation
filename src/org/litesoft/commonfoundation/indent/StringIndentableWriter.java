package org.litesoft.commonfoundation.indent;

public class StringIndentableWriter extends AbstractCollectingIndentableWriter {
    private final StringBuffer mPreviousLines = new StringBuffer();

    public StringIndentableWriter( String pDefaultIndentWith ) {
        super( pDefaultIndentWith );
    }

    @Override
    protected void newLine() {
        super.newLine();
        mPreviousLines.append( '\n' );
    }

    @Override
    protected void collectLine( String pLine ) {
        mPreviousLines.append( pLine );
    }

    @Override
    protected String collectorToString() {
        return mPreviousLines.toString();
    }
}
