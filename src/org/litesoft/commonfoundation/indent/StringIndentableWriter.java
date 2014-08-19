package org.litesoft.commonfoundation.indent;

public class StringIndentableWriter extends AbstractCollectingIndentableWriter {
    private final StringBuffer mPreviousLines = new StringBuffer();

    public StringIndentableWriter( String pDefaultIndentWith ) {
        super( pDefaultIndentWith );
    }

    public StringIndentableWriter() {
        this( DEFAULT_INDENT_WITH );
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

    public static String formatWith( Indentable pIndentable ) {
        return new StringIndentableWriter().applyToAndClose( pIndentable ).toString();
    }
}
