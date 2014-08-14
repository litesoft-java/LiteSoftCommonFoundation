package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;

public class AbstractBuilder {
    protected static <T> T assertSetOnce( T pExistingValue, String pWhat, T pNewValue ) {
        if (pExistingValue != null) {
            throw new IllegalStateException( "May not set '" + pWhat + "' again!" );
        }
        Confirm.isNotNull( pWhat, pNewValue );
        return pNewValue;
    }
}
