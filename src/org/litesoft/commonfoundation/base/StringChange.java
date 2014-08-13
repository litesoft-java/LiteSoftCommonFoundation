package org.litesoft.commonfoundation.base;

public class StringChange {
    private final String mFrom, mTo;

    public StringChange( String pFrom, String pTo ) {
        mFrom = pFrom;
        mTo = pTo;
    }

    public String getFrom() {
        return mFrom;
    }

    public String getTo() {
        return mTo;
    }
}
