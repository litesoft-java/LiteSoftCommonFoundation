package org.litesoft.commonfoundation.csv;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.typeutils.*;

public class Column {
    public static final int MAX_COLUMN_SUPPORTED = 26 * 26 + 25; // "ZZ"
    private final String mTitle;
    private int mIndex; // 0 based!
    private String mIdentifierLetters;

    public Column( String pTitle ) {
        mTitle = Confirm.significant( "Title", pTitle );
    }

    public String getTitle() {
        return mTitle;
    }

    public int getIndex() {
        return mIndex;
    }

    public String getIdentifierLetters() {
        return mIdentifierLetters;
    }

    /* package Friendly so Columns can set it! */
    void setIndex( int pIndexZeroBased ) {
        mIndex = Integers.assertFromThru( "IndexZeroBased", pIndexZeroBased, 0, MAX_COLUMN_SUPPORTED);
        if (pIndexZeroBased <= 25) {
            mIdentifierLetters = toLetter( pIndexZeroBased );
        } else {
            int zFirstLetter = 0; // 'A'
            while (26 <= (pIndexZeroBased -= 26)) {
                zFirstLetter++;
            }
            mIdentifierLetters = toLetter( zFirstLetter ) + toLetter( pIndexZeroBased );
        }
    }

    private String toLetter( int pIndexZeroBased ) {
        return Character.toString( (char)('A' + pIndexZeroBased) );
    }

    // TODO: XXX - Old Code...

    public void validateColumnTitle( String pLineSource, String pColumnTitle ) {
        if ( mTitle != null ) {
            Confirm.areEqualIgnoreCase( pLineSource + " - Column Title '" + mIdentifierLetters + "'",
                                        mTitle, ConstrainTo.significantOrNull( pColumnTitle, "" ) );
        }
    }

    public String get( String[] pFields ) {
        return pFields[mIndex];
    }
}
