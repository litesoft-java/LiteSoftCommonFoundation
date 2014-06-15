package org.litesoft.commonfoundation.base;

public class Currently {
    @SuppressWarnings("StringEquality")
    public static boolean areEqualIgnoreCase( String pStr1, String pStr2 ) {
        return (pStr1 == pStr2) || // Same String or both null
               ((pStr1 != null) && pStr1.equalsIgnoreCase( pStr2 )) ||
               ((pStr2 != null) && pStr2.equalsIgnoreCase( pStr1 ));
    }

}
