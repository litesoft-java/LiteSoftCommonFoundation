// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.charstreams;

/**
 * A CharSink is a stand in for either a StringBuilder or an java.io.Writer.
 */
public interface CharSink
{
    /**
     * Add a character.
     */
    public void add( char pChar );
}