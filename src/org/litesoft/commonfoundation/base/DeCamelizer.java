// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.base;

/**
 * Takes a 'CamelCased' string and produces a space separated string.
 * <p/>
 * Leading and trailing spaces are removed.
 * First Character is upper cased.
 * More than one Upper case letter in a row are considered an abbreviation (and spaces are not injected before the non-1st upper case letter).
 * A Space is injected before an upper case letter.
 */
public class DeCamelizer {
    public static String resolve( String pID ) {
        return (pID == null) ? null : new DeCamelizer( pID.length() ).process( pID );
    }

    private final StringBuilder sb;

    private DeCamelizer( int pAssumedLength ) {
        sb = new StringBuilder( pAssumedLength );
    }

    private String process( String pID ) {
        return normalizeIntoBuffer( pID ) ? deCamelize() : null;
    }

    private boolean normalizeIntoBuffer( String pID ) {
        int zSpaces = -pID.length(); // This number will force all leading spaces to be skipped
        for ( int at = 0; at < pID.length(); at++ ) {
            char c = pID.charAt( at );
            if ( (c == '_') || (c == ' ') ) {
                zSpaces++;
            } else {
                if ( zSpaces > 0 ) {
                    sb.append( ' ' );
                }
                sb.append( c );
                zSpaces = 0;
            }
        }
        return (sb.length() != 0);
    }

    private String deCamelize() {
        boolean zUpperNext = true; // To make the first character Uppercase
        boolean zLastUpper = false;
        for ( int at = 0; at < sb.length(); at++ ) {
            char c = sb.charAt( at );
            if ( zUpperNext ) {
                sb.setCharAt( at, Character.toUpperCase( c ) );
                zLastUpper = true;
                zUpperNext = false;
            } else if ( Character.isUpperCase( c ) ) {
                if ( !zLastUpper ) {
                    sb.insert( at++, ' ' );
                }
                zLastUpper = true;
            } else {
                if ( c == ' ' ) {
                    zUpperNext = true;
                }
                zLastUpper = false;
            }
        }
        return sb.toString();
    }
}
