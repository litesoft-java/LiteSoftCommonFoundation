// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.exceptions;

public class FileSystemException extends PersistenceException
{
    public FileSystemException( String message )
    {
        super( message );
    }

    public FileSystemException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public FileSystemException( Throwable cause )
    {
        super( cause );
    }
}
