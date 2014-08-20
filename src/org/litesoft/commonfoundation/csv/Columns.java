package org.litesoft.commonfoundation.csv;

import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

public class Columns {
    private final List<Column> mColumns = Lists.newArrayList();

    public int getMinimumTitleCells() {
        return mColumns.size();
    }

    public Column add( String pTitle ) {
        Column zE = new Column( pTitle );
        mColumns.add( zE );
        return zE;
    }

    public int length() {
        return mColumns.size();
    }

    public boolean validateColumnTitles( Row pPossibleColumnTitles ) {
        // TODO: XXX
//        Integers.assertLength( pLineSource + " - Column Titles", pPossibleColumnTitles.length, length() );
//        for ( int i = 0; i < length(); i++ ) {
//            mColumns.get( i ).validateColumnTitle( pLineSource, pPossibleColumnTitles[i] );
//        }
        return true;
    }
}
