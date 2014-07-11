package org.litesoft.commonfoundation.base;

import java8.util.function.*;

import java.util.*;

@SuppressWarnings("UnusedDeclaration")
public class Confirm {

    // TODO: vvvvvvvvvvvvvvvvvvvvvvvv  NEW  vvvvvvvvvvvvvvvvvvvvvvvv :ODOT \\

    public static String significantAsIs( String pReferenceLabel, String pToCheck )
            throws IllegalArgumentException {
        String zSignificant = significant( pReferenceLabel, pToCheck );
        if ( zSignificant.length() != pToCheck.length() ) {
            throw IllegalArgument.exception( pReferenceLabel, "may not have leading or trailing spaces: " + pToCheck );
        }
        return pToCheck;
    }

    public static String nullOrSignificantAsIs( String pReferenceLabel, String pToCheck )
            throws IllegalArgumentException {
        return (pToCheck == null) ? null : significantAsIs( pReferenceLabel, pToCheck );
    }

    public static String nullOrNotEmpty( String pReferenceLabel, String pToCheck )
            throws IllegalArgumentException {
        if ( (pToCheck != null) && (pToCheck.length() == 0) ) {
            IllegalArgument.ifEmpty( pReferenceLabel, pToCheck, pToCheck.length() );
        }
        return pToCheck;
    }

    public static String[] notNullNotEmptyAllSignificant( String pReferenceLabel, String[] pToTest )
            throws IllegalArgumentException {
        String[] rv = new String[isNotNullOrEmpty( pReferenceLabel, pToTest ).length];
        for ( int i = 0; i < pToTest.length; i++ ) {
            if ( null == (rv[i] = ConstrainTo.significantOrNull( pToTest[i] )) ) {
                throw IllegalArgument.ofEmpty( pReferenceLabel + "[" + i + "]" );
            }
        }
        return rv;
    }

    // TODO: ^^^^^^^^^^^^^^^^^^^^^^^^  NEW  ^^^^^^^^^^^^^^^^^^^^^^^^ :ODOT \\

    public static void isNull( String pReferenceLabel, Object pToTest )
            throws IllegalArgumentException {
        IllegalArgument.ifNotNull( pReferenceLabel, pToTest );
    }

    public static <T> T isNotNull( String pReferenceLabel, T pToTest )
            throws IllegalArgumentException {
        return IllegalArgument.ifNull( pReferenceLabel, pToTest );
    }

    public static String isNotNullOrEmpty( String pReferenceLabel, String pToTest )
            throws IllegalArgumentException {
        return IllegalArgument.ifEmpty( pReferenceLabel, pToTest, IllegalArgument.ifNull( pReferenceLabel, pToTest ).length() );
    }

    public static <T extends Collection<?>> T isNotNullOrEmpty( String pReferenceLabel, T pToTest )
            throws IllegalArgumentException {
        return IllegalArgument.ifEmpty( pReferenceLabel, pToTest, IllegalArgument.ifNull( pReferenceLabel, pToTest ).size() );
    }

    public static <T extends Map<?, ?>> T isNotNullOrEmpty( String pReferenceLabel, T pToTest )
            throws IllegalArgumentException {
        return IllegalArgument.ifEmpty( pReferenceLabel, pToTest, IllegalArgument.ifNull( pReferenceLabel, pToTest ).size() );
    }

    public static <T> T[] isNotNullOrEmpty( String pReferenceLabel, T[] pToTest )
            throws IllegalArgumentException {
        return IllegalArgument.ifEmpty( pReferenceLabel, pToTest, IllegalArgument.ifNull( pReferenceLabel, pToTest ).length );
    }

    public static <T extends Collection<?>> T isNotNullOrEmptyAndHasNoNullEntries( String pReferenceLabel, T pToTest )
            throws IllegalArgumentException {
        IllegalArgument.ifEmpty( pReferenceLabel, pToTest, IllegalArgument.ifNull( pReferenceLabel, pToTest ).size() );
        testElementsNotNull( pReferenceLabel, pToTest.toArray(), " entry '", "'" );
        return pToTest;
    }

    public static <T> T[] isNotNullOrEmptyAndHasNoNullEntries( String pReferenceLabel, T[] pToTest )
            throws IllegalArgumentException {
        return testElementsNotNull( pReferenceLabel, isNotNullOrEmpty( pReferenceLabel, pToTest ) );
    }

    public static <T> T[] isNotNullAndElementsNotNull( String pReferenceLabel, T[] pToTest )
            throws IllegalArgumentException {
        return testElementsNotNull( pReferenceLabel, isNotNull( pReferenceLabel, pToTest ) );
    }

    public static String significant( String pReferenceLabel, String pToTest )
            throws IllegalArgumentException {
        String zValue = IllegalArgument.ifNull( pReferenceLabel, pToTest ).trim(); // Note the trim()!
        return IllegalArgument.ifEmpty( pReferenceLabel, zValue, zValue.length() );
    }

    public static String insignificant( String pReferenceLabel, String pToTest ) {
        if ( null != pToTest ) {
            if ( (pToTest = pToTest.trim()).length() != 0 ) {
                throw IllegalArgument.exception( pReferenceLabel, "was NOT empty, but: " + pToTest );
            }
        }
        return null;
    }

    public static boolean isTrue( String pReferenceLabel, boolean pToTest )
            throws IllegalArgumentException {
        return IllegalArgument.ifNotEqual( pReferenceLabel, pToTest, true );
    }

    public static boolean isFalse( String pReferenceLabel, boolean pToTest ) {
        return IllegalArgument.ifNotEqual( pReferenceLabel, pToTest, false );
    }

    public static <T> T isEqual( String pReferenceLabel, T pToTest, T pExpected ) {
        return IllegalArgument.ifNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static boolean isEqual( String pReferenceLabel, boolean pToTest, boolean pExpected )
            throws IllegalArgumentException {
        return IllegalArgument.ifNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static char isEqual( String pReferenceLabel, char pToTest, char pExpected )
            throws IllegalArgumentException {
        return IllegalArgument.ifNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static int isEqual( String pReferenceLabel, int pToTest, int pExpected )
            throws IllegalArgumentException {
        return IllegalArgument.ifNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static long isEqual( String pReferenceLabel, long pToTest, long pExpected )
            throws IllegalArgumentException {
        return IllegalArgument.ifNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static float isEqual( String pReferenceLabel, float pToTest, float pExpected )
            throws IllegalArgumentException {
        return IllegalArgument.ifNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static double isEqual( String pReferenceLabel, double pToTest, double pExpected )
            throws IllegalArgumentException {
        return IllegalArgument.ifNotEqual( pReferenceLabel, pToTest, pExpected );
    }

    public static <T> void areEqual( T pExpected, T pActual ) {
        areEqual( null, pActual, pExpected );
    }

    public static void areEqual( int pExpected, int pActual ) {
        areEqual( null, pActual, pExpected );
    }

    public static void areEqual( long pExpected, long pActual ) {
        areEqual( null, pActual, pExpected );
    }

    public static void areEqualIgnoreCase( String pReferenceLabel, String pExpected, String pActual ) {
        if ( !Currently.areEqualIgnoreCase( pExpected, pActual ) ) {
            throw IllegalArgument.ofNotEqual( pReferenceLabel, pActual, pExpected );
        }
    }

    public static <T> void areEqual( Supplier<String> pWhatSource, T pExpected, T pActual ) {
        if ( !Currently.areEqual( pExpected, pActual ) ) {
            throw IllegalArgument.ofNotEqual( pWhatSource, pActual, pExpected );
        }
    }

    public static void areEqual( Supplier<String> pWhatSource, int pExpected, int pActual ) {
        if ( !Currently.areEqual( pExpected, pActual ) ) {
            throw IllegalArgument.ofNotEqual( pWhatSource, pActual, pExpected );
        }
    }

    public static void areEqual( Supplier<String> pWhatSource, long pExpected, long pActual ) {
        if ( !Currently.areEqual( pExpected, pActual ) ) {
            throw IllegalArgument.ofNotEqual( pWhatSource, pActual, pExpected );
        }
    }

    public static void areEqualIgnoreCase( Supplier<String> pWhatSource, String pExpected, String pActual ) {
        if ( !Currently.areEqualIgnoreCase( pExpected, pActual ) ) {
            throw IllegalArgument.ofNotEqual( pWhatSource, pActual, pExpected );
        }
    }

    public static String significantOfToStringOf( String pReferenceLabel, Object pToTest ) {
        return significant( pReferenceLabel, (pToTest != null) ? pToTest.toString() : null );
    }

    public static void endsWith( String pReferenceLabel, String pToTest, String pForString ) {
        if ( !pToTest.endsWith( pForString ) ) {
            throw IllegalArgument.exception( pReferenceLabel, "'" + pToTest + "' did not end with '" + pForString + "'!" );
        }
    }

    private static <T> T[] testElementsNotNull( String pReferenceLabel, T[] pToTest ) {
        testElementsNotNull( pReferenceLabel, pToTest, "[", "]" );
        return pToTest;
    }

    private static void testElementsNotNull( String pReferenceLabel, Object[] pElements, String pEntryPrefix, String pEntrySuffix ) {
        for ( int i = 0; i < pElements.length; i++ ) {
            if ( null == pElements[i] ) {
                throw IllegalArgument.ofNull( pReferenceLabel + pEntryPrefix + i + pEntrySuffix );
            }
        }
    }
}
