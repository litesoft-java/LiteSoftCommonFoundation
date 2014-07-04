package org.litesoft.commonfoundation.issue;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.indent.*;
import org.litesoft.commonfoundation.typeutils.*;

public class Issue implements SourceAccessor {
    public static class Builder {
        public Issue build() {
            return new Issue( mGroup, mKeyDetail, mDetails, mSource );
        }

        public Builder with( Source pSource ) {
            mSource = pSource;
            return this;
        }

        public Builder with( String pDetailLine ) {
            return with( StringTree.from( Confirm.significant( "DetailLine", pDetailLine ) ) );
        }

        public Builder with( StringTree pDetails ) {
            mDetails = Confirm.isNotNull( "Details", pDetails );
            return this;
        }

        public Builder with( Exception e ) {
            return with( StringTree.from( "Exception", Strings.toLines( Throwables.printStackTraceToString( e ) ) ) );
        }

        private Builder( String pGroup, Object pKeyDetail ) {
            mGroup = Confirm.significant( "Group", pGroup );
            mKeyDetail = Confirm.significant( "KeyDetail", toString( Confirm.isNotNull( "KeyDetail", pKeyDetail ) ) );
        }

        private static String toString( Object pKeyDetail ) {
            return (pKeyDetail instanceof Class) ? ClassName.simpleFromClass( (Class) pKeyDetail ) : pKeyDetail.toString();
        }

        private final String mGroup;
        private final String mKeyDetail;
        private StringTree mDetails;
        private Source mSource;
    }

    public static Builder of( @SignificantText String pGroup, @SignificantText Object pKeyDetail ) {
        return new Builder( pGroup, pKeyDetail );
    }

    public IssueOverride.Level level( IssueOverride.Level pDefault, IssueOverride... pOverrides ) {
        if ( pOverrides != null ) {
            for ( IssueOverride zOverride : pOverrides ) {
                if ( zOverride.getKey().equals( mKey ) ) {
                    String zGroup = zOverride.getGroup();
                    if ( (zGroup == null) || zGroup.equals( mGroup ) ) {
                        return zOverride.getLevel();
                    }
                }
            }
        }
        return pDefault;
    }

    public @SignificantText  String getGroup() {
        return mGroup;
    }

    public @SignificantText String getKeyDetail() {
        return mKeyDetail;
    }

    public @Nullable StringTree getDetails() {
        return mDetails;
    }

    @Override
    public @Nullable Source getSource() {
        return mSource;
    }

    private final String mGroup;
    private final String mKeyDetail;
    private final StringTree mDetails;
    private final Source mSource;
    private final String mKey;

    private Issue( String pGroup, String pKeyDetail, StringTree pDetails, Source pSource ) {
        mGroup = pGroup;
        mKeyDetail = pKeyDetail;
        mSource = pSource;
        mDetails = pDetails;
        mKey = mGroup + "(" + pKeyDetail + ")";
    }
}
