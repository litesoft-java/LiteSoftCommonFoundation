package org.litesoft.commonfoundation.typeutils.gregorian;

public interface TimestampAccessorH extends CalendarAccessorYMD,
                                            TimeAccessorH
{
    int getZuluOffsetMins();
}
