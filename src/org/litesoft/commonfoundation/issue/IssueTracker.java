package org.litesoft.commonfoundation.issue;

public interface IssueTracker extends IssueSink {
    public boolean hasIssues();

    public boolean hasErrors();

    public boolean hasWarnings();

    /**
     * Reports all Issues to the Console and returns weather there were Errors!
     */
    public boolean reportIssues();
}
