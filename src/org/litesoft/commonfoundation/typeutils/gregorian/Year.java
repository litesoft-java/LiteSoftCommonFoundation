// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils.gregorian;

public class Year {
    /**
     * True if pYear is thought to be a Leap Year (based on the rules that apply from 1800 thru 2100 in the "Gregorian" calendar).
     * <p/>
     * Note: if pYear is before 1800 or after 2010 or are not using a "Gregorian" calendar, then you may get incorrect results!
     * <p/>
     * Rules for 1800 - 2010:
     * <ul>
     * 1) Every 4 years is a Leap Year, except
     * 2) Every 100 years is NOT a Leap Year, except
     * 3) Every 400 years is a Leap year.
     * <ul/>
     * Hence:
     * The following are Leap Years: 1800, 1804, 1896, 1900, 1904, 1996, 2004, 2096, 2100, 2104
     * The following are NOT Leap Years: 1801, 1903, 1998, 2000, 2002
     */
    public static boolean isLeap( int pYear ) {
        if ( 0 != (pYear & 3) ) // Every 4
        {
            return false;
        }
        //noinspection SimplifiableIfStatement
        if ( 0 != (pYear % 100) ) // ! Every 100
        {
            return true;
        }
        return (0 == (pYear % 400)); // Every 400
    }
}
