// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.independence;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;
import java.util.function.*;

@SuppressWarnings("Convert2Diamond")
public class Manager {
    private final Map<Class<?>, Supplier<?>> mInstanceSuppliersByClass = Maps.newHashMap();

    public synchronized @Nullable <T> T getOptional( @NotNull Class<T> pKey ) {
        confirmKeyNotNull( pKey );
        Supplier<?> zSupplier;
        synchronized ( mInstanceSuppliersByClass ) {
            zSupplier = mInstanceSuppliersByClass.get( pKey );
        }
        return Cast.it( (zSupplier == null) ? null : zSupplier.get() );
    }

    public @NotNull <T> T getRequired( @NotNull Class<T> pKey ) {
        return confirmInstanceNotNull( pKey, getOptional( pKey ) );
    }

    public @NotNull <T> T getRequired( @NotNull Class<T> pKey, @NotNull Supplier<T> pSupplier ) {
        confirmKeyNotNull( pKey );
        confirmSupplierNotNull( pSupplier );
        Supplier<?> zSupplier;
        synchronized ( mInstanceSuppliersByClass ) {
            if ( null == (zSupplier = mInstanceSuppliersByClass.get( pKey )) ) {
                mInstanceSuppliersByClass.put( pKey, zSupplier = pSupplier );
            }
        }
        return confirmInstanceNotNull( pKey, zSupplier.get() );
    }

    /**
     * Initiate a registration where the Key must NOT already be registered.
     */
    public @NotNull <T> EntryBuilder<T> register( @NotNull Class<T> pKey ) {
        return new EntryBuilder<T>( confirmKeyNotNull( pKey ), false );
    }

    /**
     * Initiate a registration where the Key may have been already registered.
     */
    public @NotNull <T> EntryBuilder<T> forceRegister( @NotNull Class<T> pKey ) {
        return new EntryBuilder<T>( pKey, true );
    }

    /**
     * Remove the registration, if any, for the Key.
     *
     * @return true if Key was Registered
     */
    public <T> boolean remove( Class<T> pKey ) {
        synchronized ( mInstanceSuppliersByClass ) {
            return (null != mInstanceSuppliersByClass.remove( pKey ));
        }
    }

    public void clear() {
        synchronized ( mInstanceSuppliersByClass ) {
            mInstanceSuppliersByClass.clear();
        }
    }

    private <T> Class<T> confirmKeyNotNull( Class<T> pKey ) {
        return Confirm.isNotNull( "Key", pKey );
    }

    private void confirmSupplierNotNull( Supplier<?> pSupplier ) {
        Confirm.isNotNull( "Supplier", pSupplier );
    }

    private <T> T confirmInstanceNotNull( Class<T> pKey, Object pInstance ) {
        if ( pInstance == null ) {
            throw new IllegalArgumentException( "No 'Instance' available for key: " + pKey );
        }
        return Cast.it( pInstance );
    }

    private <T> void addEntryForced( @NotNull Class<T> pKey, @NotNull Supplier<T> pSupplier ) {
        synchronized ( mInstanceSuppliersByClass ) {
            mInstanceSuppliersByClass.put( pKey, pSupplier );
        }
    }

    private <T> void addEntry( @NotNull Class<T> pKey, @NotNull Supplier<T> pSupplier ) {
        synchronized ( mInstanceSuppliersByClass ) {
            if ( !mInstanceSuppliersByClass.containsKey( pKey ) ) {
                mInstanceSuppliersByClass.put( pKey, pSupplier );
                return;
            }
        }
        throw new IllegalArgumentException( "Duplicate registration for key: " + pKey );
    }

    public class EntryBuilder<T> {
        private final Class<T> mKey;
        private final boolean mForceRegistration;

        private EntryBuilder( Class<T> pKey, boolean pForceRegistration ) {
            mKey = pKey;
            mForceRegistration = pForceRegistration;
        }

        public void with( @NotNull Supplier<T> pSupplier ) {
            confirmSupplierNotNull( pSupplier );
            if ( mForceRegistration ) {
                addEntryForced( mKey, pSupplier );
            } else {
                addEntry( mKey, pSupplier );
            }
        }

        public <V extends T> void with( @NotNull V pInstance ) {
            with( new SingletonSupplier<T>( pInstance ) );
        }
    }
}
