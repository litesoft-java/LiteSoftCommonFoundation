package org.litesoft.commonfoundation.html;

public enum HtmlEntity {
    AMP( '&', "&amp;" ), //
    LT( '<', "&lt;" ), //
    GT( '>', "&gt;" ), //
    QUOT( '"', "&quot;" );

    private char character;
    private String string;

    private HtmlEntity( char character, String string ) {
        this.character = character;
        this.string = string;
    }

    public char getCharacter() {
        return character;
    }

    public String getString() {
        return string;
    }

    public boolean wouldMatch( String entity ) {
        return string.equalsIgnoreCase( entity );
    }

    public static void append( char c, StringBuilder sb ) {
        switch ( c ) {
            case '&':
                sb.append( AMP.getString() );
                break;
            case '<':
                sb.append( LT.getString() );
                break;
            case '>':
                sb.append( GT.getString() );
                break;
            case '"':
                sb.append( QUOT.getString() );
                break;
            default:
                if ( (c == '\n') || ((' ' <= c) && (c <= 126)) ) {
                    sb.append( c );
                } else {
                    sb.append( toDecimalForm( c ) );
                }
                break;
        }
    }

    public static char fromStringForm( String entity ) {
        if ( AMP.wouldMatch( entity ) ) {
            return AMP.getCharacter();
        }
        if ( LT.wouldMatch( entity ) ) {
            return LT.getCharacter();
        }
        if ( GT.wouldMatch( entity ) ) {
            return GT.getCharacter();
        }
        if ( QUOT.wouldMatch( entity ) ) {
            return QUOT.getCharacter();
        }
        return fromDecimalForm( entity );
    }

    public static String toDecimalForm( char c ) {
        return "&#" + ((int) c) + ';';
    }

    public static char fromDecimalForm( String decimalForm ) {
        if ( (decimalForm != null) && (decimalForm.length() > 3) && decimalForm.startsWith( "&#" ) && decimalForm.endsWith( ";" ) ) {
            try {
                int value = Integer.parseInt( decimalForm.substring( 2, decimalForm.length() - 1 ) );
                if ( (Character.MIN_VALUE <= value) && (value <= Character.MAX_VALUE) ) {
                    return (char) value;
                }
            }
            catch ( NumberFormatException whatever ) {
                // Fall Thru
            }
        }
        throw new IllegalArgumentException( "Not a Html Decimal Entity: '" + decimalForm + "'" );
    }

    public static final String NBSP = "&nbsp;";
    public static final String DOUBLE_QUOTE = "&quot;";

    public static final String HTML_BR = "<br />";
}
