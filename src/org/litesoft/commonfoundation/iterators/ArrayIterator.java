// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.iterators;

import java.util.*;

/**
 * An Iterator (Read-Only) of an Array.<p>
 * <p/>
 * While this same functionality is provided by
 * Arrays.asList(array).iterator(), the tendency would be to
 * return Arrays.asList(array).  While the returned List does
 * NOT allow adds or removes, it unfortunately DOES
 * support set().  This means that the underlying Array
 * CAN BE modified.  Besides, if you don't want the List
 * object, but just the Iterator, why create it.
 *
 * @author George Smith
 * @version 1.0 7/28/01
 */

public class ArrayIterator<T> extends Iterators.AbstractReadOnly<T> {
    /**
     * Construct an Iterator for an Array.<p>
     *
     * @param pArray the Array to Iterate thru (null OK).
     */
    public ArrayIterator( T[] pArray ) {
        this( "ArrayIterator", pArray );
    }

    /**
     * Super class constructor for an Iterator for an Array.<p>
     *
     * @param pInteratorName the Name for this Iterator (for toString) (null questionable).
     * @param pArray         the Array to Iterate thru (null OK).
     */
    protected ArrayIterator( String pInteratorName, T[] pArray ) {
        zInteratorName = pInteratorName;
        arrayLen = ((zArray = pArray) == null) ? 0 : pArray.length;
    }

    private String zInteratorName;
    private T[] zArray;
    private int arrayLen, index = 0;

    /**
     * Returns <tt>true</tt>/<tt>false</tt>, depending on IF there is a
     * <i>current</i> object that <i>next()</i> will return.<p>
     *
     * @return If there is an Object available via <i>next()</i>.<p>
     *
     * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#hasNext()">java.util.Iterator#hasNext()</a>
     */
    @Override
    public final boolean hasNext() {
        return (index < arrayLen);
    }

    /**
     * Return the <i>current</i> object and advance the <i>underlying</i>
     * pointer/index.  If there is no <i>current</i> object, then a
     * NoSuchElementException is thrown.<p>
     *
     * @throws NoSuchElementException if nothing to return.<p>
     * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#next()">java.util.Iterator#next()</a>
     */
    @Override
    public final T next() {
        return hasNext() ? zArray[index++] : super.next();
    }

    /**
     * Returns a debug/human friendly String that represents this.<p>
     *
     * @return A String representation of this.
     */
    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder( zInteratorName );
        sb.append( ": " );
        if ( zArray == null ) {
            sb.append( "null" );
        } else {
            sb.append( arrayLen );
            sb.append( " elements" );
            if ( !hasNext() ) {
                sb.append( " AT END (NO MORE ELEMENTS)" );
            }
            if ( arrayLen != 0 ) {
                sb.append( '\n' );
                for ( int i = 0; i < arrayLen; i++ ) {
                    T entry = zArray[i];
                    sb.append( (i == index) ? " -> [" : "    [" );
                    sb.append( (entry == null) ? "?null?" : entry.toString() );
                    sb.append( "]\n" );
                }
            }
        }
        return sb.toString();
    }
}
