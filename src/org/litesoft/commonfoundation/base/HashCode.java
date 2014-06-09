package org.litesoft.commonfoundation.base;

public class HashCode {
    public static class Builder {
        private int mHashCode;

        private Builder( int pHashCode ) {
            mHashCode = pHashCode;
        }

        public int toHashCode() {
            return mHashCode;
        }

        public Builder and( Object pValue ) {
            return addHashCode( calc( pValue ) );
        }

        public Builder and( boolean pValue ) {
            return addHashCode( calc( pValue ) );
        }

        public Builder and( int pValue ) {
            return addHashCode( calc( pValue ) );
        }

        public Builder and( long pValue ) {
            return addHashCode( calc( pValue ) );
        }

        private Builder addHashCode( int pHashCode ) {
            mHashCode = em( mHashCode, pHashCode );
            return this;
        }
    }

    public static Builder from( Object pValue ) {
        return new Builder( calc( pValue ) );
    }

    public static Builder from( boolean pValue ) {
        return new Builder( calc( pValue ) );
    }

    public static Builder from( int pValue ) {
        return new Builder( calc( pValue ) );
    }

    public static Builder from( long pValue ) {
        return new Builder( calc( pValue ) );
    }

    public static int calc( Object pValue ) {
        return (pValue == null) ? 0 : pValue.hashCode();
    }

    public static int calc( boolean pValue ) {
        return Boolean.valueOf( pValue ).hashCode();
    }

    public static int calc( int pValue ) {
        return pValue; // 'lifted' from Integer
    }

    public static int calc( long pValue ) {
        return (int) (pValue ^ (pValue >>> 32)); // 'lifted' from Long
    }

    public static int em( int pHashCode1, int pHashCode2 ) {
        return (pHashCode1 * 31) + pHashCode2;
    }

    public static int em( int pHashCode1, int pHashCode2, int pHashCode3 ) {
        return em( em( pHashCode1, pHashCode2 ), pHashCode3 );
    }

    public static int em( int pHashCode1, int pHashCode2, int pHashCode3, int pHashCode4 ) {
        return em( em( pHashCode1, pHashCode2, pHashCode3 ), pHashCode4 );
    }

    public static int em( int pHashCode1, int pHashCode2, int pHashCode3, int pHashCode4, int pHashCode5 ) {
        return em( em( pHashCode1, pHashCode2, pHashCode3, pHashCode4 ), pHashCode5 );
    }
}
