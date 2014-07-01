// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.csv;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.exceptions.*;
import org.litesoft.commonfoundation.iterators.*;

import java.util.*;

/**
 * An Iterator that converts an Iterator of Strings into an iterator of String arrays, based on the assumption that the source Iterator represents a CSV file.<p>
 * <p/>
 * For the rules to parsing CSV, see  CsvSupport
 *
 * @author George Smith
 * @version 1.0 7/28/01
 * @see CsvSupport
 */

public class CsvIterator extends Iterators.AbstractReadOnly<String[]> {
    public static final String CSV = "CSV";

    private final CsvSupport mCsvSupport = new CsvSupport();
    private final Iterator<String> mIterator;

    private String mPreNextToString, mPreNextRawText;
    private String[] mNext;

    public CsvIterator( Iterator<String> pIterator ) {
        mIterator = Confirm.isNotNull( "Iterator", pIterator );
        mNext = populateNext();
    }

    private String[] populateNext() {
        mPreNextToString = mIterator.toString();
        if ( !mIterator.hasNext() ) {
            return null;
        }
        mPreNextRawText = mIterator.next();
        try {
            return mCsvSupport.decode( mPreNextRawText ); // Happy Case!!!
        }
        catch ( UnclosedQuoteException zUnhappyException ) {
            for ( int zLines = 1; mIterator.hasNext() && (zLines < 20); zLines++ ) {
                mPreNextRawText += "\n" + mIterator.next();
                try {
                    return mCsvSupport.decode( mPreNextRawText ); // Sort of Happy Case!!!
                }
                catch ( UnclosedQuoteException e ) {
                    // Whatever
                }
            }
            throw zUnhappyException;
        }
    }

    @Override
    public boolean hasNext() {
        return (mNext != null);
    }

    @Override
    public
    @NotNull
    String[] next() {
        if ( hasNext() ) {
            String[] zNext = mNext;
            mNext = populateNext();
            return zNext;
        }
        throw new NoSuchElementException();
    }

    public String getPreNextRawText() {
        return mPreNextRawText;
    }

    @Override
    public String toString() {
        return mPreNextToString;
    }
}
