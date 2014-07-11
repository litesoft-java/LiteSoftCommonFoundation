// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;

public class Paths {
    public static boolean endsWithSep( String pPath ) {
        return (pPath != null) && Characters.isPathSep( pPath.charAt( pPath.length() - 1 ) );
    }

    public static int lastSepAt( String pPath ) {
        if ( pPath != null ) {
            for ( int at = pPath.length(); --at >= 0; ) {
                if ( Characters.isPathSep( pPath.charAt( at ) ) ) {
                    return at;
                }
            }
        }
        return -1;
    }

    public static String forwardSlash( String pPath ) {
        return pPath.replace( '\\', '/' );
    }

    /**
     * Given path(s) that may be system dependent, create a path
     * that is converted to a system independent form (forward Slashes).
     * Note: Ignore null entries (null, or all nulls returns null).
     */
    public static String forwardSlashCombine( String... pPaths ) {
        StringBuilder sb = null;
        if ( pPaths != null ) {
            for ( String path : pPaths ) {
                if ( path != null ) {
                    if ( sb == null ) {
                        sb = new StringBuilder();
                    }
                    if ( path.length() != 0 ) {
                        String fsp = forwardSlash( path );
                        if ( sb.length() == 0 ) {
                            sb.append( fsp );
                        } else {
                            boolean pStartsWithSlash = (fsp.charAt( 0 ) == '/');
                            if ( sb.charAt( sb.length() - 1 ) == '/' ) {
                                sb.append( pStartsWithSlash ? fsp.substring( 1 ) : fsp );
                            } else {
                                if ( !pStartsWithSlash ) {
                                    sb.append( '/' );
                                }
                                sb.append( fsp );
                            }
                        }
                    }
                }
            }
        }
        return (sb == null) ? null : sb.toString();
    }

    public static String justTheLastName( String pPath ) {
        pPath = "/\\" + pPath;
        return pPath.substring( Math.max( pPath.lastIndexOf( '/' ), pPath.lastIndexOf( '\\' ) ) + 1 );
    }

    public static String getExtension( String pPath ) {
        pPath = "/" + forwardSlash( ConstrainTo.notNull( pPath ) ).trim();
        String zName = pPath.substring( pPath.lastIndexOf( '/' ) );
        int zDotAt = zName.lastIndexOf( '.' );
        if ( zDotAt != -1 ) {
            String zExtension = zName.substring( zDotAt + 1 );
            if ( zExtension.length() != 0 ) {
                return zExtension;
            }
        }
        return null;
    }
}
