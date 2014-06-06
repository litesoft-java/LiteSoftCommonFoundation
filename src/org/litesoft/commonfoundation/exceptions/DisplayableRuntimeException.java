// This Source Code is in the Public Domain per: http://litesoft.org/License.txt
package org.litesoft.commonfoundation.exceptions;

import org.litesoft.commonfoundation.typeutils.*;

import java.util.*;

public class DisplayableRuntimeException extends RuntimeException implements DisplayableException
{
    public static final long serialVersionUID = -2L;

    private String mToResolveIdentifier;
    private String[] mParams;

    public DisplayableRuntimeException( Throwable pCause, String pToResolveIdentifier, String... pParams )
    {
        super( "Unresolved: " + pToResolveIdentifier );

        mToResolveIdentifier = Strings.assertNotNullNotEmpty( "ToResolveIdentifier", pToResolveIdentifier );
        mParams = Strings.deNull( pParams );

        if ( pCause != null )
        {
            initCause( pCause );
        }
    }

    public DisplayableRuntimeException( Throwable pCause, String pToResolveIdentifier )
    {
        this( pCause, pToResolveIdentifier, Strings.EMPTY_ARRAY );
    }

    public DisplayableRuntimeException( String pToResolveIdentifier, String... pParams )
    {
        this( null, pToResolveIdentifier, pParams );
    }

    public DisplayableRuntimeException( String pToResolveIdentifier )
    {
        this( null, pToResolveIdentifier, Strings.EMPTY_ARRAY );
    }

    @Override
    public String getToResolveIdentifier()
    {
        return mToResolveIdentifier;
    }

    @Override
    public String[] getParams()
    {
        return mParams;
    }

    @Override
    public String getMessage()
    {
        String plus = (mParams.length == 0) ? "" : Arrays.asList( getParams() ).toString();
        return super.getMessage() + plus;
    }
}
