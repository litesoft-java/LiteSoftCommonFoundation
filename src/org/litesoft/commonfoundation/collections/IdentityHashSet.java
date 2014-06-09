package org.litesoft.commonfoundation.collections;

import java.util.*;

public class IdentityHashSet<E> extends AbstractSet<E> {
    private final Map<E, E> zMap = new IdentityHashMap<E, E>();

    @Override
    public Iterator<E> iterator() {
        return zMap.keySet().iterator();
    }

    @Override
    public int size() {
        return zMap.size();
    }

    @Override
    public boolean add( E e ) {
        return (null == zMap.put( e, e ));
    }
}
