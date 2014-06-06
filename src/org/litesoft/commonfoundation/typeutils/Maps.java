// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import java.util.*;

public class Maps
{
    @SuppressWarnings("Convert2Diamond")
    public static <K, V> Map<K, V> newHashMap()
    {
        return new HashMap<K, V>();
    }

    public static <K, V> Map<K, V> empty()
    {
        return Collections.emptyMap();
    }

    public static <K, V> Map<K, V> copyHashMap( Map<K, V> pSource )
    {
        if ( pSource == null || pSource.isEmpty() )
        {
            return empty();
        }
        Map<K, V> rv = newHashMap();
        rv.putAll( pSource );
        return rv;
    }

    public static <K, V> Map<K, V> deNull( Map<K, V> pToCheck )
    {
        if ( pToCheck == null )
        {
            pToCheck = empty();
        }
        return pToCheck;
    }

    public static String[] stringMapToStringArray( Map<String, String> pMap )
    {
        if ( pMap == null || pMap.isEmpty() )
        {
            return Strings.EMPTY_ARRAY;
        }
        String[] rv = new String[pMap.size() * 2];
        int i = 0;
        for ( Map.Entry<String, String> zEntry : pMap.entrySet() )
        {
            rv[i++] = zEntry.getKey();
            rv[i++] = zEntry.getValue();
        }
        return rv;
    }

    public static Map<String, String> createHashMap( String... pKeyValuePairs )
            throws IllegalArgumentException
    {
        Map<String, String> zMap = newHashMap();
        return populate( zMap, pKeyValuePairs );
    }

    public static Map<String, String> populate( Map<String, String> pToPopulate, String... pNameValuePairs )
            throws IllegalArgumentException
    {
        if ( pNameValuePairs != null )
        {
            if ( (pNameValuePairs.length & 1) == 1 )
            {
                throw new IllegalArgumentException( "NameValuePairs not paired" );
            }
            for ( int i = 0; i < pNameValuePairs.length; )
            {
                String name = pNameValuePairs[i++];
                String value = pNameValuePairs[i++];
                // noinspection unchecked
                pToPopulate.put( name, value );
            }
        }
        return pToPopulate;
    }

    public static void populate( Map pToPopulate, Map pNewContents )
    {
        if ( pNewContents != null )
        {
            //noinspection unchecked
            pToPopulate.putAll( pNewContents );
        }
    }

    public static String[] getSortedKeysAsStrings( Map pMap )
    {
        String[] names = Strings.toArray( pMap.keySet().toArray() );
        Arrays.sort( names );
        return names;
    }

    public static Map<String, String> addPropertiesTo( Map<String, String> pMap, String... pProperty_NameValues )
            throws IllegalArgumentException
    {
        if ( Objects.isNotNullOrEmpty( pProperty_NameValues ) )
        {
            if ( (pProperty_NameValues.length & 1) != 0 )
            {
                throw new IllegalArgumentException( "Attempt to add Properties that were NOT Name/Value pairs" );
            }
            for ( int i = 0; i < pProperty_NameValues.length; i += 2 )
            {
                pMap.put( pProperty_NameValues[i], pProperty_NameValues[i + 1] );
            }
        }
        return pMap;
    }
}
