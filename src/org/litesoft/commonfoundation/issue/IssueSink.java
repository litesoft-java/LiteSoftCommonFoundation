package org.litesoft.commonfoundation.issue;

public interface IssueSink {
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
