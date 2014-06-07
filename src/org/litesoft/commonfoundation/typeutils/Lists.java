// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import java.util.*;

public class Lists {
    @SuppressWarnings("Convert2Diamond")
    public static <T> LinkedList<T> newLinkedList() {
        return new LinkedList<T>();
    }

    @SuppressWarnings("Convert2Diamond")
    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }

    @SuppressWarnings("Convert2Diamond")
    public static <T> ArrayList<T> newArrayList( int pInitialSize ) {
        return new ArrayList<T>( pInitialSize );
    }

    public static <T> List<T> empty() {
        return Collections.emptyList();
    }

    public static <T> List<T> deNull( List<T> pToCheck ) {
        if ( pToCheck == null ) {
            pToCheck = empty();
        }
        return pToCheck;
    }

    public static <T> List<T> deNullMutable( List<T> pToCheck ) {
        if ( pToCheck == null ) {
            pToCheck = newArrayList();
        }
        return pToCheck;
    }

    public static <T> List<T> deNullUnmodifiable( List<T> pToCheck ) {
        if ( pToCheck == null ) {
            return empty();
        }
        return Collections.unmodifiableList( pToCheck );
    }

    public static boolean isNullOrEmpty( List<?> pToCheck ) {
        return (pToCheck == null || pToCheck.isEmpty());
    }

    public static boolean isNotNullOrEmpty( List<?> pToCheck ) {
        return (pToCheck != null && !pToCheck.isEmpty());
    }

    public static void assertNotNullNotEmpty( String pErrorMessage, List<?> pToAssert )
            throws IllegalArgumentException {
        if ( isNullOrEmpty( pToAssert ) ) {
            Strings.errorNullOrEmpty( pErrorMessage, "Collection" );
        }
    }

    public static <T> List<T> of( T pEntry ) {
        ArrayList<T> rv = newArrayList();
        rv.add( pEntry );
        return rv;
    }

    public static <T> List<T> from( Collection<T> pCollection ) {
        ArrayList<T> rv = newArrayList();
        if ( pCollection != null ) {
            rv.addAll( pCollection );
        }
        return rv;
    }

    public static <T> List<T> from( Enumeration<T> pEnumeration ) {
        ArrayList<T> rv = newArrayList();
        while ( pEnumeration.hasMoreElements() ) {
            rv.add( pEnumeration.nextElement() );
        }
        return rv;
    }

    public static <T> List<T> ofUnmodifiable( T pEntry ) {
        return deNullUnmodifiable( of( pEntry ) );
    }

    public static <T> List<T> fromUnmodifiable( Collection<T> pCollection ) {
        return deNullUnmodifiable( from( pCollection ) );
    }

    public static <T> List<T> fromUnmodifiable( Enumeration<T> pEnumeration ) {
        return deNullUnmodifiable( from( pEnumeration ) );
    }

    public static String singleQuoteToString( List<String> pStrings ) {
        StringBuilder sb = new StringBuilder();
        if ( pStrings != null ) {
            char prefix = '[';
            if ( pStrings.size() == 0 ) {
                sb.append( prefix );
            } else {
                for ( String zString : pStrings ) {
                    sb.append( prefix );
                    sb.append( '\'' );
                    sb.append( zString );
                    sb.append( '\'' );
                    prefix = ',';
                }
            }
            sb.append( ']' );
        }
        return sb.toString();
    }
}
