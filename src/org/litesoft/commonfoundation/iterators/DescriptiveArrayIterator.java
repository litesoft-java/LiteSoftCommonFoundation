package org.litesoft.commonfoundation.iterators;

public class DescriptiveArrayIterator<T> extends ArrayIterator<T> implements DescriptiveIterator<T> {
    private final String mIndexPrefixTextForDescription;
    private final int mIndexAdjustment;

    public DescriptiveArrayIterator( String pIndexPrefixTextForDescription, int pIndexAdjustment, T[] pArray ) {
        super( pArray );
        mIndexPrefixTextForDescription = pIndexPrefixTextForDescription;
        mIndexAdjustment = pIndexAdjustment;
    }

    public DescriptiveArrayIterator( String pIndexPrefixTextForDescription, T[] pArray ) {
        this( pIndexPrefixTextForDescription, 0, pArray );
    }

    @Override
    public String toString() {
        return mIndexPrefixTextForDescription + (getIndex() + mIndexAdjustment);
    }
}
