package org.litesoft.commonfoundation.typeutils;

import java.util.*;

public class Sets
{
    @SuppressWarnings("Convert2Diamond")
    public static <T> HashSet<T> newHashSet()
    {
        return new HashSet<T>();
    }

    public static <T> Set<T> empty()
    {
        return Collections.emptySet();
    }

    public static <T> Set<T> deNull( Set<T> pToCheck )
    {
        if ( pToCheck == null )
        {
            pToCheck = empty();
        }
        return pToCheck;
    }

    public static boolean isNullOrEmpty( Set<?> pToCheck )
    {
        return (pToCheck == null || pToCheck.isEmpty());
    }

    public static boolean isNotNullOrEmpty( Set<?> pToCheck )
    {
        return (pToCheck != null && !pToCheck.isEmpty());
    }

    public static void assertNotNullNotEmpty( String pErrorMessage, Set<?> pToAssert )
            throws IllegalArgumentException
    {
        if ( isNullOrEmpty( pToAssert ) )
        {
            Strings.errorNullOrEmpty( pErrorMessage, "Collection" );
        }
    }
}
