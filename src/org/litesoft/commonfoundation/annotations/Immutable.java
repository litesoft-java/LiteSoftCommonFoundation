package org.litesoft.commonfoundation.annotations;

@java.lang.annotation.Documented
@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER})
public @interface Immutable
{
    String value() default "";
}
