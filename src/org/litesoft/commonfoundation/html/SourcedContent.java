package org.litesoft.commonfoundation.html;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.issue.*;
import org.litesoft.commonfoundation.typeutils.*;

public class SourcedContent {
    private final Source mSource;
    private String mContent;

    public SourcedContent( Source pSource, String pContent ) {
        mSource = Confirm.isNotNull( "Source", pSource );
        mContent = pContent;
    }

    public Source getSource() {
        return mSource;
    }

    public String getErrorPrefix() {
        StringBuilder sb = new StringBuilder();
        sb.append( mSource.getSource() );
        for ( Source zSource = mSource.getNext(); zSource != null; zSource = zSource.getNext() ) {
            sb.append( " in" ).append( zSource.getSource() );
        }
        return sb.toString();
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

    public void tossUpTo( int pUpto ) {
        mContent = mContent.substring( pUpto );
    }

    public SourcedContent deeper( String pLevel, String pNewContent ) {
        return new SourcedContent( mSource.plus( pLevel ), pNewContent );
    }

    public SourcedContent deeper( String pLevel, int pFrom, int pUpto ) {
        return deeper( pLevel, mContent.substring( pFrom, pUpto ) );
    }

    public SourcedContent deeper( String pLevel, String pFrom, String pUpto, String... pToRemove ) {
        int zFromAt = indexOf( pFrom );
        int zUptoAt = indexOf( pUpto, zFromAt + 1 );
        if ( (zFromAt == -1) || (zUptoAt == -1) ) {
            throw exception( "Unable to locate " + pLevel + " w/ Boundaries '" + pFrom + "' and '" + pUpto + "'", zFromAt, zUptoAt );
        }
        return deeper( pLevel, zFromAt + pFrom.length(), zUptoAt ).remove( null, pToRemove );
    }

    @SuppressWarnings("ConstantConditions")
    public SourcedContent normalize( String... pToRemove ) {
        StringBuilder sb = null;
        sb = replace( sb, " />", "/>" );
        sb = replace( sb, "<th>", "<td>" );
        sb = replace( sb, "</th>", "</td>" );

        sb = replace( sb, "<hr/>", " " );
        sb = replace( sb, "<br/>", " " );
        sb = replace( sb, "<b>", " " );
        sb = replace( sb, "</b>", " " );
        sb = replace( sb, "<p>", " " );
        sb = replace( sb, "</p>", " " );

        sb = killLeadingSpace( sb, ' ' );
        sb = killLeadingSpace( sb, '<' );

        sb = replace( sb, "> ", ">" );

        sb = replace( sb, "<div></div>", "" );
        sb = replace( sb, "<td></td>", "" );
        sb = replace( sb, "<tr></tr>", "" );
        return remove( sb, pToRemove );
    }

    @SuppressWarnings("ConstantConditions")
    public SourcedContent replace( String pReplace, String pWith ) {
        mContent = Strings.replace( mContent, pReplace, pWith );
        return this;
    }

    public SourcedContent remove( String... pToRemove ) {
        return remove( null, pToRemove );
    }

    private SourcedContent remove( StringBuilder sb, String... pToRemove ) {
        if ( (pToRemove != null) && (pToRemove.length != 0) ) {
            for ( String zToRemove : pToRemove ) {
                sb = replace( sb, zToRemove, "" );
            }
            if ( sb != null ) {
                mContent = sb.toString();
            }
        }
        return this;
    }

    private StringBuilder killLeadingSpace( StringBuilder sb, char pIfFollowedBy ) {
        CharSequence src = (sb != null) ? sb : mContent;
        StringBuilder rv = new StringBuilder( src.length() );
        int zAt = 0;
        while ( zAt < src.length() ) {
            zAt = transferTillSpace( src, rv, zAt );
            zAt = processSpace( zAt, src, rv, pIfFollowedBy ); // @ ' ' or EOT
        }
        return rv;
    }

    private int processSpace( int pAt, CharSequence pSrc, StringBuilder pDst, char pIfFollowedBy ) {
        if ( ++pAt < pSrc.length() ) {
            if ( pIfFollowedBy != pSrc.charAt( pAt ) ) {
                pDst.append( ' ' );
            }
        }
        return pAt;
    }

    private int transferTillSpace( CharSequence pSrc, StringBuilder pDst, int pAt ) {
        for (; pAt < pSrc.length(); pAt++ ) {
            char c = pSrc.charAt( pAt );
            if ( c == ' ' ) {
                return pAt;
            }
            pDst.append( c );
        }
        return pAt;
    }

    private StringBuilder replace( StringBuilder sb, String pToReplace, String pWith ) {
        int zFrom = (sb != null) ? sb.indexOf( pToReplace ) : mContent.indexOf( pToReplace );
        if ( zFrom != -1 ) {
            if ( sb == null ) {
                sb = new StringBuilder( mContent );
            }
            do {
                int zEnd = zFrom + pToReplace.length();
                if ( pWith.length() == 0 ) {
                    sb.delete( zFrom, zEnd );
                } else {
                    sb.replace( zFrom, zEnd, pWith );
                }
            } while ( -1 != (zFrom = sb.indexOf( pToReplace )) );
        }
        return sb;
    }

    public IllegalStateException exception( String pMessagePrefix ) {
        return new IllegalStateException( pMessagePrefix + " in " + getErrorPrefix() );
    }

    public IllegalStateException exception( String pMessagePrefix, int pStartAt, int pEndAt ) {
        return exception( pMessagePrefix + " (" + pStartAt + "," + pEndAt + ")" );
    }

    @Override
    public String toString() {
        return "Pair (" + getErrorPrefix() + "): " + mContent;
    }
}
