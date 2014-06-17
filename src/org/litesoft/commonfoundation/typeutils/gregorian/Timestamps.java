// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils.gregorian;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.exceptions.*;
import org.litesoft.commonfoundation.typeutils.*;
import org.litesoft.commonfoundation.typeutils.proxies.*;

import java.sql.*;
import java.text.*;
import java.util.Date;

public class Timestamps extends Dates {
    public static Timestamp now() {
        return new Timestamp( System.currentTimeMillis() );
    }

    public static String nowToYMDHMS4FileName() {
        return new SimpleDateFormat( "yyyyMMdd-HHmmss" ).format( now() );
    }

    public static String nowToYMDHMS() {
        return new SimpleDateFormat( "yyyy/MM/dd HH:mm:ss" ).format( now() );
    }

    public enum Default {
        Now {
            protected long getAppropriateLong( long pMillisSinceEpoch ) {
                return pMillisSinceEpoch;
            }
        },
        BOD( 0 ),
        Noon( 12 ),
        EOD {
            protected long getAppropriateLong( long pMillisSinceEpoch ) {
                return getTomorrow( pMillisSinceEpoch );
            }
        };

        public static Default deNull( Default pDefault ) {
            return (pDefault != null) ? pDefault : Now;
        }

        private static Long sTomorrow;
        private final int mDesiredHourOfToday;
        private Long mTomorrow, mAppropriateLongForToday;

        private Default( int pDesiredHourOfToday ) {
            mDesiredHourOfToday = pDesiredHourOfToday;
        }

        private Default() {
            this( -1 );
        }

        public final Timestamp get() {
            return new Timestamp( getAppropriateLong( System.currentTimeMillis() ) );
        }

        protected synchronized long getAppropriateLong( long pMillisSinceEpoch ) {
            if ( (mAppropriateLongForToday == null) || (mTomorrow == null) || (mTomorrow <= pMillisSinceEpoch) ) {
                mTomorrow = getTomorrow( pMillisSinceEpoch );
                Timestamp zTimestamp = new Timestamp( pMillisSinceEpoch );
                int zYear = yearFrom( zTimestamp );
                int zMonth = monthOfYearFrom( zTimestamp );
                int zDay = dayOfMonthFrom( zTimestamp );
                mAppropriateLongForToday = from( zYear, zMonth, zDay, mDesiredHourOfToday ).getTime();
            }
            return mAppropriateLongForToday;
        }

        private static synchronized long getTomorrow( long pMillisSinceEpoch ) {
            if ( (sTomorrow == null) || (sTomorrow <= pMillisSinceEpoch) ) {
                Timestamp zTimestamp = new Timestamp( pMillisSinceEpoch );
                int zYear = yearFrom( zTimestamp );
                int zMonth = monthOfYearFrom( zTimestamp );
                int zDay = dayOfMonthFrom( zTimestamp ) + 1;
                if ( zDay > Month.daysIn( zYear, zMonth ) ) {
                    zDay = 1;
                    if ( ++zMonth > 12 ) {
                        zMonth = 1;
                        zYear++;
                    }
                }
                sTomorrow = from( zYear, zMonth, zDay, 0 ).getTime();
            }
            return sTomorrow;
        }
    }

    public static final Timestamp[] EMPTY_ARRAY = new Timestamp[0];
    public static final TypeTransformer<Timestamp> TYPE_TRANSFORMER = new TypeTransformer<Timestamp>() {
        @Override
        public Timestamp transformNonNull( Object pObject ) {
            return Timestamps.toTimestamp( pObject );
        }
    };

    public static Timestamp[] deNull( Timestamp[] pTimestamps ) {
        return (pTimestamps != null) ? pTimestamps : EMPTY_ARRAY;
    }

    public static Timestamp toTimestamp( Object pObject ) {
        if ( pObject instanceof Timestamp ) {
            return Cast.it( pObject );
        }
        if ( pObject instanceof Date ) {
            return new Timestamp( ((Date) pObject).getTime() );
        }
        if ( pObject != null ) {
            String zString = ConstrainTo.significantOrNull( pObject.toString() );
            if ( zString != null ) {
                try {
                    return zString.endsWith( "Z" ) ? fromISO8601ZuluSimple( zString ) : Timestamp.valueOf( zString );
                }
                catch ( IllegalArgumentException e ) {
                    // Fall Thru
                }
            }
        }
        return null;
    }

    /**
     * A Simple ISO 8601 Zulu timestamp is defined as a Java SQL Timestamp w/ the ' ' replaced with a 'T' and a 'Z' appended.
     */
    public static String toISO8601ZuluSimple( Timestamp pValue ) {
        if ( pValue == null ) {
            return null;
        }
        return toDisplayString( pValue ).replace( ' ', 'T' ) + "Z";
    }

    /**
     * A Simple ISO 8601 Zulu timestamp is defined as a Java SQL Timestamp w/ the ' ' replaced with a 'T' and a 'Z' appended.
     * <p/>
     * Note: The Zulu-ness is not passed to the Timestamp.valueOf() - which means that it will technically be in local time, but be showing the Zulu time
     * <p/>
     * 123456789012345678901234
     * yyyy-mm-ddThh:mm:ss.fffZ
     * 012345678901234567890123
     * yyyy-mm-dd hh:mm:ss.fffZ
     */
    public static Timestamp fromISO8601ZuluSimple( String pValue )
            throws TimestampFormatException {
        if ( null == (pValue = ConstrainTo.significantOrNull( pValue )) ) {
            return null;
        }
        if ( !pValue.contains( " " ) && pValue.contains( "T" ) && pValue.endsWith( "Z" ) && (14 <= pValue.length()) ) {
            String[] zParts = Strings.parseChar( pValue.substring( 0, pValue.length() - 1 ), 'T' );
            if ( (zParts.length == 2) && (8 <= zParts[0].length()) && (2 <= zParts[1].length()) ) {
                try {
                    return Timestamp.valueOf( zParts[0] + " " + zParts[1] );
                }
                catch ( IllegalArgumentException e ) {
                    throw new TimestampFormatException( pValue, e );
                }
            }
        }
        throw new TimestampFormatException( pValue );
    }

    public static Timestamp daysFromNow( int pDays ) {
        return addDays( Default.EOD.get(), pDays );
    }

    public static Timestamp from( int year0based, int month1based, int day1based, int hour ) {
        return from( year0based, month1based, day1based, hour, 0, 0, 0 );
    }

    public static Timestamp from( int year0based, int month1based, int day1based, int hour, int minute ) {
        return from( year0based, month1based, day1based, hour, minute, 0, 0 );
    }

    @SuppressWarnings("deprecation")
    public static Timestamp from( int year0based, int month1based, int day1based,
                                  int hour, int minute, int second, int nano ) {
        return new Timestamp( year0based - 1900, month1based - 1, day1based, hour, minute, second, nano );
    }

    @SuppressWarnings("deprecation")
    public static int getYear( Timestamp pTimestamp ) {
        return pTimestamp.getYear() + 1900;
    }

    @SuppressWarnings("deprecation")
    public static int getMonth( Timestamp pTimestamp ) {
        return pTimestamp.getMonth() + 1;
    }

    @SuppressWarnings("deprecation")
    public static int getDay( Timestamp pTimestamp ) {
        return pTimestamp.getDate();
    }

    @SuppressWarnings("deprecation")
    public static int getHours( Timestamp pTimestamp ) {
        return pTimestamp.getHours();
    }

    @SuppressWarnings("deprecation")
    public static int getMinutes( Timestamp pTimestamp ) {
        return pTimestamp.getMinutes();
    }

    @SuppressWarnings("deprecation")
    public static int getSeconds( Timestamp pTimestamp ) {
        return pTimestamp.getSeconds();
    }

    public static int getNanos( Timestamp pTimestamp ) {
        return pTimestamp.getNanos();
    }

    public static String toDisplayString( Timestamp pTimestamp ) {
        if ( pTimestamp == null ) {
            return "";
        }
        // 123456789012345678901234
        // yyyy-mm-dd hh:mm:ss.fff...
        // 012345678901234567890123
        String s = pTimestamp.toString();
        if ( s.length() > 23 ) {
            s = s.substring( 0, 23 );
        }
        while ( s.endsWith( "0" ) && !s.endsWith( ".0" ) ) {
            s = s.substring( 0, s.length() - 1 );
        }
        return s;
    }

    public static String createVersionFromNow() { // . . . . . . . . . . . . . . . .01234567890123456
        String zTS = toDisplayString( new Timestamp( System.currentTimeMillis() ) ); // yyyy-mm-dd hh:mm:ss.fff...
        return zTS.substring( 0, 4 ) // Year
               + zTS.substring( 5, 7 ) // Month
               + zTS.substring( 8, 10 ) // Day
               + Characters.toLowercaseAlphaBase26( Integer.parseInt( zTS.substring( 11, 13 ) ) ) // Hours
               + Characters.toBase36( Integer.parseInt( zTS.substring( 14, 16 ) ) / 2 ) // Minutes
                ;
    }

    public static String validateVersion( String pVersion ) {
        pVersion = ConstrainTo.significantOrNull( pVersion, "" );
        try {
            verifyVersionsDate(
                    ssInt( pVersion, 0, 4 ), // Year
                    ssInt( pVersion, 4, 6 ), // Month
                    ssInt( pVersion, 6, 8 ), // Day
                    hoursFromLowercaseAlphaBase26( pVersion, 8 ), // Hours
                    minutesFromBase36( pVersion, 9 ) * 2 ); // Minutes
            return pVersion;
        }
        catch ( Exception e ) {
            throw new IllegalArgumentException( "'" + pVersion + "' does not appear to be a valid Version 'vs' (yyyyMMdd/yyyyMMddaX)", e );
        }
    }

    private static int hoursFromLowercaseAlphaBase26( String pVersion, int pAt ) {
        return (pVersion.length() <= pAt) ? 0 :
               checkFrom( "Hours", Characters.fromLowercaseAlphaBase26( pVersion.charAt( pAt ) ) );
    }

    private static int minutesFromBase36( String pVersion, int pAt ) {
        if ( pVersion.length() == 8 ) {
            return 0;
        }
        if ( pVersion.length() != (pAt + 1) ) {
            throw new IllegalArgumentException( "Invalid Length" );
        }
        return checkFrom( "Minutes", Characters.fromBase36( pVersion.charAt( pAt ) ) );
    }

    private static int checkFrom( String pWhat, int pAt ) {
        if ( pAt == -1 ) {
            throw new IllegalArgumentException( "Invalid " + pWhat + " indicated" );
        }
        return pAt;
    }

    private static int ssInt( String pVersion, int pFrom, int pUpTo ) {
        return Integer.parseInt( pVersion.substring( pFrom, pUpTo ) );
    }

    private static void verifyVersionsDate( int pYear, int pMonth, int pDay, int pHours, int pMinutes ) {
        Timestamp zTimestamp = from( pYear, pMonth, pDay, pHours, pMinutes );
        assertEqualsVersionTimestampPartRT( "Year", pYear, getYear( zTimestamp ) );
        assertEqualsVersionTimestampPartRT( "Month", pMonth, getMonth( zTimestamp ) );
        assertEqualsVersionTimestampPartRT( "Day", pDay, getDay( zTimestamp ) );
        assertEqualsVersionTimestampPartRT( "Hours", pHours, getHours( zTimestamp ) );
        assertEqualsVersionTimestampPartRT( "Minutes", pMinutes, getMinutes( zTimestamp ) );
    }

    private static void assertEqualsVersionTimestampPartRT( String pWhat, int pExpected, int pActual ) {
        Confirm.isEqual( "Version's " + pWhat + " did not round-trip.  ", pActual, pExpected );
    }

    public static Timestamp addDays( Timestamp pFrom, int pDays ) {
        Confirm.isNotNull( "From", pFrom );
        Integers.assertPositive( "Days", pDays );
        return normalize( getYear( pFrom ), getMonth( pFrom ), getDay( pFrom ) + pDays,
                          getHours( pFrom ), getMinutes( pFrom ), getSeconds( pFrom ), getNanos( pFrom ) );
    }

    private static Timestamp normalize( int year0based, int month1based, int day1based,
                                        int hour, int minute, int second, int nano ) {
        MutableTimestamp zMTS = new MutableTimestamp( year0based, month1based, day1based, hour, minute, second, nano );
        zMTS.normalize();
        return from( zMTS.getYear(), zMTS.getMonth(), zMTS.getDay(),
                     zMTS.getHour(), zMTS.getMinute(), zMTS.getSecond(), zMTS.getNano() );
    }

    protected static class MutableTimestamp extends MutableDate {
        protected final IntegerProxy mHour, mMinute, mSecond, mNano;

        protected MutableTimestamp( int pYear, int pMonth, int pDay, int pHour, int pMinute, int pSecond, int pNano ) {
            super( pYear, pMonth, pDay );
            mHour = new IntegerProxy( pHour );
            mMinute = new IntegerProxy( pMinute );
            mSecond = new IntegerProxy( pSecond );
            mNano = new IntegerProxy( pNano );
        }

        public int getHour() {
            return mHour.getInt();
        }

        public int getMinute() {
            return mMinute.getInt();
        }

        public int getSecond() {
            return mSecond.getInt();
        }

        public int getNano() {
            return mNano.getInt();
        }

        public void normalize() {
            constrain( 0, mNano, 999999999, mSecond );
            constrain( 0, mSecond, 59, mMinute );
            constrain( 0, mMinute, 59, mHour );
            constrain( 0, mHour, 23, mDay );
            super.normalize();
        }
    }
}
