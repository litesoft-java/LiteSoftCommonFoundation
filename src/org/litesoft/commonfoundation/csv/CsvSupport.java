// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.csv;

import org.litesoft.commonfoundation.exceptions.*;

import java.util.*;

/**
 * CSV == Comma Separated Value<p>
 * <p/>
 * A CSV line consists of one or more fields seperated by a comma.
 * Each field does NOT include any leading and trailing spaces.
 * To include a comma or leading or trailing spaces the field MUST
 * be wrapped in double quotes (eg "field").<p>
 * <p/>
 * To place a double quote within a double quoted field the double
 * quote must be doubled up (eg the proper way to format   start,"end
 * is "start,""end").<p>
 *
 * @author George Smith
 * @version 1.0 7/28/01
 */

public class CsvSupport
{
    /**
     * Constructor primarily used with decode, and encode with NO support
     * for null substitution.<p>
     * <p/>
     * Because this constructor does NOT provide any null substitution
     * String(s), any null in the encode array will cause an
     * IllegalArgumentException to be thrown (see encode).<p>
     *
     * @see #CsvSupport(String)
     * @see #encode(String[])
     * @see #decode(String)
     */
    public CsvSupport()
    {
        this( null );
    }

    /**
     * Constructor primarily used with encode with common String
     * for null substitution.<p>
     * <p/>
     * Any null in the encode array is substituted with the parameter.
     * Note: Should the substitution String be null, and it is substituted,
     * then an IllegalArgumentException is thrown (see encode).<p>
     *
     * @param pForNulls common null substitution String (null OK).<p>
     *
     * @see #CsvSupport(String, String)
     * @see #encode(String[])
     * @see #decode(String)
     */
    public CsvSupport( String pForNulls )
    {
        this( pForNulls, pForNulls );
    }

    /**
     * Constructor primarily used with encode with different Strings
     * for null substitution.<p>
     * <p/>
     * Nulls on the end of the encode array is substituted with the
     * <i>pForEndNulls</i> parameter, AND if this parameter is itself
     * null, then the array is effectively shortened (possibly resulting
     * in an empty array).<p>
     * <p/>
     * All other nulls in the encode array are substituted with the
     * <i>pForMidNulls</i> parameter.  Note: Should the substitution
     * String itself be null, then an IllegalArgumentException is
     * thrown (see encode).<p>
     *
     * @param pForMidNulls null substitution String for nulls NOT on the
     *                     <i>end</i> of the encode array (null OK).
     * @param pForEndNulls null substitution String for nulls on the
     *                     <i>end</i> of the encode array (null OK).<p>
     *
     * @see #CsvSupport(String, String)
     * @see #encode(String[])
     * @see #decode(String)
     */
    public CsvSupport( String pForMidNulls, String pForEndNulls )
    {
        zForMidNulls = pForMidNulls;
        zForEndNulls = pForEndNulls;
    }

    private String zForMidNulls;
    private String zForEndNulls;

    private String[] replaceNulls( String[] source )
    {
        int i, sLen = source.length;

        if ( zForEndNulls == null )
        {
            for ( i = sLen - 1; (i >= 0) && (source[i] == null); i-- )
            {
                sLen--;
            }
        }

        String[] newArray = new String[sLen];

        for ( i = sLen - 1; (i >= 0) && (source[i] == null); i-- )
        {
            newArray[i] = zForEndNulls;
        }

        for (; i >= 0; i-- )
        {
            if ( null == (newArray[i] = (source[i] == null) ? zForMidNulls : source[i]) )
            {
                throw new IllegalArgumentException( "null unacceptable in element: " + i );
            }
        }

        return newArray;
    }

    private String[] deNull( String[] source )
    {
        if ( source != null )
        {
            for ( int i = source.length; i-- > 0; )
            {
                if ( source[i] == null )
                {
                    return replaceNulls( source );
                }
            }
        }

        return source;
    }

    private int sumLengths( String[] source )
    {
        int retval = 0;
        for ( int i = source.length; i-- > 0; )
        {
            retval += source[i].length();
        }
        return retval;
    }

    /**
     * Encode a String Array into a CSV line/String.<p>
     * <p/>
     * To support orthogonality with decode an empty array returns a null.<p>
     * <p/>
     * Nulls in the array are replaced by either <i>mid</i> or <i>end</i>
     * null substitution strings.  If the <i>end null substitution string</i>
     * is itself a null, then the array is effectively shortened until no nulls
     * remain on the end of the array (note: this can result in an empty array).
     * If a non-end null is substituted by the <i>mid null substitution string</i>
     * but is still null, then an IllegalArgumentException is thrown.<p>
     *
     * @param pSource array of <i>fields</i> (!null).<p>
     *
     * @return a CSV encoded string of the source fields.<p>
     *
     * @throws NullPointerException     if the passed in array is null.
     * @throws IllegalArgumentException if a null is attempted to be inserted in the ouput String (after substitution).<p>
     * @see #decode(String)
     * @see #CsvSupport(String)
     * @see #CsvSupport(String, String)
     */
    public String encode( String[] pSource )
            throws NullPointerException, IllegalArgumentException
    {
        pSource = deNull( pSource ); // might produce a new Array

        int sLen = pSource.length;  // can throw NullPointerException

        if ( sLen == 0 )
        {
            return null;
        }

        StringBuilder sb = new StringBuilder( sLen + sumLengths( pSource ) );
        appendCsvField( pSource[0], sb );

        for ( int i = 1; i < sLen; i++ )
        {
            sb.append( ',' );
            appendCsvField( pSource[i], sb );
        }

        return sb.toString();
    }

    /**
     * Decode a CSV line/String of fields into an Array.<p>
     * <p/>
     * To support orthogonality with encode a null returns an empty array.<p>
     * <p/>
     * Since a CSV line consists of one or more fields, an array is always
     * returned (eg "" would return an array with one entry of "").  However,
     * if the source parameter is null, then an empty array is returned.<p>
     *
     * @param pSource CSV line of <i>fields</i> (null OK).<p>
     *
     * @return an array of decoded CSV string fields.<p>
     *
     * @throws UnclosedQuoteException        (subclass of QuoteException subclass of
     *                                       IllegalArgumentException if an
     *                                       appearently quoted field is
     *                                       does not appear to have the closing quote.<p>
     * @throws MalformedQuotedFieldException (subclass of QuoteException subclass of
     *                                       IllegalArgumentException if an
     *                                       appearently quoted field is
     *                                       encounters the 'closing' quote, but there
     *                                       is more following it.<p>
     * @see #encode(String[])
     */
    public String[] decode( String pSource )
            throws IllegalArgumentException
    {
        List<String> list = new ArrayList<String>();

        while ( pSource != null )
        {
            pSource = extractAndAddCsvField( pSource.trim(), list );
        }

        return list.toArray( new String[list.size()] );
    }

    private String extractAndAddCsvField( String source, List<String> list )
    {
        if ( (source.length() > 0) && (source.charAt( 0 ) == '"') )
        {
            return extractAndAddCsvQuotedField( source, list );
        }

        // Handle Not Quoted
        int comma = source.indexOf( ',' );
        if ( comma == -1 ) // No Comma, then must be last field
        {
            // *** The use of new String() is to Force a local underlying char buffer
            //noinspection RedundantStringConstructorCall
            list.add( new String( source ) ); // *** Already trimmed
            return null;
        }
        // *** The use of new String() is to Force a local underlying char buffer
        //noinspection RedundantStringConstructorCall
        list.add( new String( source.substring( 0, comma ).trim() ) ); // *** Possible spaces before the comma
        return source.substring( comma + 1 );
    }

    private String extractAndAddCsvQuotedField( String source, List<String> list )
    {
        String retval, field;

        int comma = findCommaAfterQuoted( source );
        if ( comma == -1 ) // No Comma, then must be last field
        {
            field = source;
            retval = null;
        }
        else
        {
            field = source.substring( 0, comma );
            retval = source.substring( comma + 1 );
        }
        field = removeWrappingQuotes( field );
        field = removeDoubleQuotes( field );

        // *** The use of new String() is to Force a local underlying char buffer
        //noinspection RedundantStringConstructorCall
        list.add( new String( field ) );
        return retval;
    }

    private int findCommaAfterQuoted( String source )
    {
        int sLen = source.length();
        int startIndex = 0;
        do
        {
            int quote = source.indexOf( '"', ++startIndex );
            if ( quote == -1 )
            {
                throw new UnclosedQuoteException( "Apparently Quoted Field no closing quote, starting at: " + source );
            }

            startIndex = quote + 1;
        }
        while ( (startIndex < sLen) && ('"' == source.charAt( startIndex )) );

        return source.indexOf( ',', startIndex );  // Find comma AFTER closing quote
    }

    private String removeDoubleQuotes( String field )
    {
        int startIndex = 0;
        for ( int doubleQuote; 0 != (doubleQuote = field.indexOf( "\"\"", startIndex ) + 1); startIndex = doubleQuote )
        {
            field = field.substring( 0, doubleQuote ) + field.substring( doubleQuote + 1 );
        }

        return field;
    }

    private String removeWrappingQuotes( String field )
    {
        int sLast = (field = field.trim()).length() - 1;
        if ( field.charAt( sLast ) != '"' )
        {
            throw new MalformedQuotedFieldException( "Appearently Quoted Field, but something after closing quote, in field: " + field );
        }

        return field.substring( 1, sLast );
    }

    private void appendCsvField( String field, StringBuilder sb )
    {
        if ( field.length() == 0 )
        {
            return;
        }

        int quote = field.indexOf( '"' );

        if ( (quote == -1) && !field.startsWith( " " ) && !field.endsWith( " " ) && (-1 == field.indexOf( ',' )) )
        {
            sb.append( field );
            return;
        }
        // quote it!
        int from = 0;
        sb.append( '"' );
        for (; quote != -1; quote = field.indexOf( '"', from + 1 ) ) // + 1 to skip the '"' at from
        {
            sb.append( field.substring( from, quote + 1 ) ); // part thru '"'
            from = quote;  // will cause the next part to include the '"'
        }
        sb.append( field.substring( from ) ); // remainder
        sb.append( '"' );
    }
}
