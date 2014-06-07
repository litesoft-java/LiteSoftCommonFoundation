// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils.gregorian;

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
}
