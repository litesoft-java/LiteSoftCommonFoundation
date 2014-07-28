package org.litesoft.commonfoundation.indent;

import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

public class ErrorBoxingIndentableWriter extends ListIndentableWriter {
    private static final String BOX_SIDE_PADDING = " ";
    private static final String BOX_SIDE = "***";
    private static final String BOX_LEFT_SIDE = BOX_SIDE + BOX_SIDE_PADDING;
    private static final String BOX_RIGHT_SIDE = BOX_SIDE_PADDING + BOX_SIDE;

    private static final char BOX_CHAR = BOX_SIDE.charAt( 0 );
    private static final int BOX_LINE_OVERHEAD = BOX_LEFT_SIDE.length() + BOX_RIGHT_SIDE.length();

    private final IndentableWriter mProxyWriter;

    public ErrorBoxingIndentableWriter( IndentableWriter pProxyWriter ) {
        super( pProxyWriter.getDefaultIndentWith() );
        mProxyWriter = pProxyWriter;
    }

    @Override
    public void close() {
        super.close();
        List<String> zLines = getLines();
        int zMaxLength = Strings.maxEntryLength( zLines );
        String zBoxLine = Strings.padRight( BOX_SIDE, BOX_CHAR, zMaxLength + BOX_LINE_OVERHEAD );
        mProxyWriter.printLn( zBoxLine );
        mProxyWriter.printLn( zBoxLine );
        mProxyWriter.printLn( zBoxLine );
        addBoxedLine( "", zMaxLength );
        for ( String zLine : zLines ) {
            addBoxedLine( zLine, zMaxLength );
        }
        addBoxedLine( "", zMaxLength );
        mProxyWriter.printLn( zBoxLine );
        mProxyWriter.printLn( zBoxLine );
        mProxyWriter.printLn( zBoxLine );
    }

    private void addBoxedLine( String zLine, int zMaxLength ) {
        mProxyWriter.printLn( BOX_LEFT_SIDE, Strings.padRight( zLine, ' ', zMaxLength ), BOX_RIGHT_SIDE );
    }
}
