package cn.qhb.haiv.core.annotation;

import java.lang.annotation.*;

/**
 * 忽略字段或方法
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transition {
}
