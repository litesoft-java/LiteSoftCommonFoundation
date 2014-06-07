// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.typeutils.gregorian;

public enum TemporalResolution {
    ToYear,
    ToMonth,
    ToDay,
    ToHour,
    ToMin,
    ToSec,
    ToMilliSec() {
        @Override
        public TemporalResolution deeper() {
            return this;
        }
    };

    public int getTimeFieldsCount() {
        return Math.max( 0, this.ordinal() - ToDay.ordinal() );
    }

    public TemporalResolution plus( int pOrdinals ) {
        return (pOrdinals <= 0) ? this : deeper().plus( pOrdinals - 1 );
    }

    public TemporalResolution deeper() {
        return values()[ordinal() + 1];
    }

    public static TemporalResolution deNull( TemporalResolution pTemporalResolution ) {
        return (pTemporalResolution != null) ? pTemporalResolution : ToMilliSec;
    }
}
