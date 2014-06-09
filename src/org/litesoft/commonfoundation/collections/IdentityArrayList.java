package org.litesoft.commonfoundation.collections;

import java.util.*;

public class IdentityArrayList<E> extends ArrayList<E> {
    public IdentityArrayList( int initialCapacity ) {
        super( initialCapacity );
    }

    public IdentityArrayList() {
    }

    public IdentityArrayList( Collection<? extends E> c ) {
        super( c );
    }

    @Override
    public int indexOf( Object o ) {
        for ( int i = 0; i < size(); i++ ) {
            E zE = get( i );
            if ( zE == o ) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf( Object o ) {
        for ( int i = size(); --i >= 0; ) {
            E zE = get( i );
            if ( zE == o ) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean remove( Object o ) {
        int index = indexOf( o );
        if ( index == -1 ) {
            return false;
        }
        remove( index );
        return true;
    }

    @Override
    public boolean equals( Object o ) {
        return (this == o) || ((o instanceof List) && equals( (List<?>) o ));
    }

    public boolean equals( List<?> them ) {
        if ( this != them ) {
            if ( this.size() != them.size() ) {
                return false;
            }
            Iterator<E> thisIt = this.iterator();
            Iterator<?> themIt = them.iterator();
            while ( thisIt.hasNext() ) { // Lists same size!
                if ( thisIt.next() != themIt.next() ) {
                    return false;
                }
            }
        }
        return true;
    }
}
