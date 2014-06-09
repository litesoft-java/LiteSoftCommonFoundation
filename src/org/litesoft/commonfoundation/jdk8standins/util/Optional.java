package org.litesoft.commonfoundation.jdk8standins.util;

import org.litesoft.commonfoundation.jdk8standins.function.*;

import java.util.*;

public class Optional<T> {
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> empty() {
        return (Optional<T>) (EMPTY);
    }

    public static <T> Optional<T> of( T pValue ) {
        if ( pValue == null ) {
            throw new NullPointerException();
        }
        return new Optional<T>( pValue );
    }

    public static <T> Optional<T> ofNullable( T pValue ) {
        if ( pValue == null ) {
            return empty();
        }
        return new Optional<T>( pValue );
    }

    public T get() {
        if ( !isPresent() ) {
            throw new NoSuchElementException();
        }
        return mValue;
    }

    public boolean isPresent() {
        return (mValue != null);
    }

    public T orElse( T pOther ) {
        return isPresent() ? mValue : pOther;
    }

    public T orElseGet( Supplier<? extends T> pOtherSupplier ) {
        return isPresent() ? mValue : pOtherSupplier.get();
    }

    private final T mValue;

    private Optional( T pValue ) {
        mValue = pValue;
    }

    private static final Optional<Object> EMPTY = new Optional<Object>( null );
}
