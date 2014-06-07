// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.base;

import org.litesoft.commonfoundation.annotations.*;

@SuppressWarnings({"unchecked"})
public class Compare {
    /**
     * Compares pA object with the pB object for order.  Nulls rise!<p>
     *
     * @return a negative integer, zero, or a positive integer as the pA object
     * is less than, equal to, or greater than the pB object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *                            from being compared to this Object.
     */
    public static int nullsOK( @Nullable Comparable pThis, @Nullable Comparable pThem ) {
        return firstNullsOK( pThis, pThem ).result();
    }

    /**
     * Compares pA object with the pB object for Ascending Order.  Nulls rise!<p>
     *
     * @return a negative integer, zero, or a positive integer as the pA object
     * is less than, equal to, or greater than the pB object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *                            from being compared to this Object.
     */
    public static int ascending( @Nullable Comparable pThis, @Nullable Comparable pThem ) {
        return firstNullsOK( pThis, pThem ).result();
    }

    /**
     * Compares pA object with the pB object for Descending Order.  Nulls sink!<p>
     *
     * @return a negative integer, zero, or a positive integer as the pA object
     * is greater than, equal to, or less than the pB object.
     *
     * @throws ClassCastException if the specified object's type prevents it
     *                            from being compared to this Object.
     */
    public static int descending( @Nullable Comparable pThis, @Nullable Comparable pThem ) {
        return ascending( pThem, pThis ); // Note param flipped order!
    }

    public static boolean firstLessThanSecond( @NotNull Comparable pFirst, @NotNull Comparable pSecond ) {
        return pFirst.compareTo( pSecond ) < 0;
    }

    public static boolean firstGreaterThanSecond( @NotNull Comparable pFirst, @NotNull Comparable pSecond ) {
        return pFirst.compareTo( pSecond ) > 0;
    }

    public static Compare first( boolean pA, boolean pB ) {
        return ZERO.then( pA, pB );
    }

    public static Compare first( int pA, int pB ) {
        return ZERO.then( pA, pB );
    }

    public static Compare first( long pA, long pB ) {
        return ZERO.then( pA, pB );
    }

    public static Compare first( @NotNull Comparable pA, @NotNull Comparable pB ) {
        return ZERO.then( pA, pB );
    }

    /**
     * Nulls Rise
     */
    public static Compare firstNullsOK( @Nullable Comparable pA, @Nullable Comparable pB ) {
        return ZERO.thenNullsOK( pA, pB );
    }

    public Compare then( boolean pA, boolean pB ) {
        return this;
    }

    public Compare then( int pA, int pB ) {
        return this;
    }

    public Compare then( long pA, long pB ) {
        return this;
    }

    public Compare then( @NotNull Comparable pA, @NotNull Comparable pB ) {
        return this;
    }

    /**
     * Nulls Rise
     */
    public Compare thenNullsOK( @Nullable Comparable pA, @Nullable Comparable pB ) {
        return this;
    }

    public final int result() {
        return mResult;
    }

    protected Compare( int pResult ) {
        mResult = pResult;
    }

    private int mResult;

    private static final Compare NEGATIVE = new Compare( -1 );
    private static final Compare POSITIVE = new Compare( 1 );
    private static final Compare ZERO = new Compare( 0 ) {
        @Override
        public Compare then( boolean pA, boolean pB ) {
            return (pA == pB) ? this : then( (pA ? 1 : 0), (pB ? 1 : 0) );
        }

        @Override
        public Compare then( int pA, int pB ) {
            return vector( pA - pB );
        }

        @Override
        public Compare then( long pA, long pB ) {
            return vector( pA - pB );
        }

        @SuppressWarnings("ConstantConditions")
        @Override
        public Compare then( @NotNull Comparable pA, @NotNull Comparable pB ) {
            if ( (pA == null) || (pB == null) ) {
                throw new NullPointerException();
            }
            return (pA == pB) ? this // same
                              : vector( pA.compareTo( pB ) );
        }

        @Override
        public Compare thenNullsOK( @Nullable Comparable pA, @Nullable Comparable pB ) {
            if ( pA == pB ) // same or both null
            {
                return this;
            }
            return (pA == null) ? NEGATIVE : (pB == null) ? POSITIVE : vector( pA.compareTo( pB ) );
        }

        private Compare vector( long pCompareTo ) {
            return (pCompareTo == 0) ? this : (pCompareTo < 0) ? NEGATIVE : POSITIVE;
        }
    };
}
