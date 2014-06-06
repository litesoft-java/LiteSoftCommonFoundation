// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.exceptions;

public interface DisplayableException
{
    public String getToResolveIdentifier();

    public String[] getParams();
}
