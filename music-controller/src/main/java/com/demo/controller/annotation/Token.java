package com.demo.controller.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Token {

    /**
     * 当前用户在request中的名字
     *
     * @return
     */
    String value() default "user";
}
