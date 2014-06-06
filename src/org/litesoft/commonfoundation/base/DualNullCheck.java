// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.base;

public enum DualNullCheck
{
    NeitherNull, //
    Only1stNull, //
    Only2ndNull, //
    BothNull;

    public static DualNullCheck of( Object p1st, Object p2nd )
    {
        return (p1st == null) ? //
               ((p2nd == null) ? BothNull : Only1stNull) : //
               ((p2nd == null) ? Only2ndNull : NeitherNull);
    }
}
