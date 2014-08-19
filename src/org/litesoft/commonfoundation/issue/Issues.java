package org.litesoft.commonfoundation.issue;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.indent.*;
import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

public class Issues implements Indentable {

    public Issues( @SignificantText String pName ) {
        mName = Confirm.significant( "Name", pName );
    }

    private final String mName;
    private final Groups mIssues = new Groups();

    public boolean hasIssues() {
        return !mIssues.isEmpty();
    }

    public void add( @NotNull Issue pIssue ) {
        mIssues.add( pIssue );
    }

    @Override
    public String toString() {
        return StringIndentableWriter.formatWith( this );
    }

    @Override
    public IndentableWriter appendTo( @NotNull IndentableWriter pWriter ) {
        if ( !hasIssues() ) {
            pWriter.printLn( "No ", mName, "!" );
        } else {
            pWriter.printLn( formatLabel( mName, mIssues.count() ) );
            pWriter.indent();
            mIssues.appendTo( pWriter );
            pWriter.outdent();
        }
        return pWriter;
    }

    private static String formatLabel( Object pKey, int pCount ) {
        return (pCount < 2) ? (pKey + ":") : (pKey + " (" + pCount + "):");
    }

    private interface IssueAddable extends Indentable {
        int count();

        void add( @NotNull Issue pIssue );
    }

    private static abstract class SortedKeyMap<Key extends Comparable<Key>, Value extends IssueAddable> implements IssueAddable {
        private final Map<Key, Value> mMap = Maps.newHashMap();

        public boolean isEmpty() {
            return mMap.isEmpty();
        }

        @Override
        public int count() {
            int zCount = 0;
            for ( Value zValue : mMap.values() ) {
                zCount += zValue.count();
            }
            return zCount;
        }

        @Override
        public void add( @NotNull Issue pIssue ) {
            Key zKey = getKey( pIssue );
            Value zValue = mMap.get( zKey );
            if ( zValue == null ) {
                mMap.put( zKey, zValue = createValue( pIssue ) );
            }
            zValue.add( pIssue );
        }

        protected abstract
        @NotNull
        Key getKey( @NotNull Issue pIssue );

        protected abstract
        @NotNull
        Value createValue( @NotNull Issue pIssue );

        @Override
        public String toString() {
            return StringIndentableWriter.formatWith( this );
        }

        @Override
        public IndentableWriter appendTo( @NotNull IndentableWriter pWriter ) {
            List<Key> zKeys = Lists.newArrayList( mMap.keySet() );
            Collections.sort( zKeys );
            for ( Key zKey : zKeys ) {
                Value zValue = mMap.get( zKey );
                appendTo( pWriter, zKey, zValue );
            }
            return pWriter;
        }

        protected void appendTo( @NotNull IndentableWriter pWriter, Key pKey, @NotNull Value pValue ) {
            pWriter.printLn( formatLabel( pKey, pValue.count() ) );
            pWriter.indent();
            pValue.appendTo( pWriter );
            pWriter.outdent();
        }
    }

    private static class Groups extends SortedKeyMap<String, KeyDetails> {
        @Override
        protected
        @NotNull
        String getKey( @NotNull Issue pIssue ) {
            return pIssue.getGroup();
        }

        @Override
        protected
        @NotNull
        KeyDetails createValue( @NotNull Issue pIssue ) {
            return new KeyDetails();
        }
    }

    private static class KeyDetails extends SortedKeyMap<String, Details> {
        @Override
        protected
        @NotNull
        String getKey( @NotNull Issue pIssue ) {
            return pIssue.getKeyDetail();
        }

        @Override
        protected
        @NotNull
        Details createValue( @NotNull Issue pIssue ) {
            return new Details();
        }
    }

    private static class Details extends SortedKeyMap<StringTree, IssueList> {
        private static final StringTree NO_DETAILS = StringTree.from( "" );

        @Override
        protected
        @NotNull
        StringTree getKey( @NotNull Issue pIssue ) {
            return ConstrainTo.notNull( pIssue.getDetails(), NO_DETAILS );
        }

        @Override
        protected
        @NotNull
        IssueList createValue( @NotNull Issue pIssue ) {
            return new IssueList();
        }

        @Override
        protected void appendTo( IndentableWriter pWriter, StringTree pKey, IssueList pIssueList ) {
            if ( pKey == NO_DETAILS ) {
                pIssueList.appendTo( pWriter );
            } else {
                pKey.appendTo( pWriter );
                pWriter.indent();
                pIssueList.appendTo( pWriter );
                pWriter.outdent();
            }
        }
    }

    private static class IssueList implements IssueAddable {
        private final List<Issue> mIssues = Lists.newArrayList();

        @Override
        public int count() {
            return mIssues.size();
        }

        @Override
        public void add( @NotNull Issue pIssue ) {
            mIssues.add( pIssue );
        }

        @Override
        public String toString() {
            return StringIndentableWriter.formatWith( this );
        }

        @Override
        public IndentableWriter appendTo( @NotNull IndentableWriter pWriter ) {
            SourceAsTree zTree = new SourceAsTree();
            for ( Issue zIssue : mIssues ) {
                Source zSource = zIssue.getSource();
                if ( zSource != null ) {
                    zTree.add( zSource.toList() );
                }
            }
            zTree.appendTo( pWriter );
            return pWriter;
        }
    }

    private static class SourceAsTree implements Indentable {
        private final Map<String, SourceAsTree> mMap = Maps.newHashMap();

        public void add( List<String> pSource ) {
            if ( Currently.isNullOrEmpty( pSource ) ) {
                mMap.put( "No Source!", null );
            } else if ( pSource.size() == 1 ) {
                mMap.put( "From (" + pSource.get( 0 ) + ").", null );
            } else {
                Iterator<String> zIterator = pSource.iterator();
                add( "From: " + zIterator.next(), zIterator );
            }
        }

        public void add( String pKey, Iterator<String> pRest ) {
            if ( !pRest.hasNext() ) {
                mMap.put( "@ (" + pKey + ").", null );
            } else {
                SourceAsTree zNext = mMap.get( pKey );
                if ( zNext == null ) {
                    mMap.put( pKey, zNext = new SourceAsTree() );
                }
                zNext.add( pRest.next(), pRest );
            }
        }

        @Override
        public IndentableWriter appendTo( @NotNull IndentableWriter pWriter ) {
            List<String> zKeys = Lists.newArrayList( mMap.keySet() );
            Collections.sort( zKeys );
            for ( String zKey : zKeys ) {
                pWriter.printLn( zKey );
                SourceAsTree zValue = mMap.get( zKey );
                if ( zValue != null ) {
                    pWriter.indent();
                    zValue.appendTo( pWriter );
                    pWriter.outdent();
                }
            }
            return pWriter;
        }
    }
}
