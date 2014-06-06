// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.stringmatching;

import org.litesoft.commonfoundation.typeutils.*;

public interface StringMatcher
{
    public boolean matches( String pInQuestion );

    public static final StringMatcher ALL = new StringMatcher()
    {
        @Override
        public boolean matches( String pInQuestion )
        {
            return true;
        }

        @Override
        public String toString()
        {
            return "ALL";
        }
    };

    public static final StringMatcher NONE = new StringMatcher()
    {
        @Override
        public boolean matches( String pInQuestion )
        {
            return false;
        }

        @Override
        public String toString()
        {
            return "NONE";
        }
    };

    public static final StringMatcher EMPTY = new StringMatcher()
    {
        @Override
        public boolean matches( String pInQuestion )
        {
            return (null == Strings.noEmpty( pInQuestion ));
        }

        @Override
        public String toString()
        {
            return "EMPTY";
        }
    };
}
