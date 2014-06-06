// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.html;

import org.litesoft.commonfoundation.base.*;

public class HTMLize implements HTMLConstants
{
    /**
     * HTML Encodes any markup in input (Spaces are left as Spaces).
     *
     * @param pText String to be processed.
     *
     * @return Processed pText with any markup properly HTML Encoded.
     */
    public static String escape( String pText )
    {
        return INSTANCE.process( pText );
    }

    /**
     * HTML Encodes any markup in input (Spaces are converted to &nbsp;).
     *
     * @param pText String to be processed.
     *
     * @return Processed pText with any markup properly HTML Encoded.
     */
    public static String escapeNoWrap( String pText )
    {
        return INSTANCE_NO_WRAP.process( pText );
    }

    public static final HTMLize INSTANCE = new HTMLize();
    public static final HTMLize INSTANCE_NO_WRAP = new HTMLize()
    {
        @Override
        protected String space()
        {
            return NBSP;
        }
    };
    public static final HTMLize INSTANCE_NO_ESCAPE = new HTMLize()
    {
        @Override
        public String process( String pText )
        {
            return pText;
        }
    };

    public String process( String pText )
    {
        if ( (pText == null) || (pText.length() == 0) )
        {
            return "";
        }
        StringBuilder zSB = new StringBuilder();
        for ( int i = 0; i < pText.length(); i++ )
        {
            char c = pText.charAt( i );
            switch ( c )
            {
                case ' ':
                    zSB.append( space() );
                    break;
                case '&':
                    zSB.append( AMPERSAND );
                    break;
                case '<':
                    zSB.append( LESS_THAN );
                    break;
                case '>':
                    zSB.append( GREATER_THAN );
                    break;
                case '"':
                    zSB.append( DOUBLE_QUOTE );
                    break;
                case '\n':
                    zSB.append( HTML_BR );
                    break;
                default:
                    if ( (' ' < c) && (c < 127) )
                    {
                        zSB.append( c );
                    }
                    else
                    {
                        zSB.append( "&#x" );
                        char[] chars = Hex.to4Chars( c );
                        for ( char aChar : chars )
                        {
                            zSB.append( aChar );
                        }
                        zSB.append( ';' );
                    }
                    break;
            }
        }
        return zSB.toString();
    }

    protected HTMLize()
    {
    }

    protected String space()
    {
        return " ";
    }
}
