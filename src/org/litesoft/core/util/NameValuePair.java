package org.litesoft.core.util;

import java.io.*;

public class NameValuePair implements Serializable
{
    private static final long serialVersionUID = 1L;

    private /* final */ String mName, mValue;

    public NameValuePair( String pName, String pValue )
    {
        mName = pName;
        mValue = pValue;
    }

    public String getName()
    {
        return mName;
    }

    public String getValue()
    {
        return mValue;
    }

    @Deprecated /** for Serialization */
    protected NameValuePair()
    {
    }
}
