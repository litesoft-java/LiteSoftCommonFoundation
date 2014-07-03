package org.litesoft.commonfoundation.issue;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.indent.*;
import org.litesoft.commonfoundation.typeutils.*;

import java8.util.function.*;

import java.util.*;

public class Source implements Indentable,
                               Supplier<String> { // GSON friendly
    private transient IssueSink mIssueSink;
    private String source;
    private Source next;

    private Source() { // 4 GSON
    }

    private Source( String pSource ) {
        this(); // To quite the unused...
        source = Confirm.isNotNull( "Source", pSource );
    }

    private Source( String pSource, Source pNext ) {
        this( pSource );
        this.mIssueSink = (next = pNext).mIssueSink;
    }

    public Source( IssueSink pIssueSink, String pSource ) {
        this( pSource );
        mIssueSink = Confirm.isNotNull( "IssueCollector", pIssueSink );
    }

    private Source( IssueSink pIssueSink, String pSource, Source pNext ) {
        mIssueSink = pIssueSink;
        source = pSource;
        next = pNext;
    }

    public Source using( IssueSink pIssueSink ) {
        Confirm.isNotNull( "IssueCollector", pIssueSink );
        return (mIssueSink == pIssueSink) ? this : new Source( pIssueSink, source, next );
    }

    public Source plus( String pSource ) {
        return new Source( pSource, this );
    }

    public Source plus( Enum<?> pSource ) {
        return new Source( pSource.name(), this );
    }

    public Source plus( PseudoEnum pSource ) {
        return new Source( pSource.name(), this );
    }

    public Source of( String pValue ) {
        return new Source( mIssueSink, source + "='" + pValue + "'", next );
    }

    public Source of( Enum<?> pValue ) {
        return of( pValue.name() );
    }

    public Source of( PseudoEnum pValue ) {
        return of( pValue.name() );
    }

    public String getSource() {
        return source;
    }

    public Source getNext() {
        return next;
    }

    @Override
    public String toString() {
        StringIndentableWriter zWriter = new StringIndentableWriter( "    " );
        appendTo( zWriter );
        zWriter.close();
        return zWriter.toString();
    }

    @Override
    public void appendTo( IndentableWriter pWriter ) {
        pWriter.printLn( source );
        if ( next != null ) {
            pWriter.indent();
            next.appendTo( pWriter );
            pWriter.outdent();
        }
    }

    @Override
    public String get() {
        return toString( " in " );
    }

    public StringTree toStringTree(String pPrefix) {
        String zSource = ConstrainTo.notNull( pPrefix ) + source;
        return (next == null) ? StringTree.from( zSource ) : StringTree.from( zSource, next.toStringTree() );
    }

    public StringTree toStringTree() {
        return (next == null) ? StringTree.from( source ) : StringTree.from( source, next.toStringTree() );
    }

    public List<String> toList() {
        List<String> zStrings = Lists.newArrayList();
        reverseOrderAddTo( zStrings );
        return Collections.unmodifiableList( zStrings );
    }

    public static List<String> asList( Source pSource ) {
        if ( pSource != null ) {
            return pSource.toList();
        }
        return Lists.empty();
    }

    public static boolean areEqual( Source p1, Source p2 ) {
        return (p1 == p2) // Identity: Same Instance or both null
               || ((p1 != null) && p1.equals( p2 )); // if p1 == null then p2 must NOT be null and are therefore NOT equal!
    }

    @Override
    public int hashCode() {
        return source.hashCode();
    }

    @Override
    public boolean equals( Object o ) {
        return (this == o) || ((o instanceof Source) && equals( (Source) o ));
    }

    public boolean equals( Source them ) {
        return (this == them) || ((them != null)
                                  && (this.source.equals( them.source ))
                                  && areEqual( this.next, them.next ));
    }

    public String toString( String pSep ) {
        return appendTo( new StringBuilder(), pSep ).toString();
    }

    public StringBuilder appendTo( StringBuilder pSB, String pSep ) {
        if ( pSB.length() != 0 ) {
            pSB.append( pSep );
        }
        pSB.append( source );
        if ( next != null ) {
            next.appendTo( pSB, pSep );
        }
        return pSB;
    }

    public <T> T addWarning( Issue.Builder pIssue ) {
        return mIssueSink.addWarning( pIssue.with( this ).build() );
    }

    public <T> T addError( Issue.Builder pIssue ) {
        return mIssueSink.addError( pIssue.with( this ).build() );
    }

    private void reverseOrderAddTo( List<String> pStrings ) {
        if ( next != null ) {
            next.reverseOrderAddTo( pStrings );
        }
        pStrings.add( source );
    }

    public static Source test( String pSource ) {
        return new Source( pSource );
    }

    public Source merge( Source them ) {
        return them; // TODO: XXX Merge this & them somehow!!!
    }
}
