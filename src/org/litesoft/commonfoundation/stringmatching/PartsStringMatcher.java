// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.stringmatching;

public class PartsStringMatcher extends AbstractStringMatcher {
    private String[] mParts;
    private int mLastIndex;

    // Package Friendly
    PartsStringMatcher( int pMinLength, boolean pIgnoreCase, String[] pParts ) {
        super( pMinLength, pIgnoreCase );
        mParts = pParts;
        mLastIndex = pParts.length - 1;
    }

    @Override
    protected boolean LLmatches( String pInQuestion ) {
        int zIQlen = pInQuestion.length();
        int zMinLength = mMinLength;

        int zFrom = 0;

        int zPndx = 0;
        String part = mParts[zPndx++];
        int zPLen = part.length();
        if ( zPLen != 0 ) {
            if ( !pInQuestion.startsWith( part ) ) {
                return false;
            }
            zFrom = zPLen;
            zMinLength -= zPLen;
        }
        do {
            part = mParts[zPndx++];
            zPLen = part.length();
            if ( zPLen != 0 ) {
                int zAt = pInQuestion.indexOf( part, zFrom );
                if ( zAt == -1 ) {
                    return false;
                }
                zFrom = zAt + zPLen;
                zMinLength -= zPLen;
                if ( (zIQlen - zFrom) < zMinLength ) {
                    return false;
                }
            }
        }
        while ( zPndx < mLastIndex );

        part = mParts[mLastIndex];
        return (part.length() == 0) || pInQuestion.endsWith( part );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( "Parts( '" ).append( mParts[0] ).append( '"' );
        for ( int i = 1; i < mParts.length; i++ ) {
            sb.append( ", '" ).append( mParts[i] ).append( '"' );
        }
        return sb.append( " )" ).toString();
    }
}
