// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

public class Characters {
    public static final int NEWLINE = 10;
    public static final int DEL = 127;
    public static final int HIBIT_SPACE = 160;

    public static final String ASCII_127 = "DEL";
    public static final String[] ASCII_LOW_32 =
            new String[]{
                    "NUL", "SOH", "STX", "ETX", "EOT", "ENQ", "ACK", "BEL", "BS", "HT", "LF", "VT", "FF", "CR", "SO", "SI", "DLE", "DC1", "DC2", "DC3",
                    "DC4", "NAK", "SYN", "ETB", "CAN", "EM", "SUB", "ESC", "FS", "GS", "RS", "US"
            };

    private static final String UNACCEPTABLE_NON_CONTROL_FILENAME_CHARACTERS = "|\\?*<\":>+[]/";

    public static boolean isUnacceptableNonControlFilenameChar( char c ) {
        return (UNACCEPTABLE_NON_CONTROL_FILENAME_CHARACTERS.indexOf( c ) != -1);
    }

    public static boolean isControlChar( char c ) {
        return (c < ' ') || ((DEL <= c) && (c < HIBIT_SPACE));
    }

    public static boolean isDisplayable7BitAsciiAllowingSpaceAndNewline( char c ) {
        return (c == NEWLINE) || ((' ' <= c) && (c < DEL));
    }

    public static boolean isNumeric( char pChar ) {
        return ('0' <= pChar) && (pChar <= '9');
    }

    public static boolean isAlphaNumericUnderScore7BitAscii( char c ) {
        return isNumeric( c ) || is7BitAlphaUnderScore( c );
    }

    public static boolean is7BitAlphaUnderScore( char c ) {
        return (c == '_') || is7BitAlpha( c );
    }

    public static boolean is7BitAlphaNumeric( char c ) {
        return isNumeric( c ) || is7BitAlpha( c );
    }

    public static boolean is7BitAlpha( char c ) {
        return isUpperCaseAsciiAlpha( c ) || isLowerCaseAsciiAlpha( c );
    }

    public static boolean isAsciiAlpha( char pChar ) {
        return isUpperCaseAsciiAlpha( pChar ) || isLowerCaseAsciiAlpha( pChar );
    }

    public static boolean isAsciiLetter( char pToTest ) {
        return isUpperCaseAsciiAlpha( pToTest ) || isLowerCaseAsciiAlpha( pToTest );
    }

    public static boolean is7BitAsciiAlpha( char pChar ) {
        return isUpperCaseAsciiAlpha( pChar ) || isLowerCaseAsciiAlpha( pChar );
    }

    public static boolean isAlpha( char pChar ) {
        return isUpperCaseAsciiAlpha( pChar ) || isLowerCaseAsciiAlpha( pChar );
    }

    public static boolean isAlphaNumeric( char pChar ) {
        return isAlpha( pChar ) || isNumeric( pChar );
    }

    public static boolean isUpperCaseAsciiAlpha( char pChar ) {
        return ('A' <= pChar) && (pChar <= 'Z');
    }

    public static boolean isLowerCaseAsciiAlpha( char pChar ) {
        return ('a' <= pChar) && (pChar <= 'z');
    }

    public static boolean isValidAttributeIdentifierStartCharacter( char pChar ) {
        // return Character.isJavaIdentifierStart( pChar ); // Not supported in GWT
        return (('A' <= pChar) && (pChar <= 'Z'));
    }

    public static boolean isValidAttributeIdentifierRestCharacter( char pChar ) {
        // return Character.isJavaIdentifierPart( pChar ); // Not supported in GWT
        return isAlphaNumeric( pChar );
    }

    public static boolean isFirstCharAsciiIdentifier( char pToTest ) {
        return isAsciiLetter( pToTest ) || (pToTest == '_');
    }

    public static boolean isNonFirstCharAsciiIdentifier( char pToTest ) {
        return isAsciiLetter( pToTest ) || (pToTest == '_') || Character.isDigit( pToTest );
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

    public static boolean isBetweenInclusive( char pChar, char pLowerInclusive, char pUpperInclusive ) {
        return (pLowerInclusive <= pChar) && (pChar <= pUpperInclusive);
    }

    private static final String ALPHA_BASE_26 = "abcdefghijklmnopqrstuvwxyz";

    public static int fromLowercaseAlphaBase26( char c ) {
        return ALPHA_BASE_26.indexOf( c );
    }

    public static char toLowercaseAlphaBase26( int p0to25 )
            throws IndexOutOfBoundsException {
        return ALPHA_BASE_26.charAt( p0to25 );
    }

    private static final String BASE_36 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static int fromBase36( char c ) {
        return BASE_36.indexOf( c );
    }

    public static char toBase36( int p0to35 )
            throws IndexOutOfBoundsException {
        return BASE_36.charAt( p0to35 );
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
}
