package com.sber.annotation;

import com.sber.entity.CacheType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {

    String fileNamePrefix() default "";

    CacheType type() default CacheType.IN_MEMORY;

    boolean isZip() default false;

    long limit() default -1L;

    Class<?>[] identityBy() default List.class;
}
