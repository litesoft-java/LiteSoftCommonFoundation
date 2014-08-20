package org.litesoft.commonfoundation.typeutils.gregorian;

public enum TimePeriod {
    MONTH, //
    WEEK, //
    DAY, //
    HOUR, //
    MIN, //
    SEC;

    public boolean has( TimePeriod them ) {
        return (them != null) && (this.ordinal() <= them.ordinal());
    }
}
