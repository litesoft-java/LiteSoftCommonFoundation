package org.litesoft.commonfoundation.exceptions;

public interface ExpectedExceptions {
    /**
     * Simple shallow check of the inner Set.
     * <p/>
     * This is normally only used with the result of convertExpected(Throwable);
     */
    boolean isExpected( String pThrowableClassName );

    /**
     * Check for the throwable to be in the Expected (both Supers and Causes).
     *
     * @return null if not found, otherwise the name of the class that matched
     */
    String convertExpected( Throwable pThrowable );
}
