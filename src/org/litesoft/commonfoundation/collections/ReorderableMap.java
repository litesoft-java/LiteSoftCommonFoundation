package org.litesoft.commonfoundation.collections;

import java.util.*;

/**
 * @param <T> This object will allow storing objects in the order of insertion.
 *            In addition , this object will also allow for reordering the elements in the list
 *            The map stores the actual objects and prevents duplicates
 *
 * @author palla
 */
public class ReorderableMap<T> {
    private Map<String, T> map;
    private List<String> order;
    private long sequence;

    public ReorderableMap() {
        map = new HashMap<String, T>();
        order = new ArrayList<String>();
        sequence = 1;
    }

    public void clear() {
        map.clear();
        order.clear();
    }

    public boolean isEmpty() {
        return (map.isEmpty());
    }

    public List<T> getOrderedElements() {
        List<T> orderedElements = new LinkedList<T>();
        for ( String name : order ) {
            if ( map.containsKey( name ) ) {
                orderedElements.add( map.get( name ) );
            }
        }
        return orderedElements;
    }

    public List<String> getKeys() {
        return new ArrayList<String>( order );
    }

    public LinkedHashMap<String, T> getOrderedMap() {
        LinkedHashMap<String, T> orderedElements = new LinkedHashMap<String, T>();
        for ( String key : order ) {
            orderedElements.put( key, map.get( key ) );
        }
        return orderedElements;
    }

    public boolean addElement( String key, T value ) {
        boolean added = false;
        if ( !map.containsKey( key ) ) {
            map.put( key, value );
            order.add( key );
            added = true;
        }
        return added;
    }

    public void removeElement( String key ) {
        order.remove( key );
        map.remove( key );
    }

    public void reOrder( String key, boolean up ) {
        int index = order.indexOf( key );
        int swapIndex = -1;
        if ( index >= 0 ) {
            if ( up ) {
                swapIndex = index - 1;
            } else {
                swapIndex = index + 1;
            }
        }
        if ( swapIndex >= 0 && swapIndex < order.size() ) {
            String swapAttribute = order.get( swapIndex );
            order.set( swapIndex, key );
            order.set( index, swapAttribute );
        }
    }

    public String getNextSequence() {
        return (String.valueOf( sequence++ ));
    }
}
