// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.problems;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.indent.*;
import org.litesoft.commonfoundation.iterators.*;
import org.litesoft.commonfoundation.typeutils.*;

import java.io.*;
import java.util.*;

@SuppressWarnings("Convert2Diamond")
public class Problem implements Serializable, Indentable {
    private static final long serialVersionUID = 1L;

    private /* final */ Enum<?> mProblemCode;

    private /* final */ Throwable mThrowable;

    private /* final */ String[] mProblemSupportValues;

    private /* final */ List<NameValuePair> mNamedSupportValues;

    @SuppressWarnings({"deprecation", "UnusedDeclaration"})
    @Deprecated
    /** for Serialization */
    protected Problem() {
    }

    public Problem( @NotNull Enum<?> pProblemCode ) {
        this( null, pProblemCode, (String[]) null );
    }

    public Problem( @NotNull Enum<?> pProblemCode, String[] pProblemSupportValues ) {
        this( null, pProblemCode, pProblemSupportValues );
    }

    public Problem( @NotNull Enum<?> pProblemCode, String pProblemSupportValue ) {
        this( null, pProblemCode, new String[]{pProblemSupportValue} );
    }

    public Problem( @NotNull Enum<?> pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1 ) {
        this( null, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1} );
    }

    public Problem( @NotNull Enum<?> pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1, String pProblemSupportValue2 ) {
        this( null, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1, pProblemSupportValue2} );
    }

    public Problem( @NotNull Enum<?> pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1, String pProblemSupportValue2,
                    String pProblemSupportValue3 ) {
        this( null, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1, pProblemSupportValue2, pProblemSupportValue3} );
    }

    public Problem( Throwable pThrowable, Enum<?> pProblemCode ) {
        this( pThrowable, pProblemCode, (String[]) null );
    }

    public Problem( Throwable pThrowable, Enum<?> pProblemCode, String pProblemSupportValue ) {
        this( pThrowable, pProblemCode, new String[]{pProblemSupportValue} );
    }

    public Problem( Throwable pThrowable, Enum<?> pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1 ) {
        this( pThrowable, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1} );
    }

    public Problem( Throwable pThrowable, Enum<?> pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1, String pProblemSupportValue2 ) {
        this( pThrowable, pProblemCode, new String[]{pProblemSupportValue0, pProblemSupportValue1, pProblemSupportValue2} );
    }

    public Problem( Throwable pThrowable, Enum<?> pProblemCode, String pProblemSupportValue0, String pProblemSupportValue1, String pProblemSupportValue2,
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
    public Problem( Throwable pThrowable, Enum<?> pProblemCode, String[] pProblemSupportValues ) {
        this( pThrowable, pProblemCode, pProblemSupportValues, null );
    }

    private Problem( Throwable pThrowable, Enum<?> pProblemCode, String[] pProblemSupportValues, List<NameValuePair> pNamedSupportValues ) {
        mThrowable = pThrowable;
        mProblemCode = Confirm.isNotNull( "ProblemCode", pProblemCode );
        pProblemSupportValues = Strings.noEmpty( pProblemSupportValues );
        pNamedSupportValues = ConstrainTo.notNull( pNamedSupportValues );
        List<String> zProblemSupportValues = Lists.newArrayList();
        if ( pProblemSupportValues != null ) {
            zProblemSupportValues.addAll( Arrays.asList( pProblemSupportValues ) );
        }
        mProblemSupportValues = zProblemSupportValues.toArray( new String[zProblemSupportValues.size()] );
        mNamedSupportValues = Lists.deNullImmutable( pNamedSupportValues );
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    @NotNull
    public Enum<?> getProblemCode() {
        return mProblemCode;
    }

    @NotNull
    public String[] getProblemSupportValues() {
        return mProblemSupportValues;
    }

    @NotNull
    @Immutable
    public List<NameValuePair> getNamedSupportValues() {
        return mNamedSupportValues;
    }

    public static class Builder {
        private final Enum<?> mProblemCode;
        private final List<NameValuePair> mPairs = Lists.newArrayList();
        private final Set<String> mNames = Sets.newHashSet();

        public Builder( Enum<?> pProblemCode ) {
            mProblemCode = Confirm.isNotNull( "ProblemCode", pProblemCode );
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
        return StringIndentableWriter.formatWith( this );
    }

    @Override
    public IndentableWriter appendTo( @NotNull IndentableWriter pWriter ) {
        pWriter.print( mProblemCode );
        if ( Currently.isNotNullOrEmpty( mProblemSupportValues ) ) { // null during construction!
            render( pWriter, new ArrayIterator<String>( mProblemSupportValues ) );
        }
        if ( Currently.isNotNullOrEmpty( mNamedSupportValues ) ) { // null during construction!
            render( pWriter, mNamedSupportValues.iterator() );
        }
        if ( mThrowable != null ) {
            pWriter.printLn();
            pWriter.indent();
            pWriter.print( Throwables.printStackTraceToString( mThrowable ) );
            if ( 0 != pWriter.currentLineOffset() ) {
                pWriter.printLn();
            }
            pWriter.outdent();
        }
        return pWriter;
    }

    private void render( IndentableWriter pWriter, Iterator<?> pIterator ) {
        for ( String zPrepend = "( "; pIterator.hasNext(); zPrepend = ", " ) {
            pWriter.print( zPrepend );
            pWriter.print( stringify( pIterator.next() ) );
        }
        pWriter.print( " )" );
    }

    private String stringify( Object pEntry ) {
        return (pEntry instanceof String) ? Strings.quote( pEntry.toString() ) : pEntry.toString();
    }
}
