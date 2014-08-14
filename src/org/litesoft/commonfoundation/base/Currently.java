package org.litesoft.commonfoundation.base;

import java.util.*;

public class Currently {

    // TODO: vvvvvvvvvvvvvvvvvvvvvvvv  NEW  vvvvvvvvvvvvvvvvvvvvvvvv :ODOT \\

    public static boolean isNullOrEmpty( String pStringToCheck ) {
        return ((pStringToCheck == null) || (pStringToCheck.trim().length() == 0));
    }

    public static boolean isNotNullOrEmpty( String pStringToCheck ) {
        return ((pStringToCheck != null) && (pStringToCheck.trim().length() != 0));
    }

    // TODO: ^^^^^^^^^^^^^^^^^^^^^^^^  NEW  ^^^^^^^^^^^^^^^^^^^^^^^^ :ODOT \\

    @SuppressWarnings("StringEquality")
    public static boolean areEqualIgnoreCase( String pStr1, String pStr2 ) {
        return (pStr1 == pStr2) || // Same String or both null
               ((pStr1 != null) && pStr1.equalsIgnoreCase( pStr2 )) ||
               ((pStr2 != null) && pStr2.equalsIgnoreCase( pStr1 ));
    }

    public static boolean completelyInsignificant( String... pToTest ) {
        if ( pToTest != null ) {
            for ( String zString : pToTest ) {
                if ( significant( zString ) ) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean insignificant( String pToTest ) {
        return ((pToTest == null) || (pToTest.trim().length() == 0));
    }

    public static boolean significant( String pToTest ) {
        return ((pToTest != null) && (pToTest.trim().length() != 0));
    }

    public static <T> boolean areEqual( T[] pObjects1, T... pObjects2 ) {
        if ( pObjects1 != pObjects2 ) { // Not Same or not both null
            if ( (pObjects1 == null) || (pObjects2 == null) || (pObjects1.length != pObjects2.length) ) {
                return false;
            }
            for ( int i = 0; i < pObjects1.length; i++ ) {
                if ( !areEqual( pObjects1[i], pObjects2[i] ) ) {
                    return false;
                }
            }
        }
        return true;
    }

    public static <T> boolean areEqual( T pStr1, T pStr2 ) {
        return (pStr1 == pStr2) || // Same or both null
               ((pStr1 != null) && pStr1.equals( pStr2 )) ||
               ((pStr2 != null) && pStr2.equals( pStr1 ));
    }

    public static <T> boolean areAllNull( T... pObjects ) {
        if ( pObjects != null ) {
            for ( T zT : pObjects ) {
                if ( zT != null ) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isEven( int pInt ) {
        return (0 == (pInt & 1));
    }

    public static boolean isEven( long pLong ) {
        return (0 == (pLong & 1));
    }

    public static boolean isNullOrEmpty( List<?> pToCheck ) {
        return (pToCheck == null || pToCheck.isEmpty());
    }

    public static boolean isNotNullOrEmpty( List<?> pToCheck ) {
        return (pToCheck != null && !pToCheck.isEmpty());
    }

    // TODO: vvvvvvvvvvvvvvvvvvvvvvvv  NEW  vvvvvvvvvvvvvvvvvvvvvvvv :ODOT \\

    public static boolean isNotNullOrEmpty( Object[] pArrayToCheck ) {
        return ((pArrayToCheck != null) && (pArrayToCheck.length != 0));
    }

    public static boolean isNullOrEmpty( Object[] pArrayToCheck ) {
        return ((pArrayToCheck == null) || (pArrayToCheck.length == 0));
    }

    public static boolean isNotNull( Object pToCheck ) {
        return (pToCheck != null);
    }

    public static boolean areNotNull( Object... pToChecks ) {
        if ( pToChecks == null ) {
            return false;
        }
        for ( Object toCheck : pToChecks ) {
            if ( toCheck == null ) {
                return false;
            }
        }
        return true;
    }

    public static boolean areEqual( boolean pThis, boolean pThat ) {
        return (pThis == pThat);
    }

    public static boolean isOdd( int pInt ) {
        return ((pInt & 1) == 1);
    }

    public static boolean isNullOrEmpty( int[] pArrayToCheck ) {
        return (pArrayToCheck == null || pArrayToCheck.length == 0);
    }

    public static boolean isNullOrEmpty( long[] pArrayToCheck ) {
        return (pArrayToCheck == null || pArrayToCheck.length == 0);
    }

    public static boolean isNullOrEmpty( double[] pArrayToCheck ) {
        return (pArrayToCheck == null || pArrayToCheck.length == 0);
    }

    public static boolean areEqual( int[] pThis, int... pThat ) {
        if ( pThis == pThat ) // handles if both are null
        {
            return true;
        }
        if ( (pThis != null) && (pThat != null) && (pThis.length == pThat.length) ) {
            for ( int i = pThis.length; --i >= 0; ) {
                if ( pThis[i] != pThat[i] ) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // TODO: ^^^^^^^^^^^^^^^^^^^^^^^^  NEW  ^^^^^^^^^^^^^^^^^^^^^^^^ :ODOT \\
}
