package org.litesoft.commonfoundation.typeutils.proxies;

public class DoublesSum {
    private Double mSum;

    public Double add( Double pValue ) {
        if ( pValue != null ) {
            mSum = (mSum == null) ? pValue : (mSum + pValue);
        }
        return pValue;
    }

    public Double getSum() {
        return mSum;
    }
}
