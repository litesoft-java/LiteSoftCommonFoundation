// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.charstreams;

public class CharSinkToString implements CharSink {
    private final StringBuilder mSB = new StringBuilder();

    @Override
    public void add( char pChar ) {
        mSB.append( pChar );
    }

    @Override
    public String toString() {
        return mSB.toString();
    }
}
