package org.litesoft.commonfoundation.issue;

public abstract class ValueNormalizer<Value> {
    protected final IssueCollector mIssueCollector;
    protected final Value mExpected;

    public ValueNormalizer( IssueCollector pIssueCollector, Value pExpected ) {
        mIssueCollector = pIssueCollector;
        mExpected = pExpected;
    }

    public ValueNormalizer( IssueCollector pIssueCollector ) {
        this(pIssueCollector, null);
    }

    abstract public Value normalizer( String pFrom, Source pSource );
}
