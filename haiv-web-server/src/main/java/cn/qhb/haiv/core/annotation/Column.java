package cn.qhb.haiv.core.annotation;

import java.lang.annotation.*;

/**
 * 字段注解
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {

    String name() default "";
}
