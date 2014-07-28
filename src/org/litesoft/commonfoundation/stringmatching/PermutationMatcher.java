package org.litesoft.commonfoundation.stringmatching;

import org.litesoft.commonfoundation.base.*;

public class PermutationMatcher implements StringMatcher {
    private final String mToPermutate;

    public PermutationMatcher( String pToPermutate ) {
        mToPermutate = Confirm.significant( "ToPermutate", pToPermutate );
    }

    @Override
    public boolean matches( String pInQuestion ) {
        if ( null == (pInQuestion = ConstrainTo.significantOrNull( pInQuestion )) ) {
            return false;
        }
        if (mToPermutate.charAt( 0 ) != pInQuestion.charAt( 0 )) {
            return false;
        }
        int zAt, zFrom = 1;
        for ( int i = 1; i < pInQuestion.length(); i++ ) {
            if (-1 == (zAt = mToPermutate.indexOf( pInQuestion.charAt( i ), zFrom ))) {
                return false;
            }
            zFrom = zAt + 1;
        }
        return true;
    }
}
