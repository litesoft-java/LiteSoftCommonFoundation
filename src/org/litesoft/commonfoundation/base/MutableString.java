package org.litesoft.commonfoundation.base;

public class MutableString {
    private String string;
    private StringBuilder sb;
    private boolean mChanged;

    public MutableString( CharSequence pSource ) {
        string = ConstrainTo.notNull( pSource );
    }

    public boolean lastOperationChanged() {
        return mChanged;
    }

    @Override
    public String toString() {
        if ( sb != null ) {
            string = sb.toString();
            sb = null;
        }
        return string;
    }

    /**
     * @return this - convenience for chaining
     */
    public MutableString singleSpace() {
        return changed( singleSpace( indexOfToSB( "  " ) ) );
    }

    /**
     * @return this - convenience for chaining
     */
    public MutableString trim() {
        return updateString( toString().trim() );
    }

    private MutableString updateString( String pNewString ) {
        if ( pNewString.equals( string ) ) {
            return noChange();
        }
        string = pNewString;
        return changed();
    }

    private MutableString changed() {
        return changed( true );
    }

    private MutableString noChange() {
        return changed( false );
    }

    private MutableString changed( boolean pChanged ) {
        mChanged = pChanged;
        return this;
    }

    /**
     * @param pToRemove - null(s) and empty strings ignored.
     *
     * @return this - convenience for chaining
     */
    public MutableString removeNonCollapsing( String... pToRemove ) {
        return changed( (pToRemove != null) && removeArrayNotNull( pToRemove ) );
    }

    private boolean removeArrayNotNull( String[] pToRemove ) {
        boolean changed = false;
        for ( String zToRemove : pToRemove ) {
            changed |= replaceCheckParams( zToRemove, "" );
        }
        return changed;
    }

    /**
     * @param pToRemove - null(s) and empty strings ignored.
     *
     * @return this - convenience for chaining
     */
    public MutableString removeCollapsing( String... pToRemove ) {
        if ( (pToRemove == null) || !removeArrayNotNull( pToRemove ) ) {
            return noChange();
        }
        for ( int zCount = 1; zCount < 100; zCount++ ) { // Cap out at 100 repeats of the potential colapse!
            if ( !removeArrayNotNull( pToRemove ) ) {
                break;
            }
        }
        return changed();
    }

    /**
     * @param pToReplace -  null or empty ignored.
     * @param pWith      - null treated as an empty string
     *
     * @return this - convenience for chaining
     */
    public MutableString replace( char pToReplace, char pWith ) {
        return (pToReplace == pWith) ? noChange() : updateString( toString().replace( pToReplace, pWith ) );
    }

    /**
     * @param pFromTos -   null(s) and empty "Name"s are ignored and null "Value"s are treated as an empty string.
     *
     * @return this - convenience for chaining
     */
    public MutableString replace( NamedStringValueSource... pFromTos ) {
        boolean changed = false;
        if ( pFromTos != null ) {
            for ( NamedStringValueSource zFromTo : pFromTos ) {
                changed |= replaceCheckParams( zFromTo.getName(), zFromTo.getValue() );
            }
        }
        return changed( changed );
    }

    /**
     * @param pToReplace -  null or empty ignored.
     * @param pWith      - null treated as an empty string
     *
     * @return this - convenience for chaining
     */
    public MutableString replace( String pToReplace, String pWith ) {
        return changed( replaceCheckParams( pToReplace, pWith ) );
    }

    /**
     * @return if Changed
     */
    private boolean replaceCheckParams( String pToReplace, String pWith ) {
        return isNotNullOrEmpty( pToReplace ) && !pToReplace.equals( pWith ) && replaceParamsOK( pToReplace, pWith );
    }

    private boolean isNotNullOrEmpty( String pToReplace ) { // TODO: Currently.isNotNullOrEmpty( ... )
        return (pToReplace != null) && (pToReplace.length() != 0);
    }

    /**
     * @return if Changed
     */
    private boolean replaceParamsOK( String pToReplace, String pWith ) {
        return replaceInBuffer( pToReplace, pWith, indexOfToSB( pToReplace ) );
    }

    private int indexOfToSB( String pToFind ) {
        if ( sb != null ) {
            return sb.indexOf( pToFind );
        }
        int zAt = string.indexOf( pToFind );
        if ( zAt != -1 ) {
            sb = new StringBuilder( string );
        }
        return zAt;
    }

    /**
     * @return if Changed
     */
    private boolean replaceInBuffer( String pToReplace, String pWith, int pFrom ) {
        if ( pFrom == -1 ) {
            return false;
        }
        pWith = ConstrainTo.notNull( pWith );
        do {
            int zEnd = pFrom + pToReplace.length();
            if ( pWith.length() == 0 ) {
                sb.delete( pFrom, zEnd );
            } else {
                sb.replace( pFrom, zEnd, pWith );
            }
        } while ( -1 != (pFrom = sb.indexOf( pToReplace, pFrom )) );
        return true;
    }

    private boolean singleSpace( int pFrom ) {
        if ( pFrom == -1 ) {
            return false;
        }
        do {
            sb.deleteCharAt( pFrom );
        } while ( -1 != (pFrom = sb.indexOf( "  " )) );
        return true;
    }
}
