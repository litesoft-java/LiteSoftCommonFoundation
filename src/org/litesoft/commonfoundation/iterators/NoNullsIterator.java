// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.iterators;

import java.util.*;

/**
 * An Iterator, of another wrapped Iterator, that will NOT return any null values.<p>
 *
 * @author George Smith
 * @version 1.0 31Aug2010
 */

public final class NoNullsIterator<T> extends Iterators.AbstractFiltering<T>
{
    /**
     * Construct an Iterator (by wrapping another Iterator) that will NOT return any null values.<p>
     *
     * @param pIterator the wrapped Iterator (!null).
     */
    public NoNullsIterator( Iterator<T> pIterator )
            throws NullPointerException
    {
        super( pIterator );
    }

    @Override
    protected boolean keepThis( T pPossibleValue )
    {
        return pPossibleValue != null;
    }
}