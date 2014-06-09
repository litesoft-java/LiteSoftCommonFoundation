package org.litesoft.commonfoundation.typeutils.proxies;

public class DoubleProxy {
    private double mDouble;

    public DoubleProxy( double pDouble ) {
        mDouble = pDouble;
    }

    public void setDouble( double pDouble ) {
        mDouble = pDouble;
    }

    public double getDouble() {
        return mDouble;
    }

    public boolean isZero() {
        return 0.0 == mDouble;
    }
}
