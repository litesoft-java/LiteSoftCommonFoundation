package org.litesoft.commonfoundation.typeutils;

public class Integers extends Numerics
{
    public static final int[] EMPTY_ARRAY_PRIMITIVES = new int[0];
    public static final Integer[] EMPTY_ARRAY = new Integer[0];

    public static int deNull( Integer pValue )
    {
        return (pValue != null) ? pValue : 0;
    }

    public static Integer assertNonNegative( String pName, Integer pValue )
            throws IllegalArgumentException
    {
        if ( pValue != null )
        {
            assertNonNegative( pName, pValue.intValue() );
        }
        return pValue;
    }

    public static int assertNonNegative( String pName, int pValue )
            throws IllegalArgumentException
    {
        if ( pValue < 0 )
        {
            throw new IllegalArgumentException( pName + CANNOT_BE_NEGATIVE + ", but was: " + pValue );
        }
        return pValue;
    }

    public static int assertPositive( String pWhat, int pValue )
    {
        if ( 0 < pValue )
        {
            return pValue;
        }
        throw new IllegalArgumentException( pWhat + MUST_BE_POSITIVE + ", but was: " + pValue );
    }

    public static void assertNotEqual( String pObjectName, int pNotExpected, int pActual )
            throws IllegalArgumentException
    {
        if ( pNotExpected == pActual )
        {
            throw new IllegalArgumentException( pObjectName + ": '" + pNotExpected + "' Not allowed" );
        }
    }

    public static void assertEqual( String pObjectName, int pExpected, int pActual )
            throws IllegalArgumentException
    {
        if ( pExpected != pActual )
        {
            throw new IllegalArgumentException( pObjectName + ": Expected '" + pExpected + "', but was '" + pActual + "'" );
        }
    }

    public static String zeroPadIt( int pMinDesiredLength, int pIt )
    {
        String rv = String.valueOf( pIt );
        while ( rv.length() < pMinDesiredLength )
        {
            rv = "0" + rv;
        }
        return rv;
    }

    public static String padIt( int pMinDesiredLength, int pIt )
    {
        return Strings.padIt( pMinDesiredLength, "" + pIt );
    }

    public static String iTpad( int pIt, int pMinDesiredLength )
    {
        return Strings.iTpad( "" + pIt, pMinDesiredLength );
    }

    public static boolean isNullOrEmpty( int[] pArrayToCheck )
    {
        return (pArrayToCheck == null || pArrayToCheck.length == 0);
    }

    public static boolean areArraysEqual( int[] pThis, int[] pThat )
    {
        if ( pThis == pThat ) // handles if both are null
        {
            return true;
        }
        if ( (pThis != null) && (pThat != null) && (pThis.length == pThat.length) )
        {
            for ( int i = pThis.length; --i >= 0; )
            {
                if ( pThis[i] != pThat[i] )
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean checkIndex( int pIndex, int pMax, boolean pInclusive )
    {
        return ((0 <= pIndex) && (pIndex < pMax)) || (pInclusive && (pIndex == pMax));
    }

    public static void validateIndex( int pIndex, int pMax, boolean pInclusive )
            throws IllegalArgumentException
    {
        if ( !checkIndex( pIndex, pMax, pInclusive ) )
        {
            throw new IllegalArgumentException( "!( 0 <= " + pIndex + " " + (pInclusive ? "<=" : "<") + " " + pMax + " )" );
        }
    }

    /**
     * return pIndex constrained between 0 (inclusive) and pMax (pInclusive).  If the effective 'max' is less than 0, then 0 will be returned.
     */
    public static int constrainIndex( int pIndex, int pMax, boolean pInclusive )
    {
        if ( (pMax < pIndex) || (!pInclusive && (pIndex == pMax)) )
        {
            pIndex = pInclusive ? pMax : pMax - 1;
        }
        return (pIndex < 0) ? 0 : pIndex;
    }

    public static String getMax2PlacePercentage( int pPortions )
    {
        if ( pPortions == 0 )
        {
            return null;
        }
        int portion = 10000 / pPortions;
        String s = "" + portion;
        while ( s.length() < 3 )
        {
            s = "0" + s;
        }
        int wholeLen = s.length() - 2;
        s = s.substring( 0, wholeLen ) + "." + s.substring( wholeLen );
        while ( s.charAt( s.length() - 1 ) == '0' )
        {
            s = s.substring( 0, s.length() - 1 );
        }
        if ( s.charAt( s.length() - 1 ) == '.' )
        {
            s = s.substring( 0, s.length() - 1 );
        }
        return s + "%";
    }

    public static boolean areNonArraysEqual( int pThis, int pThat )
    {
        return (pThis == pThat);
    }

    /**
     * Returns a String that represents <i>pThis</i> with english code tail,
     * such as "3rd".<p>
     * <p/>
     * If <i>pThis</i> is non-negative, then append on the appropriate two
     * letter <i>english</i> code:<p>
     * <pre>
     *      st,nd,rd,th
     * </pre><p>
     *
     * @param pThis the int to convert.<p>
     *
     * @return the String representation for <i>pThis</i> and if not negative,
     * then appended with the appropriate two letter <i>english</i>
     * junk.<p>
     */
    public static String toNth( int pThis )
    {
        String rv = Integer.toString( pThis );
        if ( pThis < 0 )
        {
            return rv;
        }

        if ( (pThis < 10) || ('1' != rv.charAt( rv.length() - 2 )) ) // not: 10-19 & 110-119, 210-219, ... 1010-1019, ...
        {
            switch ( rv.charAt( rv.length() - 1 ) )
            {
                case '1':
                    return rv + "st";
                case '2':
                    return rv + "nd";
                case '3':
                    return rv + "rd";
                default: // 0th and n0th and 4th - 9th and n4th - n9th
                    break;
            }
        }
        return rv + "th";
    }

    public static int insureNonNegative( int pValue )
    {
        return (pValue < 0) ? 0 : pValue;
    }

    public static int intValue( Integer pInteger, int pDefault )
    {
        return (pInteger != null) ? pInteger : pDefault;
    }

    public static int intValue( long pValue )
    {
        if ( (Integer.MIN_VALUE <= pValue) && (pValue <= Integer.MAX_VALUE) )
        {
            return (int) pValue;
        }
        throw new IllegalArgumentException( "Value (" + pValue + ") is outside the acceptable Integer Range" );
    }

    public static boolean isEven( int pInt )
    {
        return ((pInt & 1) == 0);
    }

    public static boolean isOdd( int pInt )
    {
        return ((pInt & 1) == 1);
    }
}
