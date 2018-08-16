package cn.qhb.haiv.core.annotation;

import java.lang.annotation.*;

/**
 * 表注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Table {

    String value() default "";
}
