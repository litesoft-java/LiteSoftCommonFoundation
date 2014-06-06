// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.stringmatching;

public class EqualsStringMatcher extends AbstractSingleStringMatcher
{
    // Package Friendly
    EqualsStringMatcher( int pMinLength, boolean pIgnoreCase, String pToMatch )
    {
        super( pMinLength, pIgnoreCase, pToMatch );
    }

    @Override
    protected boolean LLmatches( String pInQuestion )
    {
        return mToMatch.equals( pInQuestion );
    }

    @Override
    public String toString()
    {
        return "Equals( '" + mToMatch + "' )";
    }
}
