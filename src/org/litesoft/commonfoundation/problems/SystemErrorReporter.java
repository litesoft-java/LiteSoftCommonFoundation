package org.litesoft.commonfoundation.problems;

/**
 * An Interface to unify the way we expose unexpected errors.
 *
 * @author georgs
 */
public interface SystemErrorReporter {
    /**
     * Report a System Error to the User and possibly log it to the server.
     * <p/>
     * The source can be used to bundle the exceptions.  e.g. 5 exceptions thrown from the same source should be shown as a group!
     * <p/>
     * Note: 'context' or 'throwable' will NOT be null!
     *
     * @param source    - The "source" of the Error (!nullable)
     * @param context   - What ever context the developer thought would add value
     *                  (nullable)
     * @param throwable - The actual exception that generated the call to this method
     *                  (!nullable)
     */
    void reportSystemError( String source, String context, Throwable throwable );
}
