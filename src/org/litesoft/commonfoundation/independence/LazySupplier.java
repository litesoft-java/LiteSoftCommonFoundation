// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.independence;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.base.*;

import java8.util.function.*;

public abstract class LazySupplier<T> implements Supplier<T> {
    private T mInstance;

    @Override
    public synchronized @NotNull T get() {
        if ( mInstance == null ) {
            mInstance = Confirm.isNotNull( "create", create() );
        }
        return mInstance;
    }

    protected abstract @NotNull T create();
}
