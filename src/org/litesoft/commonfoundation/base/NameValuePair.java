package org.litesoft.commonfoundation.base;

import java.io.*;

public class NameValuePair implements NamedStringValueSource, Serializable
{
    private static final long serialVersionUID = 1L;

    private /* final */ String mName, mValue;

    public NameValuePair( String pName, String pValue )
    {
        mName = pName;
        mValue = pValue;
    }

    @Override
    public String getName()
    {
        return mName;
    }

    @Override
    public String getValue()
    {
        return mValue;
    }

    @Deprecated /** for Serialization */
    protected NameValuePair()
    {
    }
}
