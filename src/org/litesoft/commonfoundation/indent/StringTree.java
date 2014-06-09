package org.litesoft.commonfoundation.indent;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.iterators.*;
import org.litesoft.commonfoundation.typeutils.*;
import org.litesoft.commonfoundation.typeutils.Objects;

import java.util.*;

public class StringTree implements Indentable,
                                   Comparable<StringTree> {
    public static StringTree from( String pLine ) {
        return new StringTree( pLine );
    }

    public static StringTree from( String pLine, StringTree... pChildren ) {
        return new StringTree( pLine, pChildren );
    }

    public static StringTree from( String pLine, String... pChildren ) {
        if ( Objects.isNullOrEmpty( pChildren ) ) {
            return new StringTree( pLine );
        }
        StringTree[] zChildren = new StringTree[pChildren.length];
        for ( int i = 0; i < pChildren.length; i++ ) {
            zChildren[i] = new StringTree( pChildren[i] );
        }
        return new StringTree( pLine, zChildren );
    }

    public static StringTree fromTabbedLines( String pLine, String... pLines ) {
        return new StringTree( pLine, new TabbedBuilder( pLines ).build() );
    }

    public String getLine() {
        return mLine;
    }

    public StringTree[] getChildren() {
        return mChildren;
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
        pWriter.printLn( getLine() );
        pWriter.indent();
        for ( StringTree zChild : getChildren() ) {
            zChild.appendTo( pWriter );
        }
        pWriter.outdent();
    }

    @Override
    public int hashCode() {
        return mLine.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        return (this == obj) || ((obj instanceof StringTree) && equals( (StringTree) obj ));
    }

    @Override
    public int compareTo( StringTree them ) {
        int rv = this.mLine.compareTo( them.mLine );
        return (rv != 0) ? rv : compare( this.mChildren, them.mChildren );
    }

    public boolean equals( StringTree them ) {
        return (this == them) || ((them != null)
                                  && (this.mLine.equals( them.mLine ))
                                  && areEqual( this.mChildren, them.mChildren ));
    }

    private static int compare( StringTree[] pChildren1, StringTree[] pChildren2 ) {
        int rv = 0;
        int zMaxLength = Math.max( pChildren1.length, pChildren2.length );
        for ( int i = 0; i < zMaxLength; i++ ) {
            if ( 0 != (rv = Compare.nullsOK( entry( pChildren1, i ), entry( pChildren2, i ) )) ) {
                break;
            }
        }
        return rv;
    }

    private static Comparable entry( StringTree[] pEntries, int pIndex ) {
        return (pIndex < pEntries.length) ? pEntries[pIndex] : null;
    }

    private static boolean areEqual( StringTree[] pChildren1, StringTree[] pChildren2 ) {
        if ( pChildren1.length != pChildren2.length ) {
            return false;
        }
        for ( int i = 0; i < pChildren1.length; i++ ) {
            if ( !pChildren1[i].equals( pChildren2[i] ) ) {
                return false;
            }
        }
        return true;
    }

    private final String mLine;
    private final StringTree[] mChildren;

    private StringTree( String pLine, List<StringTree> pChildren ) {
        mLine = Strings.deNull( pLine ).trim();
        mChildren = new StringTree[pChildren.size()];
        for ( int i = 0; i < pChildren.size(); i++ ) {
            mChildren[i] = Objects.deNull( pChildren.get( i ), BLANK_LINE );
        }
    }

    private StringTree( String pLine, StringTree[] pChildren ) {
        this( pLine, Arrays.asList( deNull( pChildren ) ) );
    }

    private StringTree( String pLine ) {
        this( pLine, EMPTY );
    }

    private static StringTree[] deNull( StringTree[] pChildren ) {
        return (pChildren != null) ? pChildren : EMPTY;
    }

    private static final StringTree[] EMPTY = new StringTree[0];
    private static final StringTree BLANK_LINE = new StringTree( "", EMPTY );

    protected static class TabbedBuilder {
        private static final int TAB_CHAR = 9;
        private final List<StringTree> mCollector = Lists.newArrayList();
        private final OnePushBackIterator<String> mIterator;

        public TabbedBuilder( String[] pLines ) {
            mIterator = new OnePushBackIterator<String>( new ArrayIterator<String>( pLines ) );
        }

        public StringTree[] build() {
            if ( !mIterator.hasNext() ) {
                return EMPTY;
            }
            do {
                mCollector.add( process( mIterator.next().trim(), 0 ) );
            } while ( mIterator.hasNext() );
            return mCollector.toArray( new StringTree[mCollector.size()] );
        }

        private StringTree process( String pCurrent, int pIndentLevel ) {
            List<StringTree> zChildren = Lists.newArrayList();
            while ( mIterator.hasNext() ) {
                String zNext = mIterator.next();
                int zIndentLevel = tabs( zNext );
                if ( zIndentLevel <= pIndentLevel ) {
                    mIterator.pushBack();
                    return new StringTree( pCurrent, zChildren );
                }
                zChildren.add( process( zNext.trim(), zIndentLevel ) );
            }
            return new StringTree( pCurrent, zChildren );
        }

        private int tabs( String pLine ) {
            int i = 0;
            for (; i < pLine.length(); i++ ) {
                if ( TAB_CHAR != pLine.charAt( i ) ) {
                    break;
                }
            }
            return i;
        }
    }
}
