// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.issues;

import org.litesoft.commonfoundation.typeutils.Objects;
import org.litesoft.commonfoundation.typeutils.*;

import java.io.*;
import java.util.*;

public class Problem implements Serializable {
    private static final long serialVersionUID = 1L;

    private /* final */ String mProblemCode;

    private /* final */ String[] mProblemSupportValues;

    @SuppressWarnings({"deprecation", "UnusedDeclaration"})
    @Deprecated
    /** for Serialization */
    protected Problem() {
    }

    public Problem( Throwable pThrowable ) {
        this( pThrowable, null, (String[]) null );
    }

    public Problem( String pProblemCode, String[] pProblemSupportValues ) {
        this( null, pProblemCode, pProblemSupportValues );
    }

    public Problem( String pProblemCode ) {
        this( null, pProblemCode, (String[]) null );
    }

    public Problem( String pProblemCode, String pProblemSupportValue ) {
        this( null, pProblemCode, new String[]{pProblemSupportValue} );
    }

    public Problem( String pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1 ) {
        this( null, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1} );
    }

    public Problem( String pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1, String pProblemSupportValue2 ) {
        this( null, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1, pProblemSupportValue2} );
    }

    public Problem( String pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1, String pProblemSupportValue2,
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
     * @param pProblemCode
     * @param pProblemSupportValues additional info about <code>pProblemCode</code>, or <code>null</code>
     *
     * @throws IllegalArgumentException if <code>pProblemSupportValues</code> is
     *                                  specified without <code>pProblemCode</code>
     */
    public Problem( Throwable pThrowable, String pProblemCode, String[] pProblemSupportValues ) {
        pProblemCode = Strings.noEmpty( pProblemCode );
        pProblemSupportValues = Strings.noEmpty( pProblemSupportValues );
        if ( (pThrowable == null) && (pProblemCode == null) ) {
            throw new IllegalArgumentException( "Either the Throwable or the ProblemCode must not be null" );
        }
        if ( (pProblemCode == null) && (pProblemSupportValues != null) ) {
            throw new IllegalArgumentException( "ProblemSupportValues not allowed when there is no ProblemCode" );
        }

        List<String> zProblemSupportValues = new ArrayList<String>();
        if ( pProblemSupportValues != null ) {
            zProblemSupportValues.addAll( Arrays.asList( pProblemSupportValues ) );
        }
        if ( pThrowable != null ) {
            if ( pProblemCode == null ) {
                pProblemCode = pThrowable.getMessage();
            } else {
                zProblemSupportValues.add( pThrowable.getMessage() );
            }
            zProblemSupportValues.add( Throwables.printStackTraceToString( pThrowable ) );
        }
        mProblemCode = pProblemCode;
        mProblemSupportValues = new String[zProblemSupportValues.size()];
        for ( int i = 0; i < mProblemSupportValues.length; i++ ) {
            mProblemSupportValues[i] = zProblemSupportValues.get( i );
        }
    }

    public String getProblemCode() {
        return mProblemCode;
    }

    public String[] getProblemSupportValues() {
        return mProblemSupportValues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append( mProblemCode );
        if ( mProblemSupportValues != null ) {
            sb.append( Objects.toString( mProblemSupportValues, "\r\n" ) );
        }
        return (sb.length() == 0) ? "?" : sb.toString();
    }
}
