package org.litesoft.commonfoundation.issue;

import org.litesoft.commonfoundation.base.*;

public class SourcedContent implements SourceAccessor {
    private final Source mSource;
    private final String mContent;

    public SourcedContent( Source pSource, String pContent ) {
        mSource = Confirm.isNotNull( "Source", pSource );
        mContent = ConstrainTo.notNull( pContent );
    }

    public SourcedContent using( IssueSink pIssueSink ) {
        Source zSource = mSource.using( pIssueSink );
        return (mSource == zSource) ? this : new SourcedContent( zSource, mContent );
    }

    public boolean isSignificant() {
        return Currently.significant( mContent );
    }

    @Override
    public Source getSource() {
        return mSource;
    }

    public String getErrorPrefix() {
        return mSource.toString( " in" );
    }

    public String getContent() {
        return mContent;
    }

    public int indexOf( String pToFind ) {
        return indexOf( pToFind, 0 );
    }

    public int indexOf( String pToFind, int pFrom ) {
        return ((pToFind != null) && (pToFind.length() != 0)) ? mContent.indexOf( pToFind, pFrom ) : mContent.length();
    }

    public SourcedContent deeper( String pLevel, String pNewContent ) {
        return new SourcedContent( mSource.plus( pLevel ), pNewContent );
    }

    public SourcedContent deeper( String pLevel, int pFrom, int pUpto ) {
        return deeper( pLevel, mContent.substring( pFrom, pUpto ) );
    }

    public SourcedContent deeper( String pLevel, String pFrom, String pUpto, String... pToRemoveNonCollapsing ) {
        int zFromAt = indexOf( pFrom );
        if ( zFromAt == -1 ) {
            throw exception( "Unable to locate " + pLevel + " w/ Upper Boundary '" + pFrom + "'" );
        }
        int zUptoAt = (pUpto != null) ? indexOf( pUpto, zFromAt + 1 ) : mContent.length();
        if ( zUptoAt == -1 ) {
            throw exception( "Unable to locate " + pLevel + " w/ Boundaries '" + pFrom + "' and '" + pUpto + "'" );
        }
        return deeper( pLevel, zFromAt + pFrom.length(), zUptoAt ).removeNonCollapsing( pToRemoveNonCollapsing );
    }

    public SourcedContent replace( String pReplace, String pWith ) {
        return instanceFrom( new MutableString( mContent ).replace( pReplace, pWith ) );
    }

    public SourcedContent removeNonCollapsing( String... pToRemove ) {
        return instanceFrom( new MutableString( mContent ).removeNonCollapsing( pToRemove ) );
    }

    public SourcedContent removeCollapsing( String... pToRemove ) {
        return instanceFrom( new MutableString( mContent ).removeCollapsing( pToRemove ) );
    }

    private SourcedContent instanceFrom( MutableString pPossiblyChangedContent ) {
        String zContent = pPossiblyChangedContent.toString();
        return mContent.equals( zContent ) ? this : new SourcedContent( mSource, zContent );
    }

    public SourcedContent errorOnNoContent( String pKey ) {
        if ( !isSignificant() ) {
            mSource.addError( Issue.of( "NoContent", pKey ) );
        }
        return this;
    }

    public IllegalStateException exception( String pMessagePrefix ) {
        return new IllegalStateException( pMessagePrefix + " in " + getErrorPrefix() );
    }

    public IllegalStateException exception( String pMessagePrefix, int pStartAt, int pEndAt ) {
        return exception( pMessagePrefix + " (" + pStartAt + "," + pEndAt + ")" );
    }

    @Override
    public String toString() {
        return "SourcedContent (" + getErrorPrefix() + "): " + mContent;
    }
}
