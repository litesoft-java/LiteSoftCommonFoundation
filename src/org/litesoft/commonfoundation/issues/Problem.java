// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.issues;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.typeutils.*;

import java.io.*;
import java.util.*;

public class Problem implements Serializable {
    private static final long serialVersionUID = 1L;

    private /* final */ String mProblemCode;

    private /* final */ String[] mProblemSupportValues;

    private /* final */ List<NameValuePair> mNamedSupportValues;

    @SuppressWarnings({"deprecation", "UnusedDeclaration"})
    @Deprecated
    /** for Serialization */
    protected Problem() {
    }

    public Problem( @NotNull Throwable pThrowable ) {
        this( pThrowable, null, (String[]) null );
    }

    public Problem( @NotNull String pProblemCode ) {
        this( null, pProblemCode, (String[]) null );
    }

    public Problem( @NotNull String pProblemCode, String[] pProblemSupportValues ) {
        this( null, pProblemCode, pProblemSupportValues );
    }

    public Problem( @NotNull String pProblemCode, String pProblemSupportValue ) {
        this( null, pProblemCode, new String[]{pProblemSupportValue} );
    }

    public Problem( @NotNull String pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1 ) {
        this( null, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1} );
    }

    public Problem( @NotNull String pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1, String pProblemSupportValue2 ) {
        this( null, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1, pProblemSupportValue2} );
    }

    public Problem( @NotNull String pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1, String pProblemSupportValue2,
                    String pProblemSupportValue3 ) {
        this( null, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1, pProblemSupportValue2, pProblemSupportValue3} );
    }

    public Problem( Throwable pThrowable, String pProblemCode ) {
        this( pThrowable, pProblemCode, (String[]) null );
    }

    public Problem( Throwable pThrowable, String pProblemCode, String pProblemSupportValue ) {
        this( pThrowable, pProblemCode, new String[]{pProblemSupportValue} );
    }

    public Problem( Throwable pThrowable, String pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1 ) {
        this( pThrowable, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1} );
    }

    public Problem( Throwable pThrowable, String pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1, String pProblemSupportValue2 ) {
        this( pThrowable, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1, pProblemSupportValue2} );
    }

    public Problem( Throwable pThrowable, String pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1, String pProblemSupportValue2,
                    String pProblemSupportValue3 ) {
        this( pThrowable, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1, pProblemSupportValue2, pProblemSupportValue3} );
    }

    /**
     * One of <code>pProblemCode</code> or <code>pThrowable</code> must be specified.
     *
     * @param pThrowable            a Throwable or <code>null</code>
     * @param pProblemSupportValues additional info about <code>pProblemCode</code>, or <code>null</code>
     *
     * @throws IllegalArgumentException if <code>pProblemSupportValues</code> is
     *                                  specified without <code>pProblemCode</code>
     */
    public Problem( Throwable pThrowable, String pProblemCode, String[] pProblemSupportValues ) {
        this( pThrowable, pProblemCode, pProblemSupportValues, null );
    }

    private Problem( Throwable pThrowable, String pProblemCode, String[] pProblemSupportValues, List<NameValuePair> pNamedSupportValues ) {
        pProblemCode = ConstrainTo.significantOrNull( pProblemCode );
        pProblemSupportValues = Strings.noEmpty( pProblemSupportValues );
        pNamedSupportValues = ConstrainTo.notNull( pNamedSupportValues );
        if ( (pThrowable == null) && (pProblemCode == null) ) {
            throw new IllegalArgumentException( "Either the Throwable or the ProblemCode must not be null" );
        }
        if ( (pProblemCode == null) && ((pProblemSupportValues != null) || !pNamedSupportValues.isEmpty()) ) {
            throw new IllegalArgumentException( "...SupportValues not allowed when there is no ProblemCode" );
        }

        List<String> zProblemSupportValues = Lists.newArrayList();
        if ( pProblemSupportValues != null ) {
            zProblemSupportValues.addAll( Arrays.asList( pProblemSupportValues ) );
        }
        if ( pThrowable != null ) {
            if ( pProblemCode == null ) {
                pProblemCode = pThrowable.getMessage();
            }
            else {
                zProblemSupportValues.add( pThrowable.getMessage() );
            }
            zProblemSupportValues.add( Throwables.printStackTraceToString( pThrowable ) );
        }
        mProblemCode = pProblemCode;
        mProblemSupportValues = zProblemSupportValues.toArray( new String[zProblemSupportValues.size()] );
        mNamedSupportValues = Lists.deNullImmutable( pNamedSupportValues );
    }

    public @NotNull String getProblemCode() {
        return mProblemCode;
    }

    public @NotNull String[] getProblemSupportValues() {
        return mProblemSupportValues;
    }

    public @NotNull @Immutable List<NameValuePair> getNamedSupportValues() {
        return mNamedSupportValues;
    }

    public static class Builder {
        private final String mProblemCode;
        private final List<NameValuePair> mPairs = Lists.newArrayList();
        private final Set<String> mNames = Sets.newHashSet();

        public Builder( String pProblemCode ) {
            mProblemCode = ConstrainTo.significantOrNull( "ProblemCode", pProblemCode );
        }

        public Builder add( String pName, Object pValue ) {
            if ( !mNames.add( pName = Confirm.significant( "Name", pName ) ) ) {
                throw new IllegalArgumentException( "Duplicate Name: " + pName );
            }
            String zValue = (pValue == null) ? null : pValue.toString();
            mPairs.add( new NameValuePair( pName, zValue ) );
            return this;
        }

        public Problem build() {
            return new Problem( null, mProblemCode, null, mPairs );
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( mProblemCode );
        if ( mProblemSupportValues != null ) { // null during construction!
            String zOrigPrepend = "( ";
            String zPrepend = zOrigPrepend;
            for ( String zValue : mProblemSupportValues ) {
                sb.append( zPrepend ).append( Strings.quote( zValue ) );
                zPrepend = ", ";
            }
            if ( !zPrepend.equals( zOrigPrepend ) ) {
                sb.append( " )" );
            }
        }
        return (sb.length() == 0) ? "?" : sb.toString();
    }
}
