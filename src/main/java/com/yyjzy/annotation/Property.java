package com.yyjzy.annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Property {
    String value() default "";
    String type() default "";
    String comment() default "";
    boolean isEmpty() default true;
}
