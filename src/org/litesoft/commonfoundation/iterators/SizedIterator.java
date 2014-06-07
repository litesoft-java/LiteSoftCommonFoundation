// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.iterators;

import org.litesoft.commonfoundation.annotations.*;

import java.util.*;

public class SizedIterator<T> extends Iterators.AbstractWrapping<T> {
    public SizedIterator( Iterator<T> pIterator ) {
        this( null, pIterator );
    }

    public SizedIterator( long pSize ) {
        this( pSize, null );
    }

    public
    @Nullable
    Long size() {
        return mSize;
    }

    private final Long mSize;

    private SizedIterator( Long pSize, Iterator<T> pIterator ) {
        super( pIterator );
        mSize = pSize;
    }
}
