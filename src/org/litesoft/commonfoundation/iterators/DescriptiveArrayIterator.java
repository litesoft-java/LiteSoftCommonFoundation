package org.litesoft.commonfoundation.iterators;

public class DescriptiveArrayIterator<T> extends ArrayIterator<T> {
    private final String mIndexPrefixTextForDescription;
    private final int mIndexAdjustment;

    public DescriptiveArrayIterator( String pIndexPrefixTextForDescription, int pIndexAdjustment, T[] pArray ) {
        super( pArray );
        mIndexPrefixTextForDescription = pIndexPrefixTextForDescription;
        mIndexAdjustment = pIndexAdjustment;
    }

    @Override
    public String toString() {
        return mIndexPrefixTextForDescription + (getIndex() + mIndexAdjustment);
    }
}
