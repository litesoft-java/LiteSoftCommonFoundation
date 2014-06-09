package org.litesoft.commonfoundation.typeutils.proxies;

public class IntegerProxy {
    private int mInt;

    public IntegerProxy( int pInt ) {
        mInt = pInt;
    }

    public void setInt( int pInt ) {
        mInt = pInt;
    }

    public int getInt() {
        return mInt;
    }

    public IntegerProxy inc() {
        mInt++;
        return this;
    }

    public IntegerProxy dec() {
        mInt--;
        return this;
    }

    public boolean isZero() {
        return (mInt == 0);
    }

    public boolean isNonZero() {
        return (mInt != 0);
    }

    @Override
    public String toString() {
        return Integer.toString( mInt );
    }
}
