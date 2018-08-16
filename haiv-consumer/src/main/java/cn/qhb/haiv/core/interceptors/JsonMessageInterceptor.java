package cn.qhb.haiv.core.interceptors;

import cn.qhb.haiv.core.util.JsonMessage;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component(value = "jsonMessageInterceptor")
@Aspect
public class JsonMessageInterceptor {

    @Pointcut("execution (* cn.qhb.haiv.core.util.JsonMessage.*(..))")
    public void jsonMessageAspect() {
    }

    @Before("jsonMessageAspect()")
    public void doBefore(JoinPoint joinPoint) {
        JsonMessage.resetJsonResult();
    }
}
