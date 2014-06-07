// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.stringmatching;

public class EndsWithStringMatcher extends AbstractSingleStringMatcher {
    // Package Friendly
    EndsWithStringMatcher( int pMinLength, boolean pIgnoreCase, String pToMatch ) {
        super( pMinLength, pIgnoreCase, pToMatch );
    }

    @Override
    protected boolean LLmatches( String pInQuestion ) {
        return pInQuestion.endsWith( mToMatch );
    }

    @Override
    public String toString() {
        return "EndsWith( '" + mToMatch + "' )";
    }
}
