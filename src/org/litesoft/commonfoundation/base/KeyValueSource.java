package org.litesoft.commonfoundation.base;

public interface KeyValueSource<K, V> {
    K getKey();

    V getValue();
}
