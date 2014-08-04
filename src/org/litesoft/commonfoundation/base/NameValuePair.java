package org.litesoft.commonfoundation.base;

import java.io.*;

public class NameValuePair implements NamedStringValueSource,
                                      Serializable {
    private static final long serialVersionUID = 1L;

    private /* final */ String mName, mValue;

    public NameValuePair( String pName, String pValue ) {
        mName = Confirm.significant( "Name", pName );
        mValue = pValue;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getValue() {
        return mValue;
    }

    @Deprecated /** for Serialization */
    protected NameValuePair() {
    }

    @Override
    public String toString() {
        return append( append( new StringBuilder(), mName ).append( "=" ), mValue ).toString();
    }

    private StringBuilder append( StringBuilder sb, String pStr ) {
        String zPrePost = "";
        if ( pStr != null ) {
            zPrePost = pStr.contains( "'" ) ? "\"" : "'";
        }
        return sb.append( zPrePost ).append( pStr ).append( zPrePost );
    }
}
