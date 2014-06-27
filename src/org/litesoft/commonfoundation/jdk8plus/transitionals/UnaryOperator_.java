package org.litesoft.commonfoundation.jdk8plus.transitionals;

import org.litesoft.commonfoundation.base.*;

import java8.util.function.*;

public class UnaryOperator_ {
    public static <T> UnaryOperator<T> identity() {
        return Cast.it( IDENTITY );
    }

    private static final UnaryOperator<?> IDENTITY = new UnaryOperator<Object>() {
        @Override
        public Object apply( Object t ) {
            return t;
        }
    };
}
