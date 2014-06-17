package org.litesoft.commonfoundation.issue;

public interface IssueCollector {
    /**
     * Reports all Issues to the Console and return weather there were Errors!
     */
    public boolean reportIssues();

    public boolean hasIssues();

    public boolean hasErrors();

    public boolean hasWarnings();

    public <Value> Value addIssueIfNotRecognized( Value pRecognized, Source pSource, String pKeyDetails, String pLookupValue );

    /**
     * @return null
     */
    public <T> T addError( Issue pIssue );

    /**
     * @return null
     */
    public <T> T addWarning( Issue pIssue );
}
