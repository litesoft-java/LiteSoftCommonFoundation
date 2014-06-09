package org.litesoft.commonfoundation.collections;

public class ImmutableMapEntry<K, V> extends AbstractMapEntry<K, V> {
    private final K mKey;
    private final V mValue;

    public ImmutableMapEntry( K pKey, V pValue ) {
        mKey = pKey;
        mValue = pValue;
    }

    public K getKey() {
        return mKey;
    }

    public V getValue() {
        return mValue;
    }

    public V setValue( V value ) {
        throw new UnsupportedOperationException();
    }
}
