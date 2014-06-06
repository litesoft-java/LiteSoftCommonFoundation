// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.charstreams;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.typeutils.*;

public class CharSourceFromSequence implements CharSource
{
    private String mSource;
    private int mFrom, mTo;

    public CharSourceFromSequence( @Nullable CharSequence pSource, int pFrom )
    {
        mSource = Strings.deNull( pSource );
        if ( (mFrom = pFrom) < 0 )
        {
            throw new IllegalArgumentException( "'From' not allowed to be negative" );
        }
        mTo = mSource.length();
    }

    public CharSourceFromSequence( @Nullable CharSequence pSource )
    {
        this( pSource, 0 );
    }

    public CharSourceFromSequence( @Nullable CharSequence pSource, int pFrom, int pTo )
    {
        this( pSource, pFrom );
        if ( pTo > mTo )
        {
            throw new IllegalArgumentException( "'To' not allowed to be greater than the length of the 'Source' string" );
        }
        if ( pTo < 0 )
        {
            mTo += pTo;
        }
        else
        {
            mTo = pTo;
        }
    }

    @Override
    public boolean anyRemaining()
    {
        return (mFrom < mTo);
    }

    @Override
    public int peek()
    {
        return anyRemaining() ? mSource.charAt( mFrom ) : -1;
    }

    @Override
    public int get()
    {
        return anyRemaining() ? mSource.charAt( mFrom++ ) : -1;
    }

    @Override
    public char getRequired()
    {
        if ( anyRemaining() )
        {
            return mSource.charAt( mFrom++ );
        }
        throw new IllegalArgumentException( "Unexpected EOD encountered at index " + mFrom + " in " + mSource );
    }

    @Override
    public int getNextOffset()
    {
        return mFrom;
    }

    @Override
    public int getLastOffset()
    {
        return mFrom - 1;
    }

    @Override
    public String toString()
    {
        return mSource.substring( mFrom, mTo );
    }
}
