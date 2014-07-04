package org.litesoft.commonfoundation.issue;

import org.litesoft.commonfoundation.annotations.*;

public interface IssueSink {
    public <Value> Value addIssueIfNotRecognized( Value pRecognized, Source pSource, String pKeyDetails, String pLookupValue );

    /**
     * @return null
     */
    public <T> T addError( @NotNull Issue pIssue );

    /**
     * @return null
     */
    public <T> T addWarning( @NotNull Issue pIssue );
}
