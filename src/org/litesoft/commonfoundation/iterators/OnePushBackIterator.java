// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.iterators;

import java.util.*;

/**
 * An Iterator, of another wrapped Iterator, that adds the ability to
 * <i>pushBack</i> (or repeat) any ONE object just retrieved via
 * <i>next</i>.<p>
 *
 * @author George Smith
 * @version 1.0 7/28/01
 */

public final class OnePushBackIterator<T> extends Iterators.AbstractWrapping<T> {
    /**
     * Construct an Iterator (by wrapping another Iterator) that adds the
     * ability of a one-deep push back.<p>
     *
     * @param pIterator the wrapped Iterator (!null).
     */
    public OnePushBackIterator( Iterator<T> pIterator )
            throws NullPointerException {
        super( pIterator );
    }

    /**
     * Flag <i>this</i> to return from next() the last thing (Object) that
     * next() returned.
     *
     * @return an <tt>true/false</tt> indicating if the push-back succeeded.<p>
     */
    public boolean pushBack() {
        return (lastEverSet && !lastValid) && (lastValid = true); // Note Assignment!
    }

    /**
     * Returns true if the iteration has more elements.
     *
     * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#hasNext()">java.util.Iterator#hasNext()</a>
     */
    @Override
    public boolean hasNext() {
        return lastValid || super.hasNext();
    }

    /**
     * Returns the next element in the interation.
     *
     * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#next()">java.util.Iterator#next()</a>
     */
    @Override
    public T next() {
        if ( !hasNext() ) {
            super.next(); // throw exception
        }
        if ( lastValid ) {
            lastValid = false;
            return lastValue;
        }
        lastEverSet = true;
        return lastValue = super.next(); // Note Assignment!
    }

    private T lastValue = null;
    private boolean lastValid = false;
    private boolean lastEverSet = false;
}
