// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.annotations.*;
import org.litesoft.commonfoundation.jdk8standins.function.*;
import org.litesoft.commonfoundation.stringmatching.*;

import java.util.*;

public class Strings {
    public static final String[] EMPTY_ARRAY = new String[0];
    public static final char CRchar = '\r';
    public static final char NLchar = '\n';
    public static final TypeTransformer<String> TYPE_TRANSFORMER = new TypeTransformer<String>() {
        @Override
        public String transformNonNull( Object pObject ) {
            return pObject.toString();
        }
    };

    private static final String SPACES_32 = "                                "; // 32 count-em! spaces

    private final String mFirstPart, mRest;

    private Strings( String pFirstPart, String pRest ) {
        mFirstPart = pFirstPart;
        mRest = pRest;
    }

    public String getFirstPart() {
        return mFirstPart;
    }

    public String getRest() {
        return mRest;
    }

    /**
     * Adjust a String Array so that it is either null (error condition) or has the <code>DesiredLength</code>.
     * <p/>
     * Note: The <code>Source</code> (original) array may be return.
     *
     * @param pDesiredLength of the resulting array (must NOT be < 0)
     * @param pSource        array
     *
     * @return null if the <code>Source</code> is longer then the <code>DesiredLength</code>; otherwise
     * an Array that is exactly <code>DesiredLength</code> long (padded with nulls).
     */
    public static
    @Nullable
    String[] expectArray( int pDesiredLength, @Nullable String[] pSource ) {
        Integers.assertNonNegative( "DesiredLength", pDesiredLength );
        int zCurrentLength = (pSource == null) ? 0 : pSource.length;
        if ( pDesiredLength < zCurrentLength ) {
            return null;
        }
        if ( pDesiredLength == zCurrentLength ) {
            return pSource;
        }
        String[] rv = new String[pDesiredLength];
        if ( zCurrentLength != 0 ) {
            System.arraycopy( pSource, 0, rv, 0, zCurrentLength );
        }
        return rv;
    }

    /**
     * Return an String Array made up of the end elements of <code>Strings</code>, but who's first element is
     * the <code>FromIndex</code> element of <code>Strings</code>.
     * <p/>
     * Note: It is OK for <code>Strings</code> to be too short (or even null).
     * Note: The <code>Strings</code> (original) array may be return.
     *
     * @param pStrings   array
     * @param pFromIndex is the index into <code>Strings</code> to start the resulting array from (must be >= 0).
     *
     * @return null if
     */
    public static
    @Nullable
    String[] everythingFrom( @Nullable String[] pStrings, int pFromIndex ) {
        Integers.assertNonNegative( "FromIndex", pFromIndex );
        if ( (pStrings == null) || (pStrings.length <= pFromIndex) ) {
            return EMPTY_ARRAY;
        }
        String[] rv = new String[pStrings.length - pFromIndex];
        System.arraycopy( pStrings, pFromIndex, rv, 0, rv.length );
        return rv;
    }

    public static String spaces( int pSpaces ) {
        if ( pSpaces < 1 ) {
            return "";
        }
        if ( pSpaces <= SPACES_32.length() ) {
            return SPACES_32.substring( 0, pSpaces );
        }
        // Note use of StringBuilder as StringBuilder was added in 1.5
        StringBuilder retval = new StringBuilder( pSpaces );
        for ( int i = 0; i < pSpaces; i++ ) {
            retval.append( ' ' );
        }
        return retval.toString();
    }

    public static String path( String pSubDir, String pFileName ) {
        return isNullOrEmpty( pSubDir ) ? pFileName : (pSubDir + "/" + pFileName);
    }

    public static Strings parsePrefixOptionalSep( String pResponseText, String pPrefix, String pSep ) {
        if ( (null == (pPrefix = noEmpty( pPrefix ))) || !pResponseText.startsWith( pPrefix ) ) {
            return null;
        }
        int zSepAt = (null == (pSep = noEmpty( pSep ))) ? -1 : pResponseText.indexOf( pSep );
        if ( zSepAt == -1 ) {
            return new Strings( pResponseText.substring( pPrefix.length() ), null );
        }
        return new Strings( pResponseText.substring( pPrefix.length(), zSepAt ),
                            pResponseText.substring( zSepAt + pSep.length() ) );
    }

    public static String dupChars( char pCharToDup, int pDupCount ) {
        if ( pDupCount < 1 ) {
            return "";
        }
        StringBuilder sb = new StringBuilder( pDupCount );
        while ( pDupCount-- > 0 ) {
            sb.append( pCharToDup );
        }
        return sb.toString();
    }

    public static String dup( String pToDup, int pDupCount ) {
        if ( pToDup == null ) {
            return null;
        }
        if ( pDupCount < 1 ) {
            return "";
        }
        StringBuilder sb = new StringBuilder( pDupCount * pToDup.length() );
        while ( pDupCount-- > 0 ) {
            sb.append( pToDup );
        }
        return sb.toString();
    }

    public static boolean contains( String pString, String pToFind ) {
        return Objects.isNotNull( pString ) && isNotNullOrEmpty( pToFind ) && pString.contains( pToFind );
    }

    public static boolean contains( String pString, char pToFind ) {
        return Objects.isNotNull( pString ) && (-1 != pString.indexOf( pToFind ));
    }

    public static boolean isNullOrEmpty( String pStringToCheck ) {
        return ((pStringToCheck == null) || (pStringToCheck.trim().length() == 0));
    }

    public static boolean isNotNullOrEmpty( String pStringToCheck ) {
        return ((pStringToCheck != null) && (pStringToCheck.trim().length() != 0));
    }

    public static String deNull( CharSequence pString ) {
        return deNull( pString, "" );
    }

    public static String deNull( CharSequence pString, String pDefault ) {
        return (pString != null) ? pString.toString() : pDefault;
    }

    public static String deNull( CharSequence pString, Supplier<String> pDefault ) {
        return (pString != null) ? pString.toString() : pDefault.get();
    }

    public static String[] deNull( String[] pStrings ) {
        return (pStrings != null) ? pStrings : EMPTY_ARRAY;
    }

    public static String noEmpty( String pString ) {
        return noEmpty( pString, null );
    }

    public static String noEmpty( String pString, String pDefault ) {
        if ( pString != null ) {
            pString = pString.trim();
            if ( pString.length() != 0 ) {
                return pString;
            }
        }
        return pDefault;
    }

    public static String deEmpty( String pString, String pDefault ) { // TODO: DUP!
        pString = noEmpty( pString );
        return (pString != null) ? pString : pDefault;
    }

    public static String[] noEmpty( String[] pStrings ) {
        return (pStrings == null || pStrings.length == 0) ? null : pStrings;
    }

    public static String[] noEmpties( String[] pStrings ) {
        if ( pStrings != null ) {
            for ( int i = pStrings.length; --i >= 0; ) {
                String zString = noEmpty( pStrings[i] );
                if ( zString == null ) {
                    pStrings = removeStringFromArray( pStrings, i );
                }
                pStrings[i] = zString;
            }
            if ( pStrings.length == 0 ) {
                pStrings = null;
            }
        }
        return pStrings;
    }

    public static boolean allEmpty( String[] pParts ) {
        if ( pParts != null ) {
            for ( String part : pParts ) {
                if ( isNotNullOrEmpty( part ) ) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isNullOrEmptyOrSpaces( String pStringToCheck ) {
        return ((pStringToCheck == null) || //
                (pStringToCheck.length() == 0) || //
                (-1 != pStringToCheck.indexOf( ' ' )));
    }

    public static boolean isBlank( String pLine ) {
        if ( pLine != null ) {
            for ( int at = pLine.length(); --at >= 0; ) {
                if ( pLine.charAt( at ) != ' ' ) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean areEqualIgnoreCase( String a, String b ) {
        // noinspection StringEquality
        return (a == b) || ((null != a) && a.equalsIgnoreCase( b ));
    }

    public static String assertNotEmptyToString( String pWhat, Object pToCheck ) {
        return assertNotEmpty( pWhat, (pToCheck != null) ? pToCheck.toString() : null );
    }

    public static String assertNotEmpty( String pWhat, String pToCheck ) {
        if ( null == (pToCheck = noEmpty( pToCheck )) ) {
            throw failNullOrEmpty( pWhat );
        }
        return pToCheck;
    }

    public static void assertEmpty( String pWhy, String pToCheck ) {
        if ( null != noEmpty( pToCheck ) ) {
            throw new IllegalArgumentException( pWhy );
        }
    }

    private static IllegalArgumentException failNullOrEmpty( String pWhat ) {
        return new IllegalArgumentException( pWhat + " Not allowed to be null or empty!" );
    }

    public static boolean hasNoSurroundingWhiteSpace( String pString ) {
        return (pString == null) || (pString.length() == pString.trim().length()); // No Leading or trailing White Space
    }

    public static void assertEndsWith( String pWhat, String pToCheck, String pForString ) {
        if ( !pToCheck.endsWith( pForString ) ) {
            throw new IllegalArgumentException( pWhat + " '" + pToCheck + "' did not end with '" + pForString + "'!" );
        }
    }

    public static String removeIfEndsWith( String pToCheck, String pForString ) {
        return pToCheck.endsWith( pForString ) ? pToCheck.substring( 0, pToCheck.length() - pForString.length() ) : pToCheck;
    }

    public static int findIn( String pToSearch, String pFirstToFind, String... pAdditionalToFind ) {
        Objects.assertNotNull( "ToSearch", pToSearch );
        if ( deNull( pFirstToFind ).length() == 0 ) {
            throw failNullOrEmpty( "FirstToFind" );
        }
        int zAt = pToSearch.indexOf( pFirstToFind );
        if ( (zAt == -1) && (pAdditionalToFind != null) ) {
            for ( int i = 0; i < pAdditionalToFind.length; i++ ) {
                String zToFind = deNull( pAdditionalToFind[i] );
                if ( zToFind.length() == 0 ) {
                    throw failNullOrEmpty( "AdditionalToFind[" + i + "]" );
                }
                if ( -1 != (zAt = pToSearch.indexOf( zToFind )) ) {
                    break;
                }
            }
        }
        return zAt;
    }

    public static String trimTrailing( String pLine ) {
        if ( (pLine != null) && (pLine.length() != 0) ) {
            if ( pLine.charAt( pLine.length() - 1 ) == ' ' ) {
                pLine = ("." + pLine).trim().substring( 1 );
            }
        }
        return pLine;
    }

    /**
     * Return if <tt>pThis</tt> starts with <tt>pStartsWith</tt> ignoring
     * the case of the strings
     * <p/>
     *
     * @param pThis       The String to check the front of
     * @param pStartsWith The String that <tt>pThis</tt>'s front must match
     *
     * @return <tt>true</tt> if <tt>pThis</tt> starts with <tt>pStartsWith</tt> ignoring
     * the case
     */
    public static boolean startsWithIgnoreCase( String pThis, String pStartsWith ) {
        if ( (pThis != null) && (pStartsWith != null) ) {
            int lenStartsWith = pStartsWith.length();
            if ( lenStartsWith <= pThis.length() ) {
                return pStartsWith.equalsIgnoreCase( pThis.substring( 0, lenStartsWith ) );
            }
        }
        return false;
    }

    public static String trimLeadingSpaces( String pStr ) {
        if ( (pStr == null) || !pStr.startsWith( " " ) ) {
            return pStr;
        }
        int sLen = pStr.length();
        for ( int i = 0; i < sLen; i++ ) {
            if ( pStr.charAt( i ) != ' ' ) {
                return pStr.substring( i );
            }
        }
        return "";
    }

    public static String trimTrailingSpaces( String pStr ) {
        if ( (pStr == null) || !pStr.endsWith( " " ) ) {
            return pStr;
        }
        int sLen = pStr.length();
        for ( int i = sLen - 1; --i >= 0; ) {
            if ( pStr.charAt( i ) != ' ' ) {
                return pStr.substring( 0, i + 1 );
            }
        }
        return "";
    }

    public static String join( Iterable<?> iterable, String joinString ) {
        Iterator<?> iter = iterable.iterator();
        if ( iter.hasNext() ) {
            StringBuilder buf = new StringBuilder( translateToString( iter.next() ) );
            while ( iter.hasNext() ) {
                buf.append( joinString );
                buf.append( translateToString( iter.next() ) );
            }
            return buf.toString();
        }
        return "";
    }

    public static void appendAsLines( StringBuilder pSB, String... pLines ) {
        if ( pLines != null ) {
            for ( String zLine : pLines ) {
                pSB.append( deNull( zLine ) ).append( NLchar );
            }
        }
    }

    public static String mergeLines( String... pLines ) {
        StringBuilder buf = new StringBuilder();
        if ( pLines != null ) {
            for ( String zLine : pLines ) {
                buf.append( zLine ).append( NLchar );
            }
        }
        return buf.toString();
    }

    private static String translateToString( Object next ) {
        return next == null ? "<NULL>" : next.toString();
    }

    private static String runningMaxIndentSpaces = "";

    public static synchronized String indent( int indent ) { // 4 spaces per...
        if ( indent < 1 ) {
            return "";
        }
        int spacesNeeded = indent + indent + indent + indent;
        if ( runningMaxIndentSpaces.length() < spacesNeeded ) {
            StringBuilder sb = new StringBuilder( spacesNeeded );
            for ( int i = spacesNeeded; i > 0; i-- ) {
                sb.append( ' ' );
            }
            return runningMaxIndentSpaces = sb.toString();
        }
        return runningMaxIndentSpaces.substring( 0, spacesNeeded );
    }

    public static String makeIdFriendly( String text ) {
        text = noEmpty( text );
        if ( text == null ) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean pendingUnderscore = false;
        for ( int i = 0; i < text.length(); i++ ) {
            char c = text.charAt( i );
            if ( !Characters.is7BitAlphaNumeric( c ) ) {
                pendingUnderscore = true;
            } else {
                if ( pendingUnderscore && (sb.length() != 0) ) {
                    sb.append( '_' );
                }
                pendingUnderscore = false;
                sb.append( c );
            }
        }
        return noEmpty( sb.toString() );
    }

    public static String assertOptionalIdentifier( String what, String toCheck ) {
        if ( null != (toCheck = noEmpty( toCheck )) ) {
            validateIdentifier( what, toCheck );
        }
        return toCheck;
    }

    public static String assertNotEmptyIdentifier( String what, String toCheck ) {
        if ( null != (toCheck = assertNotEmpty( what, toCheck )) ) {
            validateIdentifier( what, toCheck );
        }
        return toCheck;
    }

    private static void validateIdentifier( String what, String toCheck ) {
        int errorIndex = checkIdentifier( toCheck );
        if ( errorIndex != -1 ) {
            String zPrefix = (errorIndex == 0) ? "First Character" :
                             "Character (" + (errorIndex + 1) + ":'" + toCheck.charAt( errorIndex ) + "')";
            throw new IllegalArgumentException( zPrefix + " Unacceptable for Identifier " + what + ": '" + toCheck + "'" );
        }
    }

    /**
     * return the index of the first character that is unacceptable for that position to be part of an Identifier.
     * <p/>
     * An Identifier is a String that stars with a 7Bit Alpha or underscore, and is followed by any number of 7bit AlphaNumerics or underscores.
     *
     * @param toCheck not null or empty
     *
     * @return -1 if OK, otherwise the 'bad' character index.
     */
    public static int checkIdentifier( String toCheck ) {
        if ( !Characters.is7BitAlphaUnderScore( toCheck.charAt( 0 ) ) ) {
            return 0;
        }
        for ( int i = 1; i < toCheck.length(); i++ ) {
            if ( !Characters.isAlphaNumericUnderScore7BitAscii( toCheck.charAt( i ) ) ) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Wrap the text (into multiple lines as needed) such that no 'line' exceeds 'maxCharactersPerLine' in length.
     * <p/>
     * The wrapping algorithm is four fold (applied in the following order):
     * <li>1st New Line within the maxCharactersPerLine</li>
     * <li>last space within the maxCharactersPerLine</li>
     * <li>just beyond the last punctuation (non-AlphaNumeric character) within the 'maxCharactersPerLine'</li>
     * <li>hard wrapped such that the line length is 'within the maxCharactersPerLine'</li>
     *
     * @param text                 to Wrap.
     * @param maxCharactersPerLine Don't wrap if null  or < 1!
     *
     * @return !null result with wrapped text (lines) if maxCharactersPerLine > 0, (otherwise don't wrap)
     */
    public static String wrap( String text, Integer maxCharactersPerLine ) {
        text = deNull( text ).trim();
        if ( (maxCharactersPerLine == null) || (maxCharactersPerLine < 1) ) {
            return text;
        }
        StringBuilder sb = new StringBuilder();
        while ( maxCharactersPerLine < text.length() ) {
            int breakAt = findBreakAtPosition( text, maxCharactersPerLine );
            sb.append( text.substring( 0, breakAt ) ).append( NLchar );
            text = text.substring( breakAt ).trim();
        }
        return sb.append( text ).toString();
    }

    private static int findBreakAtPosition( String text, int maxCharactersPerLine ) {
        int at = text.indexOf( NLchar );
        if ( at != -1 && (at <= maxCharactersPerLine) ) {
            return at;
        }
        text = text.substring( 0, maxCharactersPerLine );
        at = text.lastIndexOf( ' ' );
        if ( at != -1 ) {
            return at;
        }
        for ( at = maxCharactersPerLine - 1; 0 <= at; at-- ) {
            if ( !Character.isLetterOrDigit( text.charAt( at ) ) ) {
                return at + 1;
            }
        }
        return maxCharactersPerLine;
    }

    public static int indexOfControlCharacter( String text ) {
        if ( text != null ) {
            for ( int i = 0; i < text.length(); i++ ) {
                if ( Characters.isControlChar( text.charAt( i ) ) ) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static String lowercaseFirstCharacter( String pString ) {
        if ( pString != null ) {
            if ( pString.length() != 0 ) {
                return pString.substring( 0, 1 ).toLowerCase() + pString.substring( 1 );
            }
        }
        return pString;
    }

    /**
     * Returns a substring of the specified text.
     *
     * @param pEnd The End index of the substring. If negative, the index used will be "text.length() + End".
     */
    public static String substring( String pText, int pStart, int pEnd ) {
        assertNotNullNotEmpty( "text", pText );
        Integers.assertNonNegative( "start", pStart );
        return pText.substring( pStart, (pEnd >= 0) ? pEnd : pText.length() + pEnd );
    }

    public static String padLeft( char pPadWith, String pString, int pToLength ) {
        if ( (pString != null) && (pToLength <= pString.length()) ) {
            return pString;
        }
        StringBuilder sb = new StringBuilder( deNull( pString ) );
        while ( sb.length() < pToLength ) {
            sb.insert( 0, pPadWith );
        }
        return sb.toString();
    }

    public static String padRight( String pString, char pPadWith, int pToLength ) {
        if ( (pString != null) && (pToLength <= pString.length()) ) {
            return pString;
        }
        StringBuilder sb = new StringBuilder( deNull( pString ) );
        while ( sb.length() < pToLength ) {
            sb.append( pPadWith );
        }
        return sb.toString();
    }

    public static String normalizeNewLines( String pText ) {
        return replace( replace( pText, "\r\n", "\n" ), "\r", "\n" );
    }

    public static String[] toLines( String pText ) {
        return parseChar( normalizeNewLines( pText ), NLchar );
    }

    public static String[] parseChar( String pStringToParse, char pSeparator ) {
        if ( isNullOrEmpty( pStringToParse ) ) {
            return new String[0];
        }
        int count = 1;
        int from = 0;
        for ( int at; -1 != (at = pStringToParse.indexOf( pSeparator, from )); from = at + 1 ) {
            count++;
        }
        String[] parts = new String[count];
        count = from = 0;
        for ( int at; -1 != (at = pStringToParse.indexOf( pSeparator, from )); from = at + 1 ) {
            parts[count++] = pStringToParse.substring( from, at );
        }
        parts[count] = pStringToParse.substring( from );
        return parts;
    }

    public static String combine( char pSeparator, String... pStrings ) {
        if ( Objects.isNullOrEmpty( pStrings ) ) {
            return null;
        }
        StringBuilder sb = new StringBuilder();

        sb.append( pStrings[0] );

        for ( int i = 1; i < pStrings.length; i++ ) {
            sb.append( pSeparator );
            sb.append( pStrings[i] );
        }
        return sb.toString();
    }

    public static String replace( String pSource, char pToFind, String pToReplaceWith ) {
        if ( (pSource != null) && (pToReplaceWith != null) ) {
            int removeFor = 1;
            int adjustFromBy = pToReplaceWith.length();
            int from = 0;
            for ( int at; -1 != (at = pSource.indexOf( pToFind, from )); from = at + adjustFromBy ) {
                pSource = pSource.substring( 0, at ) + pToReplaceWith + pSource.substring( at + removeFor );
            }
        }
        return pSource;
    }

    public static String replace( String pSource, String pToFind, String pToReplaceWith ) {
        if ( (pSource != null) && (pToFind != null) && (pToReplaceWith != null) ) {
            int removeFor = pToFind.length();
            int adjustFromBy = pToReplaceWith.length();
            int from = 0;
            for ( int at; -1 != (at = pSource.indexOf( pToFind, from )); from = at + adjustFromBy ) {
                pSource = pSource.substring( 0, at ) + pToReplaceWith + pSource.substring( at + removeFor );
            }
        }
        return pSource;
    }

    public static String defaultIfNull( String pTestString, String pDefault ) {
        return isNullOrEmpty( pTestString ) ? pDefault : pTestString;
    }

    public static String[] stringToLines( String pString ) {
        return parseChar( replace( normalizeNewLines( pString ), "\f", "\n" ), '\n' );
    }

    public static String linesToString( String[] pLines ) {
        StringBuilder sb = new StringBuilder();
        if ( pLines != null ) {
            for ( String zLine : pLines ) {
                sb.append( zLine );
                sb.append( '\n' );
            }
        }
        return sb.toString();
    }

    public static String makeNonBlankLines( String pSource, int pLinesToMake ) {
        pSource = normalizeNewLines( deNull( pSource ) );
        StringBuilder sb = new StringBuilder();
        for ( int i = 0; i < pLinesToMake; i++ ) {
            int at = (pSource += '\n').indexOf( '\n' );
            String line = pSource.substring( 0, at );
            pSource = pSource.substring( at + 1 );
            if ( sb.length() != 0 ) {
                sb.append( '\n' );
            }
            sb.append( line ).append( ' ' );
        }
        return sb.toString();
    }

    public static String nullOKtoString( Object value ) {
        return (value == null) ? null : value.toString();
    }

    public static String nullToEmptytoString( Object value ) {
        return (value == null) ? "" : value.toString();
    }

    public static void errorNullOrEmptyOrSpace( String pErrorMessage, String pForm )
            throws IllegalArgumentException {
        error( pForm, pErrorMessage, " not allowed to be null or empty or have any spaces" );
    }

    public static void errorNullOrEmpty( String pErrorMessage, String pForm )
            throws IllegalArgumentException {
        error( pForm, pErrorMessage, " not allowed to be null or empty!" );
    }

    public static void error( String pForm, String pErrorMessage, String pMessagePlus )
            throws IllegalArgumentException {
        if ( isNullOrEmpty( pErrorMessage ) ) {
            pErrorMessage = deNull( pForm );
        }
        if ( -1 != pErrorMessage.indexOf( ' ' ) ) {
            throw new IllegalArgumentException( pErrorMessage );
        }
        throw new IllegalArgumentException( pErrorMessage + pMessagePlus );
    }

    public static String padIt( int pMinDesiredLength, String pIt ) {
        String rv = deNull( pIt );
        int padBy = pMinDesiredLength - rv.length();
        return (padBy <= 0) ? rv : (spaces( padBy ) + rv);
    }

    public static String iTpad( String pIt, int pMinDesiredLength ) {
        String rv = deNull( pIt );
        int padBy = pMinDesiredLength - rv.length();
        return (padBy <= 0) ? rv : (rv + spaces( padBy ));
    }

    public static String noSpaces( String pSource ) {
        if ( (pSource == null) || (pSource.length() == 0) || (pSource.indexOf( ' ' ) == -1) ) {
            return pSource;
        }
        StringBuilder sb = new StringBuilder( pSource );
        for ( int i = pSource.length(); --i >= 0; ) {
            if ( sb.charAt( i ) == ' ' ) {
                sb.deleteCharAt( i );
            }
        }
        return sb.toString();
    }

    public static boolean containsAnyOf( String pToCheck, String pCharsToCheckFor ) {
        if ( (pToCheck != null) && (pToCheck.length() > 0) && //
             (pCharsToCheckFor != null) && (pCharsToCheckFor.length() > 0) ) {
            for ( int i = 0; i < pCharsToCheckFor.length(); i++ ) {
                if ( -1 != pToCheck.indexOf( pCharsToCheckFor.charAt( i ) ) ) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getCommonTail( String pStr1, String pStr2 ) {
        // Binary Search
        int goodTailLength = 0;
        for ( int potentialLength = Math.min( pStr1.length(), pStr2.length() ); potentialLength > goodTailLength; ) {
            int mid = (goodTailLength + potentialLength + 1) / 2;
            String tail = pStr1.substring( pStr1.length() - mid );
            if ( pStr2.endsWith( tail ) ) {
                goodTailLength = mid;
            } else {
                potentialLength = mid - 1;
            }
        }
        return (goodTailLength == 0) ? "" : pStr1.substring( pStr1.length() - goodTailLength );
    }

    public static int indexOfOrLength( String pToSearch, char pToFind ) {
        return helperIndexOfOrLength( pToSearch, pToSearch.indexOf( pToFind ) );
    }

    public static int indexOfOrLength( String pToSearch, String pToFind ) {
        return helperIndexOfOrLength( pToSearch, pToSearch.indexOf( pToFind ) );
    }

    private static int helperIndexOfOrLength( String pToSearch, int pAt ) {
        return (pAt != -1) ? pAt : pToSearch.length();
    }

    public static String[] removeStringFromArray( String[] pArray, int pIndexToRemove ) {
        if ( Objects.isNullOrEmpty( pArray ) || (pIndexToRemove < 0) || (pArray.length <= pIndexToRemove) ) {
            return pArray;
        }
        int zNewLength = pArray.length - 1;
        String[] zNewArray = new String[zNewLength];
        if ( pIndexToRemove != 0 ) {
            System.arraycopy( pArray, 0, zNewArray, 0, pIndexToRemove );
        }
        if ( pIndexToRemove != zNewLength ) {
            System.arraycopy( pArray, pIndexToRemove + 1, zNewArray, pIndexToRemove, zNewLength - pIndexToRemove );
        }
        return zNewArray;
    }

    public static String[] appendStringArrays( String[] pArray1, String[] pArray2 ) {
        if ( Objects.isNullOrEmpty( pArray2 ) ) {
            return pArray1;
        }
        if ( Objects.isNullOrEmpty( pArray1 ) ) {
            return pArray2;
        }
        String[] joined = new String[pArray1.length + pArray2.length];
        System.arraycopy( pArray1, 0, joined, 0, pArray1.length );
        System.arraycopy( pArray2, 0, joined, pArray1.length, pArray2.length );
        return joined;
    }

    public static String[] prependString( String pNewFirst, String[] pTheRest ) {
        return appendStringArrays( new String[]{pNewFirst}, pTheRest );
    }

    public static String[] appendString( String[] pCurArray, String pNewLast ) {
        return appendStringArrays( pCurArray, new String[]{pNewLast} );
    }

    /**
     * Is the string 'pInQuestion' made up of the 'pParts' where if only 1, then must be equal, otherwise
     * the pInQuestion.startsWith(first) && pInQuestion.endsWith(last) && the middle must be found in
     * order with no overlap between.
     *
     * @param pInQuestion - null will always return false
     * @param pParts      - Null treated as empty & Null Elements treated as ""
     */
    public static boolean isMadeUpFromParts( String pInQuestion, String[] pParts ) {
        StringMatcher zSM = StringMatcherFactory.createEquals( pParts );
        return (zSM != null) && zSM.matches( pInQuestion );
    }

    public static String[] removeAllBlankOrCommentLines( String[] pLines ) {
        List<String> lines = Lists.newLinkedList();

        for ( String line : deNull( pLines ) ) {
            String s = line.trim();
            if ( (s.length() != 0) && !(s.startsWith( "#" ) || s.startsWith( "//" )) ) {
                lines.add( line );
            }
        }
        return lines.toArray( new String[lines.size()] );
    }

    public static String merge( String[] pLines ) {
        if ( (pLines == null) || (pLines.length == 0) ) {
            return "";
        }
        if ( pLines.length == 1 ) {
            return pLines[0];
        }
        int length = 0;
        for ( String line : pLines ) {
            if ( line != null ) {
                length += line.length() + 2;
            }
        }
        StringBuilder sb = new StringBuilder( length );
        for ( String line : pLines ) {
            if ( line != null ) {
                sb.append( line ).append( '\r' ).append( '\n' );
            }
        }
        return sb.toString();
    }

    public static boolean isAllUppercaseOrSpaces( String pToTest ) {
        if ( isNotNullOrEmpty( pToTest ) ) {
            for ( int i = 0; i < pToTest.length(); i++ ) {
                char c = pToTest.charAt( i );
                if ( (c != ' ') && !Character.isUpperCase( c ) ) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isAllUppercase( String pToTest ) {
        if ( isNotNullOrEmpty( pToTest ) ) {
            for ( int i = 0; i < pToTest.length(); i++ ) {
                if ( !Character.isUpperCase( pToTest.charAt( i ) ) ) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String appendNonEmpties( String pExisting, String pSeparator, String pToAppend ) {
        if ( isNullOrEmpty( pToAppend ) ) {
            return deNull( pExisting ).trim();
        }
        if ( isNullOrEmpty( pExisting ) ) {
            return pToAppend.trim();
        }
        return pExisting.trim() + deNull( pSeparator ) + pToAppend.trim();
    }

    public static String trimToLength( String pSource, int pMaxAcceptableLength )
            throws IllegalArgumentException {
        pMaxAcceptableLength = Integers.assertNonNegative( "MaxAcceptableLength", pMaxAcceptableLength );
        return ((pSource = deNull( pSource )).length() <= pMaxAcceptableLength) ? pSource : pSource.substring( 0, pMaxAcceptableLength );
    }

    public static String trimToLength( String pSource, int pMaxAcceptableLength, String pTrimmedSuffix )
            throws IllegalArgumentException {
        pMaxAcceptableLength = Integers.assertNonNegative( "MaxAcceptableLength", pMaxAcceptableLength );
        if ( (pSource = deNull( pSource )).length() <= pMaxAcceptableLength ) {
            return pSource;
        }
        int zSuffixLength = (pTrimmedSuffix = deNull( pTrimmedSuffix )).length();
        if ( zSuffixLength == 0 ) {
            return pSource.substring( 0, pMaxAcceptableLength );
        }
        if ( pMaxAcceptableLength < zSuffixLength ) {
            pTrimmedSuffix = pTrimmedSuffix.substring( 0, zSuffixLength = pMaxAcceptableLength );
        }
        pMaxAcceptableLength -= zSuffixLength;
        return pSource.substring( 0, pMaxAcceptableLength ) + pTrimmedSuffix;
    }

    public static String combineAsLines( String... pStrings ) {
        StringBuilder sb = new StringBuilder();
        if ( pStrings != null ) {
            for ( String s : pStrings ) {
                sb.append( s );
                sb.append( '\n' );
            }
        }
        return sb.toString();
    }

    public static String[] splitLines( String pString ) {
        pString = normalizeNewLines( pString );

        return parseChar( pString, '\n' );
    }

    public static void assertAll7BitAsciiAlpha( String pObjectName, String pToBeAssert )
            throws IllegalArgumentException {
        Objects.assertNotNull( pObjectName, pToBeAssert );
        for ( int i = 0; i < pToBeAssert.length(); i++ ) {
            char c = pToBeAssert.charAt( i );
            if ( !(Characters.is7BitAsciiAlpha( c )) ) {
                throw new IllegalArgumentException( pObjectName + ": '" + c + "' Not a 7 Bit Ascii Alpha at: " + i );
            }
        }
    }

    public static void assertNotEmptyIfNotNull( String pParamName, String pToBeAsserted )
            throws IllegalArgumentException {
        if ( (pToBeAsserted != null) && (pToBeAsserted.length() == 0) ) {
            throw new IllegalArgumentException( pParamName + ": Not allowed to be empty ('')" );
        }
    }

    public static void assertNotNullNotEmptyNoSpaces( String pErrorMessage, String pStringToAssert )
            throws IllegalArgumentException {
        if ( isNullOrEmptyOrSpaces( pStringToAssert ) ) {
            errorNullOrEmptyOrSpace( pErrorMessage, "String" );
        }
    }

    public static String assertNotNullNotEmpty( String pErrorMessage, String pStringToAssert )
            throws IllegalArgumentException {
        String rv = noEmpty( pStringToAssert );
        if ( rv == null ) {
            errorNullOrEmpty( pErrorMessage, "String" );
        }
        return rv;
    }

    public static void assertNullOrEmpty( String pErrorMessage, String pStringToAssert ) {
        if ( !isNullOrEmpty( pStringToAssert ) ) {
            error( "String", pErrorMessage, " must be 'empty' (null, empty, or nothing but whitespace" );
        }
    }

    public static String assertNoLeadingOrTrailingWhiteSpace( String pErrorMessage, String pStringToAssert )
            throws IllegalArgumentException {
        if ( pStringToAssert != null ) {
            String rv = pStringToAssert.trim();
            if ( pStringToAssert.length() != rv.length() ) {
                error( "String", pErrorMessage, " not allowed to have leading or trailing whitespace" );
            }
        }
        return pStringToAssert;
    }

    public static String[] assertNotNullNotEmptyAndNoNullsOrEmptiesAndTrim( String pErrorMessage, String[] pStringArrayToAssert )
            throws IllegalArgumentException {
        assertNotNullNotEmpty( pErrorMessage, pStringArrayToAssert );
        return assertNoNullsOrEmptiesAndTrim( pErrorMessage, pStringArrayToAssert );
    }

    public static void assertNotNullNotEmpty( String pErrorMessage, String[] pStringArrayToAssert )
            throws IllegalArgumentException {
        if ( Objects.isNullOrEmpty( pStringArrayToAssert ) ) {
            errorNullOrEmpty( pErrorMessage, "String[]" );
        }
    }

    public static String[] assertNoNullsOrEmptiesAndTrim( String pErrorMessage, String[] pStringArrayToAssert )
            throws IllegalArgumentException {
        String[] rv = new String[pStringArrayToAssert.length];
        for ( int i = 0; i < pStringArrayToAssert.length; i++ ) {
            String s = noEmpty( pStringArrayToAssert[i] );
            if ( s == null ) {
                errorNullOrEmpty( pErrorMessage + "[" + i + "]", "String[]" );
            }
            rv[i] = s;
        }
        return rv;
    }

    public static String multiLineHelper( String pLine, String pAppend ) {
        return isNullOrEmpty( pLine ) ? "" : (pLine + pAppend);
    }

    public static String[] toArray( String pString ) {
        return new String[]{pString};
    }

    public static String[] toArray( Collection pCollection ) {
        return (pCollection == null) ? null : toArray( pCollection.toArray() );
    }

    public static String[] toArray( Object[] pObjects ) {
        if ( pObjects == null ) {
            return null;
        }
        String[] zStrings = new String[pObjects.length];
        for ( int i = 0; i < pObjects.length; i++ ) {
            Object o = pObjects[i];
            zStrings[i] = (o == null) ? null : o.toString();
        }
        return zStrings;
    }

    public static List<String> toList( Collection pCollection ) {
        return (pCollection == null) ? null : toList( pCollection.toArray() );
    }

    public static List<String> toList( Object[] pObjects ) {
        if ( pObjects == null ) {
            return null;
        }
        List<String> strings = Lists.newArrayList( pObjects.length );
        for ( Object o : pObjects ) {
            strings.add( (o == null) ? null : o.toString() );
        }
        return strings;
    }

    public static boolean isAll7BitAsciiAlpha( String pString, int pMinLength ) {
        if ( (pString == null) || (pString.length() < pMinLength) ) {
            return false;
        }
        for ( int i = 0; i < pString.length(); i++ ) {
            if ( !Characters.is7BitAsciiAlpha( pString.charAt( i ) ) ) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAlphaNumeric( String pString, int pMinLength ) {
        if ( (pString == null) || (pString.length() < pMinLength) ) {
            return false;
        }
        for ( int i = 0; i < pString.length(); i++ ) {
            if ( !Characters.isAlphaNumeric( pString.charAt( i ) ) ) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNumeric( String pString, int pMinLength ) {
        if ( (pString == null) || (pString.length() < pMinLength) ) {
            return false;
        }
        for ( int i = 0; i < pString.length(); i++ ) {
            if ( !Characters.isNumeric( pString.charAt( i ) ) ) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidAttributeIdentifier( String pIdentifier ) {
        return isValidAttributeIdentifier( pIdentifier, 2 );
    }

    public static boolean isValidAttributeIdentifier( String pIdentifier, int pMinLength ) {
        if ( (pIdentifier == null) || (pIdentifier.length() < pMinLength) ) {
            return false;
        }

        if ( !Characters.isValidAttributeIdentifierStartCharacter( pIdentifier.charAt( 0 ) ) ) {
            return false;
        }
        for ( int i = 1; i < pIdentifier.length(); i++ ) {
            if ( !Characters.isValidAttributeIdentifierRestCharacter( pIdentifier.charAt( i ) ) ) {
                return false;
            }
        }
        return true;
    }

    public static String[] parseOptions( String pOptionsAsString )
            throws IllegalArgumentException {
        if ( null == (pOptionsAsString = noEmpty( pOptionsAsString )) ) {
            return null;
        }
        char sep = pOptionsAsString.charAt( 0 );
        return Character.isLetterOrDigit( sep ) ? //
               parseOptions( pOptionsAsString, ',' ) : //
               parseOptions( pOptionsAsString.substring( 1 ).trim(), sep );
    }

    private static String[] parseOptions( String pStringToParse, char pSeparator )
            throws IllegalArgumentException {
        List<String> zParts = Lists.newArrayList();
        int from = 0;
        for ( int at; -1 != (at = pStringToParse.indexOf( pSeparator, from )); from = at + 1 ) {
            addNotEmpty( zParts, pStringToParse.substring( from, at ) );
        }
        addNotEmpty( zParts, pStringToParse.substring( from ) );
        if ( zParts.size() < 2 ) {
            throw new IllegalArgumentException( "Not TWO options, given: " + pStringToParse );
        }
        return toArray( zParts );
    }

    private static void addNotEmpty( List<String> pParts, String pPart ) {
        if ( (pPart = pPart.trim()).length() != 0 ) {
            pParts.add( pPart );
        }
    }

    public static String combineAsLines( List<String> pStrings ) {
        return combineAsLines( (pStrings == null) ? null : pStrings.toArray( new String[pStrings.size()] ) );
    }

    public static boolean isAsciiIdentifier( String pToTest ) {
        if ( !isNullOrEmpty( pToTest ) ) {
            if ( Characters.isFirstCharAsciiIdentifier( pToTest.charAt( 0 ) ) ) {
                for ( int i = 1; i < pToTest.length(); i++ ) {
                    if ( !Characters.isNonFirstCharAsciiIdentifier( pToTest.charAt( i ) ) ) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isNoSpaceAscii( String pToTest ) {
        if ( !isNullOrEmpty( pToTest ) ) {
            for ( int i = 0; i < pToTest.length(); i++ ) {
                if ( !Characters.isNoSpaceAscii( pToTest.charAt( i ) ) ) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isNoCtrlAscii( String pToTest ) {
        if ( pToTest != null ) {
            for ( int i = 0; i < pToTest.length(); i++ ) {
                if ( !Characters.isNoCtrlAscii( pToTest.charAt( i ) ) ) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean isOneOf( String pToFind, String[] pToSearch ) {
        return Objects.isOneOf( pToFind, pToSearch );
    }

    public static String combine( String pSeparator, List<String> pStrings ) {
        return Objects.combine( pSeparator, pStrings );
    }

    public static String normalizeCtrlChars( String pText ) {
        if ( pText != null ) {
            for ( int i = pText.length(); i-- > 0; ) {
                if ( pText.charAt( i ) < ' ' ) {
                    StringBuilder sb = new StringBuilder( pText );
                    for (; i >= 0; i-- ) {
                        char c = sb.charAt( i );
                        if ( c < ' ' ) {
                            switch ( c ) {
                                case '\n':
                                case '\r':
                                case '\f':
                                    break;
                                case '\t':
                                    sb.setCharAt( i, ' ' );
                                    break;
                                default:
                                    sb.setCharAt( i, '.' );
                                    break;
                            }
                        }
                        pText = sb.toString();
                    }
                    break;
                }
            }
        }
        return pText;
    }

    public static String cvtTextForDisplay( String pText ) {
        if ( pText == null ) {
            return "[null]";
        }
        int length = pText.length();
        StringBuilder sb = new StringBuilder( length );
        for ( int i = 0; i < length; i++ ) {
            char c = pText.charAt( i );
            switch ( c ) {
                case '[':
                case ']':
                case '\\':
                case '"':
                    sb.append( '\\' );
                    sb.append( c );
                    break;
                case '\n':
                    sb.append( "\\n" );
                    break;
                case '\r':
                    sb.append( "\\r" );
                    break;
                case '\t':
                    sb.append( "\\t" );
                    break;
                case '\f':
                    sb.append( "\\f" );
                    break;
                default:
                    if ( (' ' <= c) && (c < 127) ) {
                        sb.append( c );
                        break;
                    }
                    sb.append( '[' );
                    sb.append( Characters.cvtCharForDisplay( c ) );
                    sb.append( ']' );
                    break;
            }
        }
        return sb.toString();
    }
}
