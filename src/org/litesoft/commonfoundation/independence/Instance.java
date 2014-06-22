// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.independence;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.base.*;

public class Instance {
    private static final Manager MANAGER = new Manager();

    public static @NotNull <T> T of( @NotNull Class<T> pKey ) {
        return MANAGER.getRequired( Confirm.isNotNull( "Key", pKey ) );
    }

    public static @Nullable <T> T ofOptional( @NotNull Class<T> pKey ) {
        return MANAGER.getOptional( Confirm.isNotNull( "Key", pKey ) );
    }

    /**
     * Initiate a registration where the Key must NOT already be registered.
     */
    public static @NotNull <T> Manager.EntryBuilder<T> register( @NotNull Class<T> pKey ) {
        return MANAGER.register( Confirm.isNotNull( "Key", pKey ) );
    }

    public static @NotNull Manager manager() {
        return MANAGER;
    }
}
