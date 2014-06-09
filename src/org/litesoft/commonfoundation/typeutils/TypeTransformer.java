package org.litesoft.commonfoundation.typeutils;

public interface TypeTransformer<TargetType> {
    /**
     * Transform a Non-Null object to the appropriate TargetType or null if there is a problem (should NOT throw).
     *
     * @param pObject !null
     *
     * @return null if error, otherwise an instance of TargetType.
     */
    TargetType transformNonNull( Object pObject );
}
