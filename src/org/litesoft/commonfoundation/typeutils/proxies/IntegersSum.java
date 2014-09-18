package org.litesoft.commonfoundation.typeutils.proxies;

public class IntegersSum {
    private Integer mSum;

    public Integer add( Integer pValue ) {
        if ( pValue != null ) {
            mSum = (mSum == null) ? pValue : (mSum + pValue);
        }
        return pValue;
    }

    public IntegersSum accumulate( Integer pValue ) {
        add( pValue );
        return this;
    }

    public Integer getSum() {
        return mSum;
    }
}
