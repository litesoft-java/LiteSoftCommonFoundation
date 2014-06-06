package org.litesoft.commonfoundation.typeutils.gregorian;

import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

public enum Month
{
    January( 31 ),
    Febuary()
            {
                @Override
                public int daysIn( int pYear )
                {
                    return Year.isLeap( pYear ) ? 29 : 28;
                }
            },
    March( 31 ),
    April( 30 ),
    May( 31 ),
    June( 30 ),
    July( 31 ),
    August( 31 ),
    September( 30 ),
    October( 31 ),
    November( 30 ),
    December( 31 );

    private final int mDaysInMonth;
    private final String mShortName;
    private final String mNameUpperCase;

    private Month( int pDaysInMonth )
    {
        mDaysInMonth = pDaysInMonth;
        mShortName = name().substring( 0, 3 );
        mNameUpperCase = name().toUpperCase();
    }

    private Month()
    {
        this( -1 );
    }

    public String shortName()
    {
        return mShortName;
    }

    public String nameUpperCase()
    {
        return mNameUpperCase;
    }

    /**
     * Days in 'this' Month for the specified Year.
     * <p/>
     * Dates prior to the year 1800 may get incorrect results!
     */
    public int daysIn( int pYear )
    {
        return mDaysInMonth;
    }

    public int toMonthNumber()
    {
        return ordinal() + 1;
    }

    public static Month fromMonthNumber( int pMonthNumber )
    {
        return ((1 <= pMonthNumber) && (pMonthNumber <= 12)) ? values()[pMonthNumber - 1] : null;
    }

    public static Month valueOf( int pOrdinal )
    {
        while ( pOrdinal < 0 )
        {
            pOrdinal += 12;
        }
        while ( 12 <= pOrdinal )
        {
            pOrdinal -= 12;
        }
        return values()[pOrdinal];
    }

    public Month prev()
    {
        return valueOf( ordinal() - 1 );
    }

    public Month next()
    {
        return valueOf( ordinal() + 1 );
    }

    public static String shortNameFromMonthNumber( int pMonthNumber )
    {
        Month zMonth = Month.fromMonthNumber( pMonthNumber );
        return (zMonth == null) ? "---" : zMonth.shortName();
    }

    public static String nameFromMonthNumber( int pMonthNumber )
    {
        Month zMonth = Month.fromMonthNumber( pMonthNumber );
        return (zMonth == null) ? "---" : zMonth.name();
    }

    /**
     * Days in specified Year & Month (or 0 if the month is invalid).
     * <p/>
     * Dates prior to the year 1800 may get incorrect results!
     */
    public static int daysIn( int pYear, int pMonth )
    {
        Month zMonth = fromMonthNumber( pMonth );
        return (zMonth == null) ? 0 : zMonth.daysIn( pYear );
    }

    public static int parseMonth( String pToParse, String pFrom )
            throws IllegalArgumentException
    {
        pToParse = pToParse.trim().toUpperCase();
        if ( pToParse.length() == 0 )
        {
            throw new IllegalArgumentException( "No Month in '" + pFrom + "'" );
        }
        Month zFound = null;
        List<Month> zMultipleFounds = null;
        for ( Month zMonth : values() )
        {
            if ( zMonth.nameUpperCase().startsWith( pToParse ) )
            {
                if ( zFound == null )
                {
                    zFound = zMonth;
                }
                else
                {
                    if ( zMultipleFounds == null )
                    {
                        zMultipleFounds = Lists.newArrayList();
                        zMultipleFounds.add( zFound );
                    }
                    zMultipleFounds.add( zMonth );
                }
            }
        }
        if ( zMultipleFounds != null )
        {
            String zMultiple = zMultipleFounds.toString();
            throw new IllegalArgumentException(
                    "Invalid Month '" + pToParse + "' in '" + pFrom + "': Matched-" + zMultiple.substring( 1, zMultiple.length() - 1 ) );
        }
        if ( zFound == null )
        {
            throw new IllegalArgumentException( "Month '" + pToParse + "' NOT recognized in '" + pFrom + "'" );
        }
        return zFound.toMonthNumber();
    }
}
