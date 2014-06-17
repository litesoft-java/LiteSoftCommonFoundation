// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.charstreams;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.base.*;

public class CharSourceFromSequence implements CharSource {
    private final String mSource;
    private int mFrom, mTo;

    public CharSourceFromSequence( @Nullable CharSequence pSource, int pFrom ) {
        mSource = ConstrainTo.notNull( pSource );
        if ( (mFrom = pFrom) < 0 ) {
            throw new IllegalArgumentException( "'From' not allowed to be negative" );
        }
        mTo = mSource.length();
    }

    public CharSourceFromSequence( @Nullable CharSequence pSource ) {
        this( pSource, 0 );
    }

    public CharSourceFromSequence( @Nullable CharSequence pSource, int pFrom, int pTo ) {
        this( pSource, pFrom );
        if ( pTo > mTo ) {
            throw new IllegalArgumentException( "'To' not allowed to be greater than the length of the 'Source' string" );
        }
        if ( pTo < 0 ) {
            mTo += pTo;
        } else {
            mTo = pTo;
        }
    }

    @Override
    public boolean anyRemaining() {
        return (mFrom < mTo);
    }

    @Override
    public int peek() {
        return anyRemaining() ? mSource.charAt( mFrom ) : -1;
    }

    @Override
    public int get() {
        return anyRemaining() ? mSource.charAt( mFrom++ ) : -1;
    }

    @Override
    public char getRequired() {
        if ( anyRemaining() ) {
            return mSource.charAt( mFrom++ );
        }
        throw new IllegalArgumentException( "Unexpected EOD encountered at index " + mFrom + " in " + mSource );
    }

    @Override
    public int getNextOffset() {
        return mFrom;
    }

    @Override
    public int getLastOffset() {
        return mFrom - 1;
    }

    @Override
    public String getUpTo( char c ) {
        int at = mSource.indexOf( c, mFrom );
        if ( at == -1 ) {
            return "";
        }
        String rv = mSource.substring( mFrom, at );
        mFrom = at;
        return rv;
    }

    @Override
    public boolean consumeSpaces() {
        while ( peek() == ' ' ) {
            mFrom++;
        }
        return anyRemaining();
    }

    @Override
    public String getUpToNonVisible7BitAscii() {
        if ( !anyRemaining() ) {
            return "";
        }
        int zFrom = mFrom;
        for ( int c = peek(); (c != -1) && isVisible7BitAscii( c ); c = peek() ) {
            mFrom++;
        }
        return mSource.substring( zFrom, mFrom );
    }

    @Override
    public boolean consumeNonVisible7BitAscii() {
        for ( int c = peek(); (c != -1) && !isVisible7BitAscii( c ); c = peek() ) {
            mFrom++;
        }
        return anyRemaining();
    }

    @Override
    public String toString() {
        return mSource.substring( mFrom, mTo );
    }

    private boolean isVisible7BitAscii( int c ) {
        return (' ' < c) && (c <= 126);
    }
}
