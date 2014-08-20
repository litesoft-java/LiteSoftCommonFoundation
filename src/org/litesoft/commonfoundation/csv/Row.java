package org.litesoft.commonfoundation.csv;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.exceptions.*;
import org.litesoft.commonfoundation.issue.*;

public class Row {
    private final String mRawText;
    private final Source mSource;
    private final String[] mCells;

    public Row( String pRawText, Source pSource, String[] pCells ) {
        mRawText = pRawText;
        mSource = pSource;
        mCells = pCells;
    }

    public int getCellCount() {
        return mCells.length;
    }

    /**
     * @return null if Row too short to contain the Column's Index
     */
    public SourcedContent getCell( @NotNull Column pColumn ) {
        int zIndex = pColumn.getIndex();
        return (mCells.length <= zIndex) ? null :
               new SourcedContent( mSource.plus( "Column" ).of( pColumn.getTitle() + "(" + pColumn.getIdentifierLetters() + ")" ),
                                   mCells[zIndex] );
    }

    public boolean isEmptyRow() {
        return (mCells.length == 1) && (mCells[0].length() == 0);
    }

    public static Row build( @NotNull Source pSource, @NotNull CsvIterator pLines ) {
        Source zSource = pSource.plus( pLines.toString() );
        String zPreNextRawText = pLines.getPreNextRawText();
        String[] zRow;
        try {
            zRow = pLines.next();
        }
        catch ( UnclosedQuoteException e ) {
            zSource.addError( Issue.of( CsvIterator.CSV, "UnclosedQuote" ).with( zPreNextRawText ) );
            return null;
        }
        return new Row( zPreNextRawText, zSource, zRow );
    }

    public <T> T error( String pErrorKey ) {
        mSource.addError( Issue.of( CsvIterator.CSV, pErrorKey ).with( mRawText ) );
        return null;
    }
}
