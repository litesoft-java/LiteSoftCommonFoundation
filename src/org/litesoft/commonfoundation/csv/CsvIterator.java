// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.csv;

import org.litesoft.commonfoundation.interators.*;

/**
 * An Iterator of the values in a String encoded as Comma Separated Values.<p>
 * <p/>
 * For the rules to parsing CSV, see  CsvSupport
 *
 * @author George Smith
 * @version 1.0 7/28/01
 * @see CsvSupport
 */

public final class CsvIterator extends ArrayIterator<String>
{
    /**
     * Construct an Iterator of the values in a String encoded as Comma Separated Values.<p>
     *
     * @param pSource the String to CSV decode (null OK).
     */
    public CsvIterator( String pSource )
    {
        super( "CsvIterator", (new CsvSupport()).decode( pSource ) );
    }
}
