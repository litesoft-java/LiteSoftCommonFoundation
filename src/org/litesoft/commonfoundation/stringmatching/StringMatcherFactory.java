// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.stringmatching;

import org.litesoft.commonfoundation.typeutils.*;

public class StringMatcherFactory {
    /**
     * Is the string 'pInQuestion' made up of the 'pParts' where if only 1, then must be equal, otherwise
     * the pInQuestion.startsWith(first) && pInQuestion.endsWith(last) && the middle must be found in
     * order with no overlap between.
     *
     * @param pParts - Null treated as empty & Null Elements treated as ""
     *
     * @return Appropriate matcher or 'null' if pParts is empty and all elements are empty
     */
    public static StringMatcher createEquals( String... pParts ) {
        return createEquals( false, pParts );
    }

    /**
     * Is the string 'pInQuestion' made up of the 'pParts' where if only 1, then must be equal, otherwise
     * the pInQuestion.startsWith(first) && pInQuestion.endsWith(last) && the middle must be found in
     * order with no overlap between.
     *
     * @param pParts - Null treated as empty & Null Elements treated as ""
     *
     * @return Appropriate matcher or 'null' if pParts is empty and all elements are empty
     */
    public static StringMatcher createEqualsIgnoreCase( String... pParts ) {
        return createEquals( true, pParts );
    }

    /**
     * Is the string 'pInQuestion' made up of the 'pParts' where if only 1, then must be equal, otherwise
     * the pInQuestion.startsWith(first) && pInQuestion.endsWith(last) && the middle must be found in
     * order with no overlap between.
     *
     * @param pIgnoreCase - true if Character "case" should be ignored
     * @param pParts      - Null treated as empty & Null Elements treated as ""
     *
     * @return Appropriate matcher or 'null' if pParts is empty and all elements are empty
     */
    public static StringMatcher createEquals( boolean pIgnoreCase, String... pParts ) {
        return LLcreate( pParts, false, pIgnoreCase );
    }

    /**
     * Is the string 'pInQuestion' made up of the 'pParts' where if only 1, then treated as a "StartsWith", otherwise
     * the pInQuestion.startsWith(first) && pInQuestion.endsWith(last) && the middle must be found in
     * order with no overlap between.
     *
     * @param pParts - Null treated as empty & Null Elements treated as ""
     *
     * @return Appropriate matcher or 'null' if pParts is empty and all elements are empty
     */
    public static StringMatcher createStartsWith( String... pParts ) {
        return createStartsWith( false, pParts );
    }

    /**
     * Is the string 'pInQuestion' made up of the 'pParts' where if only 1, then treated as a "StartsWith", otherwise
     * the pInQuestion.startsWith(first) && pInQuestion.endsWith(last) && the middle must be found in
     * order with no overlap between.
     *
     * @param pParts - Null treated as empty & Null Elements treated as ""
     *
     * @return Appropriate matcher or 'null' if pParts is empty and all elements are empty
     */
    public static StringMatcher createStartsWithIgnoreCase( String... pParts ) {
        return createStartsWith( true, pParts );
    }

    /**
     * Is the string 'pInQuestion' made up of the 'pParts' where if only 1, then treated as a "StartsWith", otherwise
     * the pInQuestion.startsWith(first) && pInQuestion.endsWith(last) && the middle must be found in
     * order with no overlap between.
     *
     * @param pIgnoreCase - true if Character "case" should be ignored
     * @param pParts      - Null treated as empty & Null Elements treated as ""
     *
     * @return Appropriate matcher or 'null' if pParts is empty and all elements are empty
     */
    public static StringMatcher createStartsWith( boolean pIgnoreCase, String... pParts ) {
        return LLcreate( pParts, true, pIgnoreCase );
    }

    private static StringMatcher LLcreate( String[] pParts, boolean pSingleIsNotEqualsButStartsWith, boolean pIgnoreCase ) {
        pParts = Strings.deNull( pParts );
        String[] zParts = new String[pParts.length];
        int zPartsLength = 0;
        for ( int i = 0; i < pParts.length; i++ ) {
            String part = pParts[i];
            if ( part == null ) {
                part = "";
            }
            zParts[i] = pIgnoreCase ? part.toLowerCase() : part;
            zPartsLength += part.length();
        }
        if ( zPartsLength == 0 ) {
            return null;
        }
        switch ( zParts.length ) {
            case 1:
                if ( pSingleIsNotEqualsButStartsWith ) {
                    return new StartsWithStringMatcher( zPartsLength, pIgnoreCase, zParts[0] );
                } else {
                    return new EqualsStringMatcher( zPartsLength, pIgnoreCase, zParts[0] );
                }
            case 2:
                if ( zParts[1].length() == 0 ) {
                    return new StartsWithStringMatcher( zPartsLength, pIgnoreCase, zParts[0] );
                }
                if ( zParts[0].length() == 0 ) {
                    return new EndsWithStringMatcher( zPartsLength, pIgnoreCase, zParts[1] );
                }
                return new StartsAndEndsWithStringMatcher( zPartsLength, pIgnoreCase, zParts[0], zParts[1] );
            default:
                return new PartsStringMatcher( zPartsLength, pIgnoreCase, zParts );
        }
    }
}
