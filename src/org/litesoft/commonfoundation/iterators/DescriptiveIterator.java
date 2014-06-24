package org.litesoft.commonfoundation.iterators;

import java.util.*;

/**
 * A Descriptive Iterator provides a toString method that Prepends "Text For Description" to the Iterator's "index".
 *
 * Note: The "index" is to the entry that WILL be returned by the "next" method.
 * As such before the first call to "next" the "index" is "0", and immediately after the first call to "next" the "index" is "1".
 */
public interface DescriptiveIterator<T> extends Iterator<T> {
}
