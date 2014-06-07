// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.stringmatching;

public class ContainsStringMatcher extends AbstractSingleStringMatcher {
    // Package Friendly
    ContainsStringMatcher( int pMinLength, boolean pIgnoreCase, String pToMatch ) {
        super( pMinLength, pIgnoreCase, pToMatch );
    }

    @Override
    protected boolean LLmatches( String pInQuestion ) {
        return (pInQuestion.contains( mToMatch ));
    }

    @Override
    public String toString() {
        return "Contains( '" + mToMatch + "' )";
    }
}
