package org.litesoft.commonfoundation.issue;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.indent.*;

public class IssueTrackerImpl implements IssueTracker,
                                         Indentable {
    public static final String SEPARATOR = "*****************************************************************************";

    /**
     * @param pOverrides No Nulls!
     */
    public IssueTrackerImpl( IssueOverride... pOverrides ) {
        mOverrides = ConstrainTo.notNull( pOverrides, IssueOverride.EMPTY_ARRAY );
    }

    @Override
    public boolean hasIssues() {
        return hasWarnings() || hasErrors();
    }

    @Override
    public boolean hasErrors() {
        return mErrors.hasIssues();
    }

    @Override
    public boolean hasWarnings() {
        return mWarnings.hasIssues();
    }

    @Override
    public <Value> Value addIssueIfNotRecognized( Value pRecognized, Source pSource, String pKeyDetails, String pLookupValue ) {
        if ( pRecognized == null ) {
            addWarning( Issue.of( "NotRecognized", pKeyDetails ).with( pLookupValue ).with( pSource ).build() );
        }
        return pRecognized;
    }

    /**
     * @return null
     */
    @Override
    public <T> T addError( Issue pIssue ) {
        return addIssue( pIssue, IssueOverride.Level.Error );
    }

    /**
     * @return null
     */
    @Override
    public <T> T addWarning( Issue pIssue ) {
        return addIssue( pIssue, IssueOverride.Level.Warning );
    }

    public boolean reportIssues() {
        if ( !hasIssues() ) {
            return false;
        }
        StringIndentableWriter zWriter = new StringIndentableWriter( "    " );
        zWriter.printLn();
        zWriter.printLn( SEPARATOR );
        appendTo( zWriter );
        zWriter.printLn( SEPARATOR );
        zWriter.close();
        System.out.println( zWriter.toString() );
        return hasErrors();
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
        if ( !hasIssues() ) {
            pWriter.printLn( "No Issues!" );
            return;
        }
        pWriter.printLn( "Issues:" );
        addBeforeWarnings( pWriter );
        if ( hasWarnings() ) {
            pWriter.indent();
            mWarnings.appendTo( pWriter );
            pWriter.outdent();
        }
        if ( hasErrors() ) {
            pWriter.indent();
            mErrors.appendTo( pWriter );
            pWriter.outdent();
        }
        addAfterErrors( pWriter );
    }

    @SuppressWarnings("UnusedParameters")
    protected void addBeforeWarnings( IndentableWriter pWriter ) {
    }

    @SuppressWarnings("UnusedParameters")
    protected void addAfterErrors( IndentableWriter pWriter ) {
    }

    private final IssueOverride[] mOverrides;
    private final Issues mErrors = new Issues( "Errors" );
    private final Issues mWarnings = new Issues( "Warnings" );

    private <T> T addIssue( Issue pIssue, IssueOverride.Level pDefaultLevel ) {
        IssueOverride.Level zLevel = pIssue.level( pDefaultLevel, mOverrides );
        if ( IssueOverride.Level.OK != zLevel ) {
            Issues zIssues = (IssueOverride.Level.Error == zLevel) ? mErrors : mWarnings;
            zIssues.add( pIssue );
        }
        return null;
    }
}
