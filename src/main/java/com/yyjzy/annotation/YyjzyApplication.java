package com.yyjzy.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface YyjzyApplication {
        String value() default "yyjzy";
}
