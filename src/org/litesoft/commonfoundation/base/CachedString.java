// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.base;

public class CachedString
{
    private boolean mValid = false;
    private String mValue = null;

    public boolean isValid()
    {
        return mValid;
    }

    public String getValue()
    {
        return mValue;
    }

    public void setValue( String pValue )
    {
        mValid = true;
        mValue = pValue;
    }
}
