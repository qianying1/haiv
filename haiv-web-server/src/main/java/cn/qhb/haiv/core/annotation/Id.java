package cn.qhb.haiv.core.annotation;

import java.lang.annotation.*;

/**
 * 唯一键id
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Id {
    String name() default "id";
}
