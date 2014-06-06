// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.typeutils.gregorian;

import org.litesoft.commonfoundation.typeutils.*;
import org.litesoft.deprecated.*;

import java.util.*;

/**
 * This class is for interacting with Java's Util Date in Local (or Wall) Time, except where UTC is explicitly mentioned!
 */
@SuppressWarnings("UnusedDeclaration")
public class UtilDateAdaptor implements TimestampAccessorHMSM
{
    public static int calculateCurrentUTCtoWallOffsetMinutes()
    {
        return new UtilDateAdaptor( 0, null ).getUTCtoWallOffsetMinutes();
    }

    /**
     * create with NOW
     */
    public UtilDateAdaptor()
    {
        this( System.currentTimeMillis(), null );
    }

    public UtilDateAdaptor( Date pDate )
    {
        this( pDate, null );
    }

    public UtilDateAdaptor( Date pDate, TemporalResolution pTemporalResolution )
    {
        this( pDate, TemporalResolution.deNull( pTemporalResolution ).ordinal() );
    }

    public static UtilDateAdaptor offsetWallToMilliSec( int pOffsetMinutes, int pYear, int pMonth, int pDay, int pHour, int pMin, int pSec, int pMilliSec )
    {
        return new UtilDateAdaptor( pOffsetMinutes, TemporalResolution.ToMilliSec, pYear, pMonth, pDay, pHour, pMin, pSec, pMilliSec );
    }

    public static UtilDateAdaptor currentWall( int pYear, int pMonth, int pDay, int pHour, int pMin, int pSec, int pMilliSec )
    {
        return offsetWallToMilliSec( calculateCurrentUTCtoWallOffsetMinutes(), pYear, pMonth, pDay, pHour, pMin, pSec, pMilliSec );
    }

    public static UtilDateAdaptor zulu( int pYear, int pMonth, int pDay, int pHour, int pMin, int pSec, int pMilliSec )
    {
        return offsetWallToMilliSec( 0, pYear, pMonth, pDay, pHour, pMin, pSec, pMilliSec );
    }

    public static UtilDateAdaptor offsetWallToSec( int pOffsetMinutes, int pYear, int pMonth, int pDay, int pHour, int pMin, int pSec )
    {
        return new UtilDateAdaptor( pOffsetMinutes, TemporalResolution.ToSec, pYear, pMonth, pDay, pHour, pMin, pSec, 0 );
    }

    public static UtilDateAdaptor currentWall( int pYear, int pMonth, int pDay, int pHour, int pMin, int pSec )
    {
        return offsetWallToSec( calculateCurrentUTCtoWallOffsetMinutes(), pYear, pMonth, pDay, pHour, pMin, pSec );
    }

    public static UtilDateAdaptor zulu( int pYear, int pMonth, int pDay, int pHour, int pMin, int pSec )
    {
        return offsetWallToSec( 0, pYear, pMonth, pDay, pHour, pMin, pSec );
    }

    public static UtilDateAdaptor offsetWallToMin( int pOffsetMinutes, int pYear, int pMonth, int pDay, int pHour, int pMin )
    {
        return new UtilDateAdaptor( pOffsetMinutes, TemporalResolution.ToMin, pYear, pMonth, pDay, pHour, pMin, 0, 0 );
    }

    public static UtilDateAdaptor currentWall( int pYear, int pMonth, int pDay, int pHour, int pMin )
    {
        return offsetWallToMin( calculateCurrentUTCtoWallOffsetMinutes(), pYear, pMonth, pDay, pHour, pMin );
    }

    public static UtilDateAdaptor zulu( int pYear, int pMonth, int pDay, int pHour, int pMin )
    {
        return offsetWallToMin( 0, pYear, pMonth, pDay, pHour, pMin );
    }

    public static UtilDateAdaptor offsetWallToHour( int pOffsetMinutes, int pYear, int pMonth, int pDay, int pHour )
    {
        return new UtilDateAdaptor( pOffsetMinutes, TemporalResolution.ToHour, pYear, pMonth, pDay, pHour, 0, 0, 0 );
    }

    public static UtilDateAdaptor currentWall( int pYear, int pMonth, int pDay, int pHour )
    {
        return offsetWallToHour( calculateCurrentUTCtoWallOffsetMinutes(), pYear, pMonth, pDay, pHour );
    }

    public static UtilDateAdaptor zulu( int pYear, int pMonth, int pDay, int pHour )
    {
        return offsetWallToHour( 0, pYear, pMonth, pDay, pHour );
    }

    public static UtilDateAdaptor offsetWallToDay( int pOffsetMinutes, int pYear, int pMonth, int pDay )
    {
        return new UtilDateAdaptor( pOffsetMinutes, TemporalResolution.ToDay, pYear, pMonth, pDay, 0, 0, 0, 0 );
    }

    public static UtilDateAdaptor currentWall( int pYear, int pMonth, int pDay )
    {
        return offsetWallToDay( calculateCurrentUTCtoWallOffsetMinutes(), pYear, pMonth, pDay );
    }

    public static UtilDateAdaptor zulu( int pYear, int pMonth, int pDay )
    {
        return offsetWallToDay( 0, pYear, pMonth, pDay );
    }

    public static UtilDateAdaptor offsetWallToMonth( int pOffsetMinutes, int pYear, int pMonth )
    {
        return new UtilDateAdaptor( pOffsetMinutes, TemporalResolution.ToMonth, pYear, pMonth, 0, 0, 0, 0, 0 );
    }

    public static UtilDateAdaptor currentWall( int pYear, int pMonth )
    {
        return offsetWallToMonth( calculateCurrentUTCtoWallOffsetMinutes(), pYear, pMonth );
    }

    public static UtilDateAdaptor zulu( int pYear, int pMonth )
    {
        return offsetWallToMonth( 0, pYear, pMonth );
    }

    public static UtilDateAdaptor offsetWallToYear( int pOffsetMinutes, int pYear )
    {
        return new UtilDateAdaptor( pOffsetMinutes, TemporalResolution.ToYear, pYear, 0, 0, 0, 0, 0, 0 );
    }

    public static UtilDateAdaptor currentWall( int pYear )
    {
        return offsetWallToYear( calculateCurrentUTCtoWallOffsetMinutes(), pYear );
    }

    public static UtilDateAdaptor zulu( int pYear )
    {
        return offsetWallToYear( 0, pYear );
    }

    public static UtilDateAdaptor fromUtilDateToString( String pDateToString )
    {
        return (pDateToString != null) ? new UtilDateAdaptor( Adaptor.parseUtilDate( pDateToString ), null ) : null;
    }

    public static UtilDateAdaptor fromUtilDateJustDateToString( String pDateToString )
    {
        return (pDateToString != null) ? new UtilDateAdaptor( Adaptor.parseUtilDate( pDateToString ), TemporalResolution.ToDay ) : null;
    }

    public TemporalResolution getTemporalResolution()
    {
        return TemporalResolution.values()[mAdaptor.getTemporalFields()];
    }

    public int getUTCtoWallOffsetMinutes()
    {
        return mAdaptor.getUTCtoWallOffsetMinutes();
    }

    @Override
    public int getZuluOffsetMins()
    {
        return getUTCtoWallOffsetMinutes();
    }

    @Override
    public int getYear()
    {
        return mAdaptor.getYear();
    }

    @Override
    public int getMonth()
    {
        return mAdaptor.getMonth();
    }

    @Override
    public int getDay()
    {
        return mAdaptor.getDay();
    }

    @Override
    public int getHour()
    {
        return mAdaptor.getHour();
    }

    @Override
    public int getMin()
    {
        return mAdaptor.getMin();
    }

    @Override
    public int getSec()
    {
        return mAdaptor.getSec();
    }

    @Override
    public int getMilliSec()
    {
        return mAdaptor.getMilliSec();
    }

    public long getUTClong()
    {
        return mAdaptor.getUTClong();
    }

    public Date getWallDate()
    {
        return new Date( getUTClong() );
    }

    public int dayOfWeek()
    {
        return Adaptor.dayOfWeek( getWallDate() );
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append( Integers.zeroPadIt( 4, getYear() ) );
        sb.append( getTemporalResolution() == TemporalResolution.ToYear ? '|' : '-' );
        sb.append( Integers.zeroPadIt( 2, getMonth() ) );
        sb.append( getTemporalResolution() == TemporalResolution.ToMonth ? '|' : '-' );
        sb.append( Integers.zeroPadIt( 2, getDay() ) );
        sb.append( getTemporalResolution() == TemporalResolution.ToDay ? '|' : 't' );
        sb.append( Integers.zeroPadIt( 2, getHour() ) );
        sb.append( getTemporalResolution() == TemporalResolution.ToHour ? '|' : ':' );
        sb.append( Integers.zeroPadIt( 2, getMin() ) );
        sb.append( getTemporalResolution() == TemporalResolution.ToMin ? '|' : ':' );
        sb.append( Integers.zeroPadIt( 2, getSec() ) );
        sb.append( getTemporalResolution() == TemporalResolution.ToSec ? '|' : '.' );
        sb.append( Integers.zeroPadIt( 3, getMilliSec() ) );
        int zOffsetMinutes = getUTCtoWallOffsetMinutes();
        if ( zOffsetMinutes == 0 )
        {
            sb.append( 'Z' );
        }
        else
        {
            if ( zOffsetMinutes > 0 )
            {
                sb.append( '+' );
            }
            else
            {
                sb.append( '-' );
                zOffsetMinutes = -zOffsetMinutes;
            }
            int zHours = zOffsetMinutes / 60;
            int zMins = zOffsetMinutes - (zHours * 60);
            sb.append( Integers.zeroPadIt( 2, zHours ) );
            if ( zMins != 0 )
            {
                sb.append( ':' ).append( Integers.zeroPadIt( 2, zMins ) );
            }
        }
        return sb.toString();
    }

    private final Adaptor mAdaptor;

    private UtilDateAdaptor( Date pWallDate, int pTemporalFields )
    {
        mAdaptor = new Adaptor( pWallDate, pTemporalFields );
    }

    private UtilDateAdaptor( long pUTC, TemporalResolution pTemporalResolution )
    {
        this( new Date( pUTC ), pTemporalResolution );
    }

    private UtilDateAdaptor( int pOffsetMinutes, TemporalResolution pTemporalResolution, int pYear, int pMonth, int pDay, int pHour, int pMin, int pSec,
                             int pMilliSec )
    {
        mAdaptor = new Adaptor( pOffsetMinutes, pTemporalResolution.ordinal(), pYear, pMonth, pDay, pHour, pMin, pSec, pMilliSec );
    }

    private static class Adaptor extends LL_UtilDateAdaptor
    {
        private Adaptor( int pUTCtoWallOffsetMinutes, int pTemporalFields, int pYear, int pMonth, int pDay, int pHour, int pMin, int pSec, int pMilliSec )
        {
            super( pUTCtoWallOffsetMinutes, pTemporalFields, pYear, pMonth, pDay, pHour, pMin, pSec, pMilliSec );
        }

        private Adaptor( Date pWallDate, int pTemporalFields )
        {
            super( pWallDate, pTemporalFields );
        }

        public int getUTCtoWallOffsetMinutes()
        {
            return mUTCtoWallOffsetMinutes;
        }

        public int getTemporalFields()
        {
            return mTemporalFields;
        }

        public int getYear()
        {
            return mYear;
        }

        public int getMonth()
        {
            return mMonth;
        }

        public int getDay()
        {
            return mDay;
        }

        public int getHour()
        {
            return mHour;
        }

        public int getMin()
        {
            return mMin;
        }

        public int getSec()
        {
            return mSec;
        }

        public int getMilliSec()
        {
            return mMilliSec;
        }

        public long getUTClong()
        {
            int zYear = mYear;
            int zMonth = mMonth;
            int zDay = mDay;
            int zHour = mHour;
            int zMin = mMin;
            if ( mUTCtoWallOffsetMinutes != 0 ) // then Adjust the time to UTC
            {
                zMin -= mUTCtoWallOffsetMinutes; // subtract the offset: to advance those to the west, and retard those to the east.
                while ( zMin < 0 )
                {
                    zMin += 60;
                    if ( --zHour < 0 )
                    {
                        zHour += 24;
                        if ( --zDay < 1 )
                        {
                            if ( --zMonth < 1 )
                            {
                                zMonth = 12;
                                zYear--;
                            }
                            zDay = Month.daysIn( zYear, zMonth );
                        }
                    }
                }
                while ( 60 <= zMin )
                {
                    zMin -= 60;
                    if ( 23 < ++zHour )
                    {
                        zHour -= 24;
                    }
                }
            }
            return UTC( zYear, zMonth, zDay, zHour, zMin, mSec, mMilliSec );
        }
    }
}
