// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.stringmatching;

public abstract class AbstractStringMatcher implements StringMatcher
{
    protected int mMinLength;
    private boolean mIgnoreCase;

    // Package Friendly
    AbstractStringMatcher( int pMinLength, boolean pIgnoreCase )
    {
        mMinLength = pMinLength;
        mIgnoreCase = pIgnoreCase;
    }

    @Override
    public final boolean matches( String pInQuestion )
    {
        return (pInQuestion != null) && (pInQuestion.length() >= mMinLength) && //
               (mIgnoreCase ? LLmatches( pInQuestion.toLowerCase() ) : LLmatches( pInQuestion ));
    }

    abstract protected boolean LLmatches( String pInQuestion );
}
