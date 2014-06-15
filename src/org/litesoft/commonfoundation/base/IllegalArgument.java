// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.base;

/**
 * @author George Smith
 * @version 1.0 02/02/02 Initial Version
 */
public class IllegalArgument {
    public static void ifNotNull( String pReferenceLabel, Object pToTest ) {
        if ( pToTest != null ) {
            throw ofNotNull( pReferenceLabel, pToTest );
        }
    }

    public static <T> T ifNull( String pReferenceLabel, T pToTest ) {
        if ( pToTest == null ) {
            throw ofNull( pReferenceLabel );
        }
        return pToTest;
    }

    public static <T> T ifEmpty( String pReferenceLabel, T pToTest, int pSize ) {
        if ( pSize == 0 ) {
            throw ofEmpty( pReferenceLabel );
        }
        return pToTest;
    }

    public static boolean ifNotEqual( String pReferenceLabel, boolean pToTest, boolean pExpected ) {
        if ( pExpected == pToTest ) {
            return pToTest;
        }
        throw ofNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static char ifNotEqual( String pReferenceLabel, char pToTest, char pExpected ) {
        if ( pExpected == pToTest ) {
            return pToTest;
        }
        throw ofNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static int ifNotEqual( String pReferenceLabel, int pToTest, int pExpected ) {
        if ( pExpected == pToTest ) {
            return pToTest;
        }
        throw ofNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static long ifNotEqual( String pReferenceLabel, long pToTest, long pExpected ) {
        if ( pExpected == pToTest ) {
            return pToTest;
        }
        throw ofNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static float ifNotEqual( String pReferenceLabel, float pToTest, float pExpected ) {
        if ( pExpected == pToTest ) {
            return pToTest;
        }
        throw ofNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static double ifNotEqual( String pReferenceLabel, double pToTest, double pExpected ) {
        if ( pExpected == pToTest ) {
            return pToTest;
        }
        throw ofNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static <T> T ifNotEqual( String pReferenceLabel, T pToTest, T pExpected ) {
        if ( (pExpected == pToTest) || ((pExpected != null) && pExpected.equals( pToTest )) ) {
            return pToTest;
        }
        throw ofNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static IllegalArgumentException ofNotNull( String pReferenceLabel, Object pNotNull ) {
        return exception( pReferenceLabel, "Expected to be 'null', but was '" + pNotNull + "'!" );
    }

    public static IllegalArgumentException ofNull( String pReferenceLabel ) {
        return exception( pReferenceLabel, "may NOT be 'null'!" );
    }

    public static IllegalArgumentException ofEmpty( String pReferenceLabel ) {
        return exception( pReferenceLabel, "may NOT be empty!" );
    }

    public static IllegalArgumentException ofNotEqual( String pReferenceLabel, Object pActual, Object pExpected ) {
        return exception( ConstrainTo.notNull( pReferenceLabel, "Values Mismatch" ),
                          "\n    Expected '" + pExpected + "'," +
                          "\n     but was '" + pActual + "'!" );
    }

    public static IllegalArgumentException exception( String pReferenceLabel, String pWhy ) {
        return new IllegalArgumentException( pReferenceLabel + " " + pWhy );
    }
}
