// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.independence;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.base.*;

import java8.util.function.*;

public class SingletonSupplier<T> implements Supplier<T> {
    private final T mInstance;

    public <V extends T> SingletonSupplier( @NotNull V pInstance ) {
        mInstance = Confirm.isNotNull( "Instance", pInstance );
    }

    @Override
    public synchronized @NotNull T get() {
        return mInstance;
    }
}
