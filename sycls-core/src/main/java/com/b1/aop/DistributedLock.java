package com.b1.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    /**
     * Lock name
     */
    String key();

    /**
     * Lock target
     */
    String target();

    /**
     * Lock unit time
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * Lock wait time
     */
    long waitTime() default 0L;

    /**
     * Lock time
     */
    long leaseTime() default 3L;

}
