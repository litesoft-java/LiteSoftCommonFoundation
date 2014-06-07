// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.exceptions;

public interface DisplayableException {
    public String getToResolveIdentifier();

    public String[] getParams();
}
