// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.base;

import org.litesoft.commonfoundation.annotations.*;

@SuppressWarnings("unchecked")
public class Cast {
    public static
    @Nullable
    <T> T it( @Nullable Object o ) {
        return (T) o;
    }

    public static <T> Class<T> tryClass( Class<?> pClass ) {
        return (Class<T>) pClass;
    }

    public static <T> T tryObject( Class<T> pType, Object pInstance ) {
        return (T) pInstance;
    }
}
