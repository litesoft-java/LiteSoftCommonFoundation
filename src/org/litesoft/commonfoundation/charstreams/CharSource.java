// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.charstreams;

/**
 * A CharSource is like a powerful version of a "char" based Iterator.
 */
public interface CharSource {
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

    /**
     * Return a string (and consume the characters) from the current position up to (but not including) the position of the 'c' character.  OR "" if 'c' is not found (nothing consumed).
     */
    public String getUpTo( char c );

    /**
     * Consume all the spaces (NOT white space) until either there are no more characters or a non space is encountered (NOT consumed).
     *
     * @return true if there are more characters.
     */
    public boolean consumeSpaces();

    /**
     * Return a string (and consume the characters) from the current position thru the end of the characters OR up to (but not including) a character that is not a visible 7-bit ascii character (' ' < c <= 126).
     */
    public String getUpToNonVisible7BitAscii();

    /**
     * Consume all the non-visible 7-bit ascii characters (visible c == ' ' < c <= 126) until either there are no more characters or a visible 7-bit ascii character is encountered (NOT consumed).
     *
     * @return true if there are more characters.
     */
    public boolean consumeNonVisible7BitAscii();
}
