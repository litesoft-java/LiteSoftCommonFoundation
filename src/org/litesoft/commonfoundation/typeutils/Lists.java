// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.collections.*;

import java.util.*;

@SuppressWarnings("Convert2Diamond")
public class Lists {
    public static Integer max( List<Integer> pIntegers ) {
        if ( Currently.isNullOrEmpty( pIntegers ) ) {
            return null;
        }
        Iterator<Integer> zIterator = pIntegers.iterator();
        Integer z1stNonNull = zIterator.next();
        for (; z1stNonNull == null; z1stNonNull = zIterator.next() ) {
            if ( !zIterator.hasNext() ) {
                return null;
            }
        }
        int zMax = z1stNonNull;
        while ( zIterator.hasNext() ) {
            Integer zInt = zIterator.next();
            if ( zInt != null ) {
                zMax = Math.max( zMax, zInt );
            }
        }
        return zMax;
    }

    public interface Provider<T> {
        List<T> get();
    }

    public static <T> IdentityArrayList<T> newIdentityArrayList() {
        return new IdentityArrayList<T>();
    }

    public static <T> LinkedList<T> newLinkedList() {
        return new LinkedList<T>();
    }

    public static <T> ArrayList<T> newArrayList() {
        return new ArrayList<T>();
    }

    public static <T> ArrayList<T> newArrayList( T... pEntries ) {
        ArrayList<T> zList = newArrayList();
        append( zList, pEntries );
        return zList;
    }

    public static <T> ArrayList<T> newArrayList( Collection<T> pEntries ) {
        ArrayList<T> zList = newArrayList();
        if ( pEntries != null ) {
            zList.addAll( pEntries );
        }
        return zList;
    }

    public static <T> ArrayList<T> newArrayList( int pInitialSize ) {
        return new ArrayList<T>( Math.max( 1, pInitialSize ) );
    }

    public static <T> List<T> newArrayList( List<T> pList1, List<T> pList2 ) {
        ArrayList<T> zMerged = newArrayList( ConstrainTo.notNull( pList1 ) );
        zMerged.addAll( ConstrainTo.notNull( pList2 ) );
        return zMerged;
    }

    public static <T> List<T> empty() {
        return Collections.emptyList();
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

    // TODO: vvvvvvvvvvvvvvvvvvvvvvvv  NEW  vvvvvvvvvvvvvvvvvvvvvvvv :ODOT \\

    public static <T> List<T> deNullImmutable( List<T> pList ) {
        if ( Currently.isNullOrEmpty( pList ) ) {
            return empty();
        }
        List<T> zList = newArrayList( pList ); // COPY!
        return Collections.unmodifiableList( zList );
    }

    // TODO: ^^^^^^^^^^^^^^^^^^^^^^^^  NEW  ^^^^^^^^^^^^^^^^^^^^^^^^ :ODOT \\

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

    public static <T> List<T> append( List<T> pBase, T... pAdditionalEntries ) {
        if ( pAdditionalEntries != null ) {
            Collections.addAll( pBase, pAdditionalEntries );
        }
        return pBase;
    }

    public static <T> List<T> append( List<T> pBase, List<T> pAdditionalEntries ) {
        pBase = deNullMutable( pBase );
        pBase.addAll( deNullMutable( pAdditionalEntries ) );
        return pBase;
    }

    public static <T> T[] subRange( T[] pSourceArray, int pFrom, T[] pTargetArray ) {
        int zExpectedLength = pSourceArray.length - pFrom;
        if ( zExpectedLength < pTargetArray.length ) {
            throw new IllegalArgumentException( "TargetArray Too Large (" + pTargetArray.length + "), expected <= " + zExpectedLength );
        }
        List<T> zList = newArrayList( zExpectedLength );
        while ( pFrom < pSourceArray.length ) {
            zList.add( pSourceArray[pFrom++] );
        }
        return zList.toArray( pTargetArray );
    }

    public static <T> Provider<T> toProvider( List<T> pSource ) {
        return new SimpleProvider<T>( ConstrainTo.notNull( pSource ) );
    }

    public static <T> Provider<T> toProvider( T[] pSourceArray ) {
        List<T> zList = newArrayList( pSourceArray );
        return toProvider( zList );
    }

    public static String convertToString( List<String> inputList ) {
        String result = null;
        if ( inputList != null ) {
            result = inputList.toString();
            result = result.substring( 1, result.length() - 1 );
        }
        return result;
    }

    public static List<String> convertToString( List<String> inputList, int pMaxLineLength ) {
        if ( inputList == null ) {
            return null;
        }
        int zMaxLengthLessSpace = pMaxLineLength - 1;
        ArrayList<String> zLines = newArrayList();
        StringBuilder zLine = new StringBuilder();
        if ( !inputList.isEmpty() ) {
            Iterator<String> it = inputList.iterator();
            zLine.append( it.next() );
            while ( it.hasNext() ) {
                zLine.append( ',' );
                String zEntry = it.next();
                if ( zMaxLengthLessSpace <= (zLine.length() + zEntry.length()) ) {
                    zLines.add( zLine.toString() );
                    zLine = new StringBuilder();
                }
                zLine.append( zEntry );
            }
        }
        zLines.add( zLine.toString() );
        return zLines;
    }

    public static int maxEntryLength( List<String> pStrings ) {
        int zMaxLength = 0;
        if ( pStrings != null ) {
            for ( String zString : pStrings ) {
                if ( zString != null ) {
                    zMaxLength = Math.max( zMaxLength, zString.length() );
                }
            }
        }
        return zMaxLength;
    }

    public static List<String> sortIgnoreCase( List<String> pStrings ) {
        if ( pStrings != null ) {
            Collections.sort( pStrings, Compare.IGNORE_CASE_COMPARATOR );
        }
        return pStrings;
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

    private static class SimpleProvider<T> implements Provider<T> {
        private final List<T> mList;

        private SimpleProvider( List<T> pList ) {
            mList = pList;
        }

        @Override
        public List<T> get() {
            return mList;
        }
    }
}
