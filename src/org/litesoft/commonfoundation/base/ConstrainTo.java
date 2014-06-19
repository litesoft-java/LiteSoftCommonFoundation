package org.litesoft.commonfoundation.base;

import org.litesoft.commonfoundation.annotations.*;

import java.util.*;
import java.util.function.*;

@SuppressWarnings({"unchecked", "UnusedDeclaration"})
public class ConstrainTo {

    public static final String DEFAULT_STRING = "";
    public static final long DEFAULT_LONG = 0;
    public static final int DEFAULT_INTEGER = 0;
    public static final float DEFAULT_FLOAT = (float) 0;
    public static final double DEFAULT_DOUBLE = 0.0;
    public static final boolean DEFAULT_BOOLEAN = false;

    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static String significantOrNullOfToStringOf( Object pSource ) {
        return significantOrNull( (pSource != null) ? pSource.toString() : null );
    }

    public static String significantOrNull( String pString ) {
        return significantOrNull( pString, null );
    }

    public static String significantOrNull( String pString, String pDefault ) {
        if ( pString != null ) {
            pString = pString.trim();
            if ( pString.length() != 0 ) {
                return pString;
            }
        }
        return pDefault;
    }

    public static @Nullable <T> T firstNonNull( T pToCheck, T p1stFallBack, T... pAdditionalFallBacks ) {
        if ( pToCheck != null ) {
            return pToCheck;
        }
        if ( p1stFallBack != null ) {
            return p1stFallBack;
        }
        if ( pAdditionalFallBacks != null ) {
            for ( T zFallBack : pAdditionalFallBacks ) {
                if ( zFallBack != null ) {
                    return zFallBack;
                }
            }
        }
        return null;
    }

    public static @Nullable <T> T firstNonNull( T pToCheck, T pDefault ) {
        if ( pToCheck != null ) {
            return pToCheck;
        }
        return pDefault;
    }

    public static @NotNull <T> T notNull( T pToCheck, @NotNull T pDefault ) {
        if ( pToCheck != null ) {
            return pToCheck;
        }
        return IllegalArgument.ifNull( "notNull Default", pDefault );
    }

    public static @NotNull <T> T notNull( T pToCheck, @NotNull Supplier<T> pDefault ) {
        if ( pToCheck != null ) {
            return pToCheck;
        }
        T zDefault = IllegalArgument.ifNull( "notNull Default", pDefault ).get();
        return IllegalArgument.ifNull( "notNull Supplier get()", zDefault );
    }

    public static <T> List<T> notNullImmutableList( T... pArray ) {
        if ( (pArray == null) || (pArray.length == 0) ) {
            return Collections.emptyList();
        }
        return Arrays.asList( pArray );
    }

    // Common Type Specific NotNulls w/ "default" defaults!

    public static String notNull( CharSequence pValue ) {
        return (pValue != null) ? pValue.toString() : DEFAULT_STRING;
    }

    public static int notNull( Integer pValue ) {
        return (pValue != null) ? pValue : DEFAULT_INTEGER;
    }

    public static long notNull( Long pValue ) {
        return (pValue != null) ? pValue : DEFAULT_LONG;
    }

    public static boolean notNull( Boolean pValue ) {
        return (pValue != null) ? pValue : DEFAULT_BOOLEAN;
    }

    public static double notNull( Double pValue ) {
        return (pValue != null) ? pValue : DEFAULT_DOUBLE;
    }

    public static float notNull( Float pValue ) {
        return (pValue != null) ? pValue : DEFAULT_FLOAT;
    }

    public static String[] notNull( String[] pStrings ) { // TODO: Convert to notNullImmutableList
        return (pStrings != null) ? pStrings : EMPTY_STRING_ARRAY;
    }

    public static <T> List<T> notNull( List<T> pToCheck ) {
        if ( pToCheck == null ) {
            pToCheck = Collections.emptyList();
        }
        return pToCheck;
    }

    public static <T> Set<T> notNull( Set<T> pToCheck ) {
        if ( pToCheck == null ) {
            pToCheck = Collections.emptySet();
        }
        return pToCheck;
    }

    public static <K, V> Map<K, V> notNull( Map<K, V> pToCheck ) {
        if ( pToCheck == null ) {
            pToCheck = Collections.emptyMap();
        }
        return pToCheck;
    }
}
