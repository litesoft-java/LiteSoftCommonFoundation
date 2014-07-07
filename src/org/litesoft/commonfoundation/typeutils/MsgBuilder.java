package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;

public class MsgBuilder {
    private StringBuilder mSB = new StringBuilder();

    @Override
    public String toString() {
        return mSB.toString();
    }

    public MsgBuilder addQuoted( Object pToBeQuoted ) {
        addSpace();
        mSB.append( "'" );
        mSB.append( pToBeQuoted );
        mSB.append( "'" );
        return this;
    }

    public MsgBuilder addText( String pText ) {
        if ( pText.length() != 0 ) {
            if ( is7BitAlphaNumeric( pText.charAt( 0 ) ) ) {
                addSpace();
            }
            mSB.append( pText );
        }
        return this;
    }

    public MsgBuilder addOptionalText( String pText ) {
        return (null == (pText = ConstrainTo.significantOrNull( pText ))) ? this : addText( pText );
    }

    public MsgBuilder addOptionalText( String pPrefix, String pText ) {
        return (null == (pText = ConstrainTo.significantOrNull( pText ))) ? this : addText( pPrefix + pText );
    }

    public MsgBuilder addPluralText( String pText, int pCount ) {
        pText = ConstrainTo.notNull( pText );
        if ( pCount != 1 ) {
            pText += "s";
        }
        return addText( pText );
    }

    public MsgBuilder addOptionalPluralText( String pText, int pCount ) {
        return (null == (pText = ConstrainTo.significantOrNull( pText ))) ? this : addPluralText( pText, pCount );
    }

    public MsgBuilder addOptionalQuoted( String pLabel, String pOptionalToBeQuotes ) {
        if ( pOptionalToBeQuotes != null ) {
            addText( pLabel ).addQuoted( pOptionalToBeQuotes );
        }
        return this;
    }

    private void addSpace() {
        if ( (mSB.length() != 0) && is7BitAlphaNumeric( mSB.charAt( mSB.length() - 1 ) ) ) {
            mSB.append( ' ' );
        }
    }

    private boolean is7BitAlphaNumeric( char c ) {
        return Characters.is7BitAlphaNumeric( c );
    }
}
