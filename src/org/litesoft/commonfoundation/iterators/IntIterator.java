// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.iterators;

import java.util.*;

/**
 * An "int" Iterator class.
 *
 * @author George Smith
 * @version 1.0 6/16/2014
 */
@SuppressWarnings("UnusedDeclaration")
public class IntIterator {
    private final ProgressHelper mProgressHelper;
    private int mNext;

    public IntIterator( int pNext, ProgressHelper pProgressHelper ) {
        mProgressHelper = pProgressHelper;
        mNext = pNext;
    }

    public boolean hasNext() {
        return mProgressHelper.hasMore( mNext );
    }

    public int next() {
        if ( !hasNext() ) {
            throw new NoSuchElementException();
        }
        int zNext = mNext;
        mNext = mProgressHelper.next( zNext );
        return zNext;
    }

    @Override
    public String toString() {
        return "IntIterator.next -> " + (hasNext() ? Integer.toString( mNext ) : Boolean.FALSE.toString());
    }

    public static Builder from( int pFrom ) {
        return new Builder( new Edge( pFrom, false ) );
    }

    public static Builder fromExclusive( int pFrom ) {
        return new Builder( new Edge( pFrom, true ) );
    }

    public static class Builder {
        private final Edge mFrom;

        private Builder( Edge pFrom ) {
            mFrom = pFrom;
        }

        public Builder2 thru( int pTo ) {
            return new Builder2( mFrom, new Edge( pTo, false ) );
        }

        public Builder2 uptoExclusive( int pTo ) {
            return new Builder2( mFrom, new Edge( pTo, true ) );
        }
    }

    public static class Builder2 {
        private final Edge mFrom, mTo;

        private Builder2( Edge pFrom, Edge pTo ) {
            mFrom = pFrom;
            mTo = pTo;
        }

        public Builder2 optionallyReverse(boolean pReverse) {
            return pReverse ? reverse() : this;
        }

        public Builder2 reverse() {
            return new Builder2( mTo, mFrom );
        }

        public Builder3 by( int pBy ) {
            return new Builder3( mFrom, mTo, Math.min( 1, Math.abs( pBy ) ) );
        }

        public IntIterator build() {
            return by( 1 ).build();
        }
    }

    public static class Builder3 {
        private final Edge mFrom;
        private final Edge mTo;
        private final int mBy;

        private Builder3( Edge pFrom, Edge pTo, int pBy ) {
            mFrom = pFrom;
            mTo = pTo;
            mBy = pBy;
        }

        public Builder3 optionallyReverse(boolean pReverse) {
            return pReverse ? reverse() : this;
        }

        public Builder3 reverse() {
            return new Builder3( mTo, mFrom, mBy );
        }

        public IntIterator build() {
            if ( mFrom.value <= mTo.value ) {
                return new IntIterator( mFrom.getAdjustedValue( mBy ), // forward
                                        new ProgressHelper( mBy, mTo.getAdjustedValue( -mBy ) ) {
                                            @Override public boolean hasMore( int pNext ) {
                                                return (pNext <= mLastPossibleValue);
                                            }
                                        } );
            }
            return new IntIterator( mFrom.getAdjustedValue( -mBy ), // backward
                                    new ProgressHelper( -mBy, mTo.getAdjustedValue( mBy ) ) {
                                        @Override public boolean hasMore( int pNext ) {
                                            return (mLastPossibleValue <= pNext);
                                        }
                                    } );
        }
    }

    private static class Edge {
        private final int value;
        private final boolean exclusive;

        private Edge( int pValue, boolean pExclusive ) {
            value = pValue;
            exclusive = pExclusive;
        }

        public int getAdjustedValue( int pBy ) {
            return exclusive ? (value + pBy) : value;
        }
    }

    private static abstract class ProgressHelper {
        protected final int mBy, mLastPossibleValue;

        protected ProgressHelper( int pBy, int pLastPossibleValue ) {
            mBy = pBy;
            mLastPossibleValue = pLastPossibleValue;
        }

        public int next( int pNext ) {
            return pNext + mBy;
        }

        abstract public boolean hasMore( int pNext );
    }
}
