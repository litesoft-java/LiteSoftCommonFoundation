// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;

import java.util.*;

@SuppressWarnings({"Convert2Diamond", "UnusedDeclaration"})
public class Maps {
    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap<K, V>();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<K, V>();
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }

    public static <K, V> HashMap<K, V> newHashMap(Map<K, V> pMap) {
        HashMap<K, V> zHashMap = newHashMap();
        if (pMap != null) {
            zHashMap.putAll( pMap );
        }
        return zHashMap;
    }

    public static <K, V> Map<K, V> empty() {
        return Collections.emptyMap();
    }

    public static <K, V> Map<K, V> deNullMutable( Map<K, V> map ) {
        if ( map != null ) { // Generics says can NOT be a Tertiary!
            return map;
        }
        return newHashMap();
    }

    // TODO: vvvvvvvvvvvvvvvvvvvvvvvv  NEW  vvvvvvvvvvvvvvvvvvvvvvvv :ODOT \\

    public static <K, V> Map<K, V> copyHashMap( Map<K, V> pSource ) {
        if ( pSource == null || pSource.isEmpty() ) {
            return empty();
        }
        Map<K, V> rv = newHashMap();
        rv.putAll( pSource );
        return rv;
    }

    public static String[] ToStringArray( Map<String, String> pMap ) {
        if ( pMap == null || pMap.isEmpty() ) {
            return Strings.EMPTY_ARRAY;
        }
        String[] rv = new String[pMap.size() * 2];
        int i = 0;
        for ( Map.Entry<String, String> zEntry : pMap.entrySet() ) {
            rv[i++] = zEntry.getKey();
            rv[i++] = zEntry.getValue();
        }
        return rv;
    }

    public static Map<String, String> createHashMap( String... pKeyValuePairs )
            throws IllegalArgumentException {
        Map<String, String> zMap = newHashMap();
        return populate( zMap, pKeyValuePairs );
    }

    public static Map<String, String> populate( Map<String, String> pToPopulate, String... pNameValuePairs )
            throws IllegalArgumentException {
        if ( pNameValuePairs != null ) {
            if ( (pNameValuePairs.length & 1) == 1 ) {
                throw new IllegalArgumentException( "NameValuePairs not paired" );
            }
            for ( int i = 0; i < pNameValuePairs.length; ) {
                String name = pNameValuePairs[i++];
                String value = pNameValuePairs[i++];
                // noinspection unchecked
                pToPopulate.put( name, value );
            }
        }
        return pToPopulate;
    }

    public static void populate( Map pToPopulate, Map pNewContents ) {
        if ( pNewContents != null ) {
            //noinspection unchecked
            pToPopulate.putAll( pNewContents );
        }
    }

    public static String[] getSortedKeysAsStrings( Map pMap ) {
        String[] names = Strings.toArray( pMap.keySet().toArray() );
        Arrays.sort( names );
        return names;
    }

    public static Map<String, String> addPropertiesTo( Map<String, String> pMap, String... pProperty_NameValues )
            throws IllegalArgumentException {
        if ( Currently.isNotNullOrEmpty( pProperty_NameValues ) ) {
            if ( (pProperty_NameValues.length & 1) != 0 ) {
                throw new IllegalArgumentException( "Attempt to add Properties that were NOT Name/Value pairs" );
            }
            for ( int i = 0; i < pProperty_NameValues.length; i += 2 ) {
                pMap.put( pProperty_NameValues[i], pProperty_NameValues[i + 1] );
            }
        }
        return pMap;
    }

    // TODO: ^^^^^^^^^^^^^^^^^^^^^^^^  NEW  ^^^^^^^^^^^^^^^^^^^^^^^^ :ODOT \\

    public static void addPairTo( Map<String, String> map, String key, String value ) {
        if ( Currently.significant( value ) ) {
            map.put( key, value );
        }
    }

    public static <E extends Enum<E>> void addPairTo( Map<String, String> map, String key, E value ) {
        if ( value != null ) {
            map.put( key, value.name() );
        }
    }

    public static LinkedHashMap<String, String> newLinkedHashMap( String key, String value, String... additionalKeyValuePairs ) {
        LinkedHashMap<String, String> zMap = newLinkedHashMap();
        addPairTo( zMap, key, value );
        if ( additionalKeyValuePairs != null ) {
            if ( !Currently.isEven( additionalKeyValuePairs.length ) ) {
                throw new IllegalArgumentException( "key-value(s) not paired!" );
            }
            for ( int i = 0; i < additionalKeyValuePairs.length; ) {
                String zKey = additionalKeyValuePairs[i++];
                String zValue = additionalKeyValuePairs[i++];
                zMap.put( zKey, zValue );
            }
        }
        return zMap;
    }

    public static String makeStringKey( Object pKeyPart0, Object... pKeyPartNths ) {
        String zKey = pKeyPart0.toString();
        if ( pKeyPartNths != null ) {
            for ( Object zNth : pKeyPartNths ) {
                zKey += ":" + zNth;
            }
        }
        return zKey;
    }

    public static <V> V fromStringKeyed( Map<String, V> pMap, Object pKeyPart0, Object... pKeyPartNths ) {
        return pMap.get( makeStringKey( pKeyPart0, pKeyPartNths ) );
    }

    public static <V> Builder<V> stringKeyedBuilder( V pValue, Object pKeyPart0, Object... pKeyPartNths ) {
        return new Builder<V>().add( pValue, pKeyPart0, pKeyPartNths );
    }

    public static class Builder<V> {
        private Map<String, V> mMap = newHashMap();

        public Builder<V> add( V pValue, Object pKeyPart0, Object... pKeyPartNths ) {
            String zKey = makeStringKey( pKeyPart0, pKeyPartNths );
            Confirm.isNull( "Duplicate Entry for: " + zKey, mMap.put( zKey, pValue ) );
            return this;
        }

        public Map<String, V> toMap() {
            return mMap;
        }
    }
}
