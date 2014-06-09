package org.litesoft.commonfoundation.issue;

import org.litesoft.commonfoundation.typeutils.*;

@SuppressWarnings("UnusedDeclaration")
public class IssueOverride {
    public static final IssueOverride[] EMPTY_ARRAY = new IssueOverride[0];

    public enum Level {OK, Warning, Error}

    public static IssueOverride forKey( String pKey, Level pLevel ) {
        return new IssueOverride( pLevel, null, Strings.assertNotEmpty( "Key", pKey ) );
    }

    public static IssueOverride forGroup( String pGroup, Level pLevel ) {
        return new IssueOverride( pLevel, null, Strings.assertNotEmpty( "Group", pGroup ) );
    }

    public Level getLevel() {
        return mLevel;
    }

    public String getGroup() {
        return mGroup;
    }

    public String getKey() {
        return mKey;
    }

    private final Level mLevel;
    private final String mGroup;
    private final String mKey;

    private IssueOverride( Level pLevel, String pGroup, String pKey ) {
        mLevel = Objects.assertNotNull( "Level", pLevel );
        mGroup = Strings.noEmpty( pGroup );
        mKey = Strings.noEmpty( pKey );
    }
}
