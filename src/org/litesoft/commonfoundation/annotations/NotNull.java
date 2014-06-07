// This Source Code is in the Public Domain per: http://unlicense.org
package org.litesoft.commonfoundation.annotations;

@java.lang.annotation.Documented
@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER})
public @interface NotNull {

    java.lang.String value() default "";
}