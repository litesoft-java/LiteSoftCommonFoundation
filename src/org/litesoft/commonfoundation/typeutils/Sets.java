package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;

import java.util.*;

public class Sets {
    public static <T> HashSet<T> newHashSet() {
        return new HashSet<T>();
    }

    public static <T> HashSet<T> newHashSet( int zInitialSize ) {
        return new HashSet<T>( Math.max( 1, zInitialSize ) );
    }

    public static <T> HashSet<T> newHashSet( Collection<T> pEntries ) {
        HashSet<T> zSet = newHashSet();
        add( zSet, pEntries );
        return zSet;
    }

    public static <T> HashSet<T> newHashSet( T... pInitialEntries ) {
        HashSet<T> zSet = new HashSet<T>();
        add( zSet, pInitialEntries );
        return zSet;
    }

    public static <T> Set<T> copy( Set<T> pSet ) {
        Set<T> zCopy = newHashSet();
        zCopy.addAll( ConstrainTo.notNull( pSet ) );
        return zCopy;
    }

    public static <T> Set<T> add( Set<T> pSet, T... pToAdd ) {
        if ( pToAdd != null ) {
            for ( T zT : pToAdd ) {
                if ( zT != null ) {
                    pSet.add( zT );
                }
            }
        }
        return pSet;
    }

    public static <T> Set<T> add( Set<T> pSet, Collection<T> pToAdd ) {
        if ( pToAdd != null ) {
            pSet.addAll( pToAdd );
        }
        return pSet;
    }
}
