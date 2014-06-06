// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.iterators;

import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

/**
 * An Iterator that filters another Iterator by only returning Strings
 * and only if the Strings are:
 * not null, not of zero length after trim(), and do not start with
 * the CommentPrefix (again after trimming).<p>
 *
 * @author George Smith
 * @version 1.0 7/28/01
 */

public final class DropCommentsTextLinesOnlyFilteringIterator extends Iterators.AbstractFiltering<String>
{
    public static final String HASH_COMMENT_PREFIX = "#";
    public static final String SLASH_SLASH_COMMENT_PREFIX = "//";

    /**
     * Construct an Iterator that filters another Iterator by only returning
     * the appropriate Strings.<p>
     *
     * @param pIterator the filtered Iterator.
     */
    public DropCommentsTextLinesOnlyFilteringIterator( Iterator<String> pIterator, String... pCommentPrefixes )
    {
        super( pIterator );
        for ( String zPrefix : Strings.deNull( pCommentPrefixes ) )
        {
            if ( null != (zPrefix = Strings.noEmpty( zPrefix )) )
            {
                mCommentPrefixes.add( zPrefix );
            }
        }
        if ( mCommentPrefixes.isEmpty() )
        {
            mCommentPrefixes.add( HASH_COMMENT_PREFIX );
        }
    }

    /**
     * Determines if the next item in the stream being filtered is to be kept
     * (passed out as an acceptable item) or tossed.  The kept items:
     * must be Strings and only if the Strings are:
     * not null, not of zero length after trim(), and do not start with
     * "//" (again after trimming).<p>
     *
     * @param pPossibleValue the next item to check from the stream being filtered (null OK).
     *
     * @return <tt>true</tt>/<tt>false</tt>, if the next item (pPossibleValue)
     * should be kept.<p>
     */
    @Override
    protected boolean keepThis( String pPossibleValue )
    {
        if ( (pPossibleValue == null) || (pPossibleValue = pPossibleValue.trim()).isEmpty() ) // weed out null
        {
            return false;
        }
        for ( String zPrefix : mCommentPrefixes )
        {
            if ( pPossibleValue.startsWith( zPrefix ) )
            {
                return false;
            }
        }
        return true;
    }

    private final Set<String> mCommentPrefixes = Sets.newHashSet();
}
