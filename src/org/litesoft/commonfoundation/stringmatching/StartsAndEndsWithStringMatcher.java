// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.stringmatching;

public class StartsAndEndsWithStringMatcher extends AbstractStringMatcher
{
    private String mStartsWith, mEndsWith;

    // Package Friendly
    StartsAndEndsWithStringMatcher( int pMinLength, boolean pIgnoreCase, String pStartsWith, String pEndsWith )
    {
        super( pMinLength, pIgnoreCase );
        mStartsWith = pStartsWith;
        mEndsWith = pEndsWith;
    }

    @Override
    protected boolean LLmatches( String pInQuestion )
    {
        return pInQuestion.startsWith( mStartsWith ) && pInQuestion.endsWith( mEndsWith );
    }

    @Override
    public String toString()
    {
        return "StartsAndEndsWith( '" + mStartsWith + "', '" + mEndsWith + "' )";
    }
}
