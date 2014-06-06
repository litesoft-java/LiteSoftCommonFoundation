// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.interators;

import org.litesoft.commonfoundation.base.*;

import java.util.*;

/**
 * An Iterator helper class.
 *
 * @author George Smith
 * @version 1.0 7/28/01
 */

public class Iterators
{
    public static <T> Iterator<T> empty()
    {
        return Cast.it( NULL_INSTANCE );
    }

    public static <T> Iterator<T> deNull( Iterator<T> pIterator )
    {
        if ( pIterator != null )
        {
            return pIterator;
        }
        return empty();
    }

    /**
     * There are two points for having this class:<p>
     * <p/>
     * First, that all Iterators need to be able to throw NoSuchElementException if
     * next is inappropriately called.  The easiest way is to simply implement a
     * dumb next at a superclass level that always throws it.<p>
     * <p/>
     * Second, the SUN/Java Iterators should NOT have supported <i>remove()</i>.
     * The GOF (Gang Of Four) Iterator not only provided more functionality
     * (supporting both multiple reads of the current item AND the ability to pass
     * over the collection multiple times), but provided a nice <i>read only</i>
     * view of an arbitrarily stored collection.  To <i>restore</i> the read only
     * view aspect of an Iterator, this class provides a <i>final</i> implementation
     * of <i>remove()</i>.<p>
     *
     * @author George Smith
     * @version 1.0 7/28/01
     * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html">java.util.Iterator</a>
     */
    public static abstract class AbstractReadOnly<T> implements Iterator<T>,
                                                                Disposable
    {
        /**
         * hasNext() is part of the Iterator interface.  It is abstract here
         * because there is no reasonable, common, implementation and to
         * remind the developer who extends this class that they, at a
         * minimum, need to implement it.<p>
         *
         * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#hasNext()">java.util.Iterator#hasNext()</a>
         */
        @Override
        abstract public boolean hasNext();

        /**
         * All most all Iterators will need to throw NoSuchElementException
         * at some point, so this is simply a convenience implementation.<p>
         *
         * @throws NoSuchElementException
         * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#next()">java.util.Iterator#next()</a>
         */
        @Override
        public T next()
        {
            throw new NoSuchElementException();
        }

        /**
         * As mentioned above, the SUN/Java Iterators should NOT have supported
         * <i>remove()</i>.<p>
         * <p/>
         * Therefor, this method is a <i>final</i> version that throws
         * UnsupportedOperationException.<p>
         *
         * @throws UnsupportedOperationException
         * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#remove()">java.util.Iterator.remove()</a>
         */
        @Override
        public final void remove()
        {
            throw new UnsupportedOperationException();
        }

        @Override
        public void dispose()
        {
        }
    }

    /**
     * This class's purpose is to be extended by any concrete Iterator that is
     * wrapping/decorating another Iterator.<p>
     *
     * @author George Smith
     * @version 1.0 7/28/01
     */
    public static abstract class AbstractWrapping<T> extends AbstractReadOnly<T>
    {
        /**
         * Construct an Iterator wrapping Iterator.<p>
         *
         * @param pIterator Iterator to be <i>wrapped</i> (!null).<p>
         *
         * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html">java.util.Iterator</a>
         */
        protected AbstractWrapping( Iterator<T> pIterator )
        {
            mIterator = deNull( pIterator );
        }

        /**
         * Reference to the <i>wrapped</i> Iterator.
         */
        protected Iterator<T> mIterator;

        /**
         * This hasNext() simply forwards the request to the <i>wrapped</i> Iterator.<p>
         *
         * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#hasNext()">java.util.Iterator#hasNext()</a>
         */
        @Override
        public boolean hasNext()
        {
            return mIterator.hasNext();
        }

        /**
         * This next() simply forwards the request to the <i>wrapped</i> Iterator.<p>
         *
         * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#next()">java.util.Iterator#next()</a>
         */
        @Override
        public T next()
        {
            return mIterator.next();
        }

        @Override
        public void dispose()
        {
            if ( mIterator instanceof Disposable )
            {
                ((Disposable) mIterator).dispose();
            }
            mIterator = empty();
            super.dispose();
        }

        @Override
        protected void finalize()
                throws Throwable
        {
            dispose();
            super.finalize();
        }
    }

    /**
     * This class's purpose is to be extended by a concrete Filtering Iterator
     * that is wrapping/decorating another Iterator.  The Filtering works
     * by looking ahead for an entry that is acceptable.  This acceptability
     * is determined by the <i>keepThis</i> method.<p>
     *
     * @author George Smith
     * @version 1.0 7/28/01
     * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html">java.util.Iterator</a>
     */

    public static abstract class AbstractFiltering<T> extends AbstractWrapping<T>
    {
        /**
         * Construct a Filtering (of another Iterator) Iterator.<p>
         *
         * @param pIterator Iterator to be <i>Filtered</i> (!null).<p>
         *
         * @see AbstractWrappingIterator
         * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html">java.util.Iterator</a>
         */
        protected AbstractFiltering( Iterator<T> pIterator )
        {
            super( pIterator );
        }

        private T lookAheadObject = null;
        private boolean lookAheadValid = false;

        /**
         * This method is the <i>heart</i> of a Filtering Iterator.
         * It determines if an Object from the underlying Iterator
         * is to be kept or tossed.<p>
         * <p/>
         * It is abstract here because there is no reasonable, common,
         * implementation and to remind the developer who extends
         * this class that they need to implement it.<p>
         *
         * @param pPossibleValue Object reference to test for keeping (not tossing).<p>
         *
         * @return <tt>true</tt> if the <i>pPossibleValue</i> should be kept (not tossed).
         */
        abstract protected boolean keepThis( T pPossibleValue );

        /**
         * Returns true if the iteration has more elements.<p>
         * <p/>
         * This method relies on a look-ahead buffer and keepThis() to
         * implement the filtering.<p>
         *
         * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#hasNext()">java.util.Iterator#hasNext()</a>
         * @see #keepThis(Object)
         */
        @Override
        public final boolean hasNext()
        {
            while ( !lookAheadValid && super.hasNext() )
            {
                lookAheadValid = keepThis( lookAheadObject = super.next() );
            }
            return lookAheadValid;
        }

        /**
         * Returns the next element in the interation.
         * <p/>
         * This method relies on hasNext (and a look-ahead buffer) to
         * implement the filtering.<p>
         *
         * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#next()">java.util.Iterator#next()</a>
         * @see #next()
         */
        @Override
        public final T next()
        {
            if ( !hasNext() )
            {
                super.next(); // throw exception
            }
            lookAheadValid = false;
            return lookAheadObject;
        }

        @Override
        public void dispose()
        {
            lookAheadObject = null;
            super.dispose();
        }
    }

    /**
     * Returns a debug/human friendly <i>label</i>ed String that represents
     * the <i>iterator</i>.<p>
     *
     * @param pIterator the Iterator to dump.
     * @param pLabel    the label for the Iterator.<p>
     *
     * @return A <i>label</i>ed String that represents <i>iterator</i>.
     */
    public static String toString( Iterator pIterator, String pLabel )
    {
        StringBuilder sb = new StringBuilder();
        if ( pLabel != null )
        {
            sb.append( pLabel ).append( ": " );
        }
        if ( pIterator == null )
        {
            sb.append( "null" );
        }
        else
        {
            appendElementsWithCount( sb, pIterator );
        }
        return sb.toString();
    }

    private static void appendElementsWithCount( StringBuilder pSb, Iterator pIterator )
    {
        StringBuilder zElements = new StringBuilder();
        int elements = 0;
        if ( pIterator.hasNext() )
        {
            zElements.append( ":\n" );
            while ( pIterator.hasNext() )
            {
                elements++;
                Object entry = pIterator.next();
                zElements.append( "    [" );
                zElements.append( (entry == null) ? "?null?" : entry.toString() );
                zElements.append( "]\n" );
            }
        }
        pSb.append( elements );
        pSb.append( " elements" );
        pSb.append( zElements );
    }

    private Iterators()
    {
    }

    private static final Iterator NULL_INSTANCE = new AbstractReadOnly()
    {
        /**
         * Returns <tt>false</tt>, because there is <b>never</b> another element.<p>
         * <p/>
         * Note: <tt>next</tt> will throw an exception.<p>
         *
         * @return <tt>false</tt>.<p>
         *
         * @see <a href="http://java.sun.com/j2se/1.3/docs/api/java/lang/Util/Iterator.html#hasNext()">java.util.Iterator#hasNext()</a>
         */
        @Override
        public boolean hasNext()
        {
            return false;
        }

        /**
         * Returns a debug/human friendly String that represents this.<p>
         *
         * @return A String representation of this.
         */
        @Override
        public String toString()
        {
            return "NullIterator";
        }
    };
}
