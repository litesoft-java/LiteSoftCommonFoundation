// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.stringmatching;

public abstract class AbstractSingleStringMatcher extends AbstractStringMatcher
{
    protected String mToMatch;

    protected AbstractSingleStringMatcher( int pMinLength, boolean pIgnoreCase, String pToMatch )
    {
        super( pMinLength, pIgnoreCase );
        mToMatch = pToMatch;
    }
}
