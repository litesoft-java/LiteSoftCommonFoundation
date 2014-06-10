// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;

import java.util.*;

@SuppressWarnings("Convert2Diamond")
public class Sets {
    public static <T> HashSet<T> newHashSet() {
        return new HashSet<T>();
    }

    public static <T> HashSet<T> newHashSet( int zInitialSize ) {
        return new HashSet<T>( Math.max( 1, zInitialSize ) );
    }

    @SuppressWarnings("unchecked")
    public static <T> HashSet<T> newHashSet( T... pInitialEntries ) {
        HashSet<T> zSet = newHashSet();
        add( zSet, pInitialEntries );
        return zSet;
    }

    public static <T> Set<T> empty() {
        return Collections.emptySet();
    }

    public static <T> Set<T> copy( Set<T> pSet ) {
        Set<T> zCopy = newHashSet();
        zCopy.addAll( ConstrainTo.notNull( pSet ) );
        return zCopy;
    }

    @SuppressWarnings("unchecked")
    public static <T> void add( Set<T> pSet, T... pToAdd ) {
        if ( pToAdd != null ) {
            for ( T zT : pToAdd ) {
                if ( zT != null ) {
                    pSet.add( zT );
                }
            }
        }
    }

    public static boolean isNullOrEmpty( Set<?> pToCheck ) {
        return (pToCheck == null || pToCheck.isEmpty());
    }

    public static boolean isNotNullOrEmpty( Set<?> pToCheck ) {
        return (pToCheck != null && !pToCheck.isEmpty());
    }
}
