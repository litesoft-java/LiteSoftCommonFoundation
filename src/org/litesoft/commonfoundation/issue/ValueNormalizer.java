package org.litesoft.commonfoundation.issue;

public abstract class ValueNormalizer<Value> {
    protected final IssueSink mIssueSink;
    protected final Value mExpected;

    public ValueNormalizer( IssueSink pIssueSink, Value pExpected ) {
        mIssueSink = pIssueSink;
        mExpected = pExpected;
    }

    public ValueNormalizer( IssueSink pIssueSink ) {
        this( pIssueSink, null);
    }

    abstract public Value normalizer( String pFrom, Source pSource );
}
