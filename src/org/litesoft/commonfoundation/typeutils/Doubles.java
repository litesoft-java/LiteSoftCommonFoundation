package org.litesoft.commonfoundation.typeutils;

import org.litesoft.commonfoundation.base.*;
import org.litesoft.commonfoundation.typeutils.proxies.*;

public abstract class Doubles {
    public static final double ZERO = 0.0;
    public static final Double OBJECT_ZERO = ZERO;
    public static final double[] PRIMITIVE_EMPTY_ARRAY = new double[0];
    public static final Double[] EMPTY_ARRAY = new Double[0];
    public static final TypeTransformer<Double> TYPE_TRANSFORMER = new TypeTransformer<Double>() {
        @Override
        public Double transformNonNull( Object pObject ) {
            return Doubles.toDouble( pObject );
        }
    };

    public static Double firstNotNull( Double pValue1, Double pValue2 ) {
        return (pValue1 != null) ? pValue1 : pValue2;
    }

    public static Double toDouble( Object pObject ) {
        if ( pObject instanceof Double ) {
            return Cast.it( pObject );
        }
        if ( pObject instanceof Number ) {
            return ((Number) pObject).doubleValue();
        }
        if ( pObject != null ) {
            String zString = ConstrainTo.significantOrNull( pObject.toString() );
            if ( zString != null ) {
                try {
                    return Double.valueOf( zString );
                }
                catch ( NumberFormatException e ) {
                    // Fall Thru
                }
            }
        }
        return null;
    }

    public static double roundUpToThe100th( double pValue ) {
        return roundUpToThe( pValue, 100.0 );
    }

    public static double roundUpToThe( double pValue, double pRoundUpFactor ) {
        return roundUp( pValue * pRoundUpFactor ) / pRoundUpFactor;
    }

    public static double roundUp( double value ) {
        return Math.floor( value + 0.99 );
    }

    public static Double sum( Double... pAmounts ) {
        DoublesSum zSum = new DoublesSum();
        if ( pAmounts != null ) {
            for ( Double zAmount : pAmounts ) {
                zSum.add( zAmount );
            }
        }
        return zSum.getSum();
    }

    public static boolean allZero( double... pAmounts ) {
        if ( pAmounts != null ) {
            for ( double zAmount : pAmounts ) {
                if ( zAmount != 0 ) {
                    return false;
                }
            }
        }
        return true;
    }

    abstract public String format( double pValue );

    public static Doubles getFormat( String pPattern ) {
        return Factory.sInstance.getFormat( pPattern );
    }

    public static abstract class Factory {
        private static Factory sInstance;

        protected Factory() {
            sInstance = this;
        }

        abstract public Doubles getFormat( String pPattern );
    }
}
