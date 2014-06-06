// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.interators;

import java.util.*;

/**
 * An Iterator that will NEVER throw a NoSuchElementException
 * when next() is called.  The primary utility (discovered to date) for this
 * class is for converting a variable number (ussually <= n), but ordered
 * "stream" of things (the wrapped Iterator), into another form where you
 * do not want or care (or even want to care) if there are enough of them.<p>
 *
 * @author George Smith
 * @version 1.0 7/28/01
 */

public final class InfiniteNextIterator<T> extends Iterators.AbstractWrapping<T>
{
    /**
     * Construct an Iterator that will NEVER throw a NoSuchElementException
     * when next() is called.<p>
     *
     * @param pIterator       the wrapped Iterator (!null).
     * @param pBeyondEndValue the value to return from next() when
     *                        NoSuchElementException would have been thown (null OK).
     */
    public InfiniteNextIterator( Iterator<T> pIterator, T pBeyondEndValue )
            throws NullPointerException
    {
        super( pIterator );
        zBeyondEndValue = pBeyondEndValue;
    }

    /**
     * Construct an Iterator that will NEVER throw a NoSuchElementException
     * when next() is called.<p>
     *
     * @param pIterator the wrapped Iterator (!null).
     */
    public InfiniteNextIterator( Iterator<T> pIterator )
            throws NullPointerException
    {
        this( pIterator, null );
    }

    /**
     * Returns the next object in the wrapped Iterator while there is more
     * (hasNext), otherwise returns the BeyondEndValue (which defaults to null).<p>
     * <p/>
     * Note: This implementation of next() will NEVER throw a NoSuchElementException.<p>
     *
     * @return the next object in the stream or BeyondEndValue.<p>
     *
     * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#next()">java.util.Iterator#next()</a>
     */
    @Override
    public T next()
    {
        return hasNext() ? super.next() : zBeyondEndValue;
    }

    private T zBeyondEndValue;
}
