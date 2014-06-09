package org.litesoft.commonfoundation.issue;

import org.litesoft.commonfoundation.indent.*;
import org.litesoft.commonfoundation.typeutils.*;
import org.litesoft.commonfoundation.typeutils.Objects;

import java.util.*;

public class Source implements Indentable { // GSON friendly

    public Source( String pSource ) {
        this( pSource, null );
    }

    public Source plus( String pSource ) {
        return new Source( pSource, this );
    }

    public Source of( String pValue ) {
        return new Source( source + "='" + pValue + "'", next );
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

    private void reverseOrderAddTo( List<String> pStrings ) {
        if ( next != null ) {
            next.reverseOrderAddTo( pStrings );
        }
        pStrings.add( source );
    }

    private Source( String pSource, Source pNext ) {
        this(); // To quite the unused...
        source = Objects.assertNotNull( "Source", pSource );
        next = pNext;
    }

    private Source() { // 4 GSON
    }

    private String source;
    private Source next;
}
