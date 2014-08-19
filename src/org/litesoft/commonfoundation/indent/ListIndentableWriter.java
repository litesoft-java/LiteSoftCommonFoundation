package org.litesoft.commonfoundation.indent;

import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

public class ListIndentableWriter extends AbstractCollectingIndentableWriter {
    private final List<String> mPreviousLines = Lists.newArrayList();

    public ListIndentableWriter( String pDefaultIndentWith ) {
        super( pDefaultIndentWith );
    }

    public ListIndentableWriter() {
        this( DEFAULT_INDENT_WITH );
    }

    @Override
    protected void collectLine( String pLine ) {
        mPreviousLines.add( pLine );
    }

    @Override
    protected String collectorToString() {
        return Strings.linesToString( mPreviousLines.toArray( new String[mPreviousLines.size()] ) );
    }

    public List<String> getLines() {
        return mPreviousLines;
    }
}
