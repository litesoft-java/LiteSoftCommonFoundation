package org.litesoft.commonfoundation.collections;

import java.util.*;

public abstract class AbstractMapEntry<K, V> implements Map.Entry<K, V> {
    @Override
    public final int hashCode() {
        return getKey().hashCode(); // Skipping the Value.
    }

    @Override
    public final boolean equals( Object obj ) {
        return (this == obj) || ((obj instanceof Map.Entry) && equals( (Map.Entry) obj ));
    }

    public final boolean equals( Map.Entry them ) {
        return (this == them) || ((them != null)
                                  && this.getKey().equals( them.getKey() )
                                  && this.getValue().equals( them.getValue() ));
    }

    public final String toString() {
        return getKey() + "=" + getValue();
    }
}
