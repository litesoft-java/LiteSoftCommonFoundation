package org.litesoft.commonfoundation.issue;

public interface IssueManager extends IssueSink {
    /**
     * Reports all Issues to the Console and return weather there were Errors!
     */
    public boolean reportIssues();

    public boolean hasIssues();

    public boolean hasErrors();

    public boolean hasWarnings();
}
