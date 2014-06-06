package org.litesoft.commonfoundation.base;

import org.litesoft.commonfoundation.annotations.*;

public class Cast
{
    @SuppressWarnings({"unchecked"})
    public static @Nullable <T> T it( @Nullable Object o )
    {
        return (T) o;
    }
}
