// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

public class Characters {
    public static final String ASCII_127 = "DEL";
    public static final String[] ASCII_LOW_32 =
            new String[]{
                    "NUL", "SOH", "STX", "ETX", "EOT", "ENQ", "ACK", "BEL", "BS", "HT", "LF", "VT", "FF", "CR", "SO", "SI", "DLE", "DC1", "DC2", "DC3",
                    "DC4", "NAK", "SYN", "ETB", "CAN", "EM", "SUB", "ESC", "FS", "GS", "RS", "US"
            };

    public static boolean isAsciiLetter( char pToTest ) {
        return (('A' <= pToTest) && (pToTest <= 'Z')) || (('a' <= pToTest) && (pToTest <= 'z'));
    }

    public static boolean isNoSpaceAscii( char pToTest ) {
        return ((' ' < pToTest) && (pToTest < 127));
    }

    public static boolean isNoCtrlAscii( char pToTest ) {
        return ((' ' <= pToTest) && (pToTest < 127));
    }

    public static boolean isPathSep( char pChar ) {
        return (pChar == '/') || (pChar == '\\') || (pChar == ':');
    }

    public static boolean isNumeric( char pChar ) {
        return ('0' <= pChar) && (pChar <= '9');
    }

    public static boolean is7BitAsciiAlpha( char pChar ) {
        return (('A' <= pChar) && (pChar <= 'Z')) || (('a' <= pChar) && (pChar <= 'z'));
    }

    public static boolean isAlpha( char pChar ) {
        return is7BitAsciiAlpha( pChar );
    }

    public static boolean isAlphaNumeric( char pChar ) {
        return isAlpha( pChar ) || isNumeric( pChar );
    }

    public static boolean isValidAttributeIdentifierStartCharacter( char pChar ) {
        // return Character.isJavaIdentifierStart( pChar ); // Not supported in GWT
        return (('A' <= pChar) && (pChar <= 'Z'));
    }

    public static boolean isValidAttributeIdentifierRestCharacter( char pChar ) {
        // return Character.isJavaIdentifierPart( pChar ); // Not supported in GWT
        return isAlphaNumeric( pChar );
    }

    public static String cvtCharForDisplay( char pChar ) {
        if ( pChar < 0 ) // is this even possible?
        {
            return "-" + cvtCharForDisplay( (char) -pChar );
        }
        if ( pChar < ' ' ) {
            return ASCII_LOW_32[pChar];
        }
        if ( pChar < 127 ) {
            return String.valueOf( pChar );
        }
        if ( pChar == 127 ) {
            return ASCII_127;
        }
        if ( pChar < 256 ) {
            return "HiBit-" + cvtCharForDisplay( (char) (pChar - 128) );
        }

        return "x" + Integer.toString( pChar, 16 ); // toHex
    }

    public static boolean isFirstCharAsciiIdentifier( char pToTest ) {
        return isAsciiLetter( pToTest ) || (pToTest == '_');
    }

    public static boolean isNonFirstCharAsciiIdentifier( char pToTest ) {
        return isAsciiLetter( pToTest ) || (pToTest == '_') || Character.isDigit( pToTest );
    }
}
