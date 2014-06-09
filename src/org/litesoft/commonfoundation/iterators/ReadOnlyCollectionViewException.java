package org.litesoft.commonfoundation.iterators;

/**
 * The SUN/Java Iterators should NOT have supported <i>remove()</i>.
 * The GOF (Gang Of Four) Iterator not only provided more functionality
 * (supporting both multiple reads of the current item AND the ability to pass
 * over the collection multiple times), but provided a nice <i>read only</i>
 * view of an arbitrarily stored collection.
 *
 * @author George Smith
 * @version 1.0 7/28/01
 */
public class ReadOnlyCollectionViewException extends UnsupportedOperationException {
    public ReadOnlyCollectionViewException() {
        super( "Mutable operations on 'views' were a BAD Idea!" );
    }
}
