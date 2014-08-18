package org.litesoft.commonfoundation.base;

import org.litesoft.commonfoundation.exceptions.*;

@SuppressWarnings("DuplicateThrows")
public interface KeyValueStringsLineParser extends KeyValueStringsFactory {
    /**
     * Parse the line and extract the Key & Value into the KeyValueStrings.
     * <p/>
     * If the line is not significant, a null is returned!
     *
     * @throws MalformedException       - (which extends IllegalArgumentException) if the line is Malformed.
     * @throws IllegalArgumentException - if the extracted Key or Value are unacceptable.
     */
    KeyValueStrings parse( String pLine )
            throws MalformedException, IllegalArgumentException;
}
