package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;

public class Floats {
    public static final float ZERO = 0;
    public static final Float OBJECT_ZERO = ZERO;
    public static final float[] PRIMITIVE_EMPTY_ARRAY = new float[0];
    public static final Float[] EMPTY_ARRAY = new Float[0];
    public static final TypeTransformer<Float> TYPE_TRANSFORMER = new TypeTransformer<Float>() {
        @Override
        public Float transformNonNull( Object pObject ) {
            return Floats.toFloat( pObject );
        }
    };

    public static float deNull( Float pFloat ) {
        return (pFloat != null) ? pFloat : ZERO;
    }

    public static float[] deNull( float[] pFloats ) {
        return (pFloats != null) ? pFloats : PRIMITIVE_EMPTY_ARRAY;
    }

    public static Float[] deNull( Float[] pFloats ) {
        return (pFloats != null) ? pFloats : EMPTY_ARRAY;
    }

    public static Float toFloat( Object pObject ) {
        if ( pObject instanceof Float ) {
            return Cast.it( pObject );
        }
        if ( pObject instanceof Number ) {
            return ((Number) pObject).floatValue();
        }
        if ( pObject != null ) {
            String zString = Strings.noEmpty( pObject.toString() );
            if ( zString != null ) {
                try {
                    return Float.valueOf( zString );
                }
                catch ( NumberFormatException e ) {
                    // Fall Thru
                }
            }
        }
        return null;
    }
}
