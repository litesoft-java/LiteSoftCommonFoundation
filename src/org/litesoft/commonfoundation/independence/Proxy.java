package org.litesoft.commonfoundation.independence;

import java8.util.function.*;

public class Proxy<T> implements Supplier<T> {
    private T mInstance;

    public Proxy( T pInstance ) {
        mInstance = pInstance;
    }

    public T get() {
        return mInstance;
    }

    public void set( T pInstance ) {
        mInstance = pInstance;
    }
}
