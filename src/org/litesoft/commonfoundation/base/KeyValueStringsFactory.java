package org.litesoft.commonfoundation.base;

public interface KeyValueStringsFactory {
    /**
     * It is up to the implementation to determine what are the rules for acceptable Key & Value values.
     *
     * @return !null
     *
     * @throws IllegalArgumentException - if either the Key or Value are unacceptable
     */
    KeyValueStrings create( String pKey, String pValue )
            throws IllegalArgumentException;
}
