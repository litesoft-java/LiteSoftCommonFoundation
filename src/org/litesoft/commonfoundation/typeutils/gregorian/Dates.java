// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils.gregorian;

import org.litesoft.commonfoundation.typeutils.proxies.*;

import java.util.*;

public class Dates {
    /**
     * Returns null if a valid date, or error text if not!
     */
    public static String checkValidity( int pYear, int pMonth, int pDay ) {
        Month zMonth = Month.fromMonthNumber( pMonth );
        if ( zMonth == null ) {
            return "Invalid month: " + pMonth;
        }
        if ( pDay < 1 ) {
            return "Invalid day: " + pDay;
        }
        int maxDaysInMonth = zMonth.daysIn( pYear );
        if ( maxDaysInMonth < pDay ) {
            return zMonth.name() + " " + pYear + " only has " + maxDaysInMonth + ", attempted to set days to: " + pDay;
        }
        return null;
    }

    /**
     * Returns a value that is the result of adding 1900 to the year
     * from the passed date (interpreted in the local time zone).
     *
     * @return the year represented by the date (0 based).
     */
    @SuppressWarnings("deprecation")
    public static int yearFrom( Date date ) {
        return date.getYear() + 1900;
    }

    /**
     * Returns a value that is the result of adding 1 to the month
     * from the passed date (interpreted in the local time zone).
     *
     * @return the month represented by the date (1-12).
     */
    @SuppressWarnings("deprecation")
    public static int monthOfYearFrom( Date date ) {
        return date.getMonth() + 1; // adjust 0-based to 1-based
    }

    /**
     * Returns the day of the month
     * from the passed date (interpreted in the local time zone).
     *
     * @return the day of the month represented by the date (1-31).
     */
    @SuppressWarnings("deprecation")
    public static int dayOfMonthFrom( Date date ) {
        return date.getDate();
    }

    /**
     * Returns the hour of the day
     * from the passed date (interpreted in the local time zone).
     *
     * @return the hour represented by the date (0-23).
     */
    @SuppressWarnings("deprecation")
    public static int hoursFrom( Date date ) {
        return date.getHours();
    }

    /**
     * Returns the number of minutes past the hour
     * from the passed date (interpreted in the local time zone).
     *
     * @return the number of minutes past the hour represented by the date (0-59).
     */
    @SuppressWarnings("deprecation")
    public static int minutesFrom( Date date ) {
        return date.getMinutes();
    }

    /**
     * Returns the number of seconds past the minute
     * from the passed date (interpreted in the local time zone).
     *
     * @return the number of seconds past the minute represented by the date (0-61, 60 & 61 for leap seconds).
     */
    @SuppressWarnings("deprecation")
    public static int secondsFrom( Date date ) {
        return date.getSeconds();
    }

    protected static class MutableDate {
        protected final IntegerProxy mYear, mMonth, mDay;

        protected MutableDate( int pYear, int pMonth, int pDay ) {
            mYear = new IntegerProxy( pYear );
            mMonth = new IntegerProxy( pMonth );
            mDay = new IntegerProxy( pDay );
        }

        public int getYear() {
            return mYear.getInt();
        }

        public int getMonth() {
            return mMonth.getInt();
        }

        public int getDay() {
            return mDay.getInt();
        }

        public void normalize() {
            constrain( 1, mMonth, 12, mYear );
            int zDay = mDay.getInt();
            for ( int zDaysInMonth; zDay > (zDaysInMonth = daysInMonth()); ) {
                zDay -= zDaysInMonth; // Adjust Day BEFORE adjusting month!
                constrain( 1, mMonth.inc(), 12, mYear );
            }
            while ( zDay < 1 ) {
                constrain( 1, mMonth.dec(), 12, mYear );
                zDay += daysInMonth(); // Adjust Day AFTER adjusting month!
            }
            mDay.setInt( zDay );
        }

        private int daysInMonth() {
            return Month.daysIn( getYear(), getMonth() );
        }

        protected void constrain( int pMinValue, IntegerProxy pToConstrain, int pMaxValue, IntegerProxy pParent ) {
            int zValue = pToConstrain.getInt();
            int zAdjustBy = (pMaxValue - pMinValue) + 1;
            for (; pMaxValue < zValue; pParent.inc() ) {
                zValue -= zAdjustBy;
            }
            for (; zValue < pMinValue; pParent.dec() ) {
                zValue += zAdjustBy;
            }
            pToConstrain.setInt( zValue );
        }
    }
}
