package org.litesoft.commonfoundation.base;

public interface KeyedTextValues {
    /**
     * Get the Externalization value for the 'key', or null if it is not found.
     * <p/>
     *
     * @param key !empty
     *
     * @return null or the substitution String.
     */
    String get( String key );

    static final KeyedTextValues EMPTY = new KeyedTextValues() {
        @Override
        public String get( String key ) {
            return null;
        }
    };
}
