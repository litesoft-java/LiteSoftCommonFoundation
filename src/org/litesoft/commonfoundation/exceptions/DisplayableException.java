// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.exceptions;

public interface DisplayableException {
    public static final String[] NO_PARAMS = new String[0];

    public String getToResolveIdentifier();

    public String[] getParams();
}
