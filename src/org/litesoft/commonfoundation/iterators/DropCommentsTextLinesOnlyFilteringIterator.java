// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.iterators;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

/**
 * An Iterator that filters another Iterator by only returning Strings
 * and only if the Strings are:
 * not null, not of zero length after trim(), and do not start with
 * the CommentPrefix (again after trimming).<p>
 * <p/>
 * Optionally it can be specified that inline comments can be dropped.
 *
 * @author George Smith
 * @version 1.0 7/28/01
 */
public final class DropCommentsTextLinesOnlyFilteringIterator extends Iterators.AbstractFiltering<String> implements DescriptiveIterator<String> {
    public static final String HASH_COMMENT_PREFIX = "#";
    public static final String SLASH_SLASH_COMMENT_PREFIX = "//";

    private boolean mRemoveInline;

    /**
     * Construct an Iterator that filters another Iterator by only returning
     * the appropriate Strings.<p>
     *
     * @param pIterator the filtered Iterator.
     */
    public DropCommentsTextLinesOnlyFilteringIterator( DescriptiveIterator<String> pIterator, String... pCommentPrefixes ) {
        super( pIterator );
        for ( String zPrefix : ConstrainTo.notNullImmutableList( pCommentPrefixes ) ) {
            if ( null != (zPrefix = ConstrainTo.significantOrNull( zPrefix )) ) {
                mCommentPrefixes.add( zPrefix );
            }
        }
        if ( mCommentPrefixes.isEmpty() ) {
            mCommentPrefixes.add( HASH_COMMENT_PREFIX );
        }
    }

    public DropCommentsTextLinesOnlyFilteringIterator dropInline() {
        mRemoveInline = true;
        return this;
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
    protected boolean keepThis( String pPossibleValue ) {
        if ( (pPossibleValue == null) || (pPossibleValue = pPossibleValue.trim()).isEmpty() ) { // weed out null
            return false;
        }
        for ( String zPrefix : mCommentPrefixes ) {
            if ( pPossibleValue.startsWith( zPrefix ) ) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected String filter( String pValue ) {
        if ( mRemoveInline ) {
            for ( String zPrefix : mCommentPrefixes ) {
                int zAt = pValue.indexOf( zPrefix );
                if ( zAt != -1 ) {
                    pValue = pValue.substring( 0, zAt ).trim();
                }
            }
        }
        return super.filter( pValue );
    }

    private final Set<String> mCommentPrefixes = Sets.newHashSet();

    @Override
    public String toString() {
        return mIterator.toString();
    }
}
