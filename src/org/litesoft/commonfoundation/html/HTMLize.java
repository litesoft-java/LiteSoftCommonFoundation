// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.html;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.typeutils.*;
import static org.litesoft.commonfoundation.html.HtmlEntity.*;

public class HTMLize {
    /**
     * NBSPs are converted to a space.
     */
    public static String convertNBSPsToSpaces( String text ) {
        if ( text != null ) {
            int at = text.indexOf( '&' );
            if ( at != -1 ) {
                int adjustLengthNBSPtoSpace = NBSP.length() - 1;
                int sourceTextDelta = 0;
                String index = text.toLowerCase();
                for ( int from = at; -1 != (at = index.indexOf( NBSP, from )); from = at + NBSP.length() ) {
                    int sourceAt = at + sourceTextDelta;
                    text = text.substring( 0, sourceAt ) + ' ' + text.substring( sourceAt + NBSP.length() );
                    sourceTextDelta -= adjustLengthNBSPtoSpace;
                }
            }
        }
        return text;
    }

    public static String spacesToNBSP( String pText ) {
        return Strings.replace( ConstrainTo.notNull( pText ), " ", NBSP );
    }

    /**
     * HTML Encodes any markup in input (Spaces are left as Spaces).
     *
     * @param pText String to be processed.
     *
     * @return Processed pText with any markup properly HTML Encoded.
     */
    public static String escape( String pText ) {
        return INSTANCE.process( pText );
    }

    /**
     * HTML Encodes any markup in input (Spaces are converted to &nbsp;).
     *
     * @param pText String to be processed.
     *
     * @return Processed pText with any markup properly HTML Encoded.
     */
    public static String escapeNoWrap( String pText ) {
        return INSTANCE_NO_WRAP.process( pText );
    }

    public static final HTMLize INSTANCE = new HTMLize();
    public static final HTMLize INSTANCE_NO_WRAP = new HTMLize() {
        @Override
        protected String space() {
            return NBSP;
        }
    };
    public static final HTMLize INSTANCE_NO_ESCAPE = new HTMLize() {
        @Override
        public String process( String pText ) {
            return pText;
        }
    };

    public String process( String pText ) {
        if ( (pText == null) || (pText.length() == 0) ) {
            return "";
        }
        StringBuilder zSB = new StringBuilder();
        for ( int i = 0; i < pText.length(); i++ ) {
            char c = pText.charAt( i );
            switch ( c ) {
                case ' ':
                    zSB.append( space() );
                    break;
                case '&':
                    zSB.append( AMP.getString() );
                    break;
                case '<':
                    zSB.append( LT.getString() );
                    break;
                case '>':
                    zSB.append( GT.getString() );
                    break;
                case '"':
                    zSB.append( DOUBLE_QUOTE );
                    break;
                case '\n':
                    zSB.append( HTML_BR );
                    break;
                default:
                    if ( (' ' < c) && (c < 127) ) {
                        zSB.append( c );
                    } else {
                        zSB.append( "&#x" );
                        char[] chars = Hex.to4Chars( c );
                        for ( char aChar : chars ) {
                            zSB.append( aChar );
                        }
                        zSB.append( ';' );
                    }
                    break;
            }
        }
        return zSB.toString();
    }

    public static String nbsp4spaces( String pText ) {
        return Strings.replace( pText, " ", NBSP );
    }

    protected HTMLize() {
    }

    protected String space() {
        return " ";
    }
}
