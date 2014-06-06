// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.charstreams;

/**
 * A CharSource is like a powerful version of a "char" based Iterator.
 */
public interface CharSource
{
    /**
     * Report if there are any more characters available to get().
     * <p/>
     * Similar to Iterator's hasNext().
     */
    public boolean anyRemaining();

    /**
     * Get the next character (consume it from the stream) or -1 if there are no more characters available.
     */
    public int get();

    /**
     * Get the next character (consume it from the stream) or throw an exception if there are no more characters available.
     */
    public char getRequired();

    /**
     * Return the next character (without consuming it) or -1 if there are no more characters available.
     */
    public int peek();

    /**
     * Return the Next Offset (from the stream) that the peek/get/getRequired would read from (it may be beyond the stream end).
     */
    public int getNextOffset();

    /**
     * Return the Last Offset (from the stream), which the previous get/getRequired read from (it may be -1 if stream has not been successfully read from).
     */
    public int getLastOffset();
}