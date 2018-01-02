package com.qsd.framework.commons.aop;

import java.lang.annotation.*;

/**
 * Created by zhengyu
 * 用于标记对外暴露的api service的方法定义
 * 主要用于serviceAspect，记录api操作参数，耗时等日志
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface ApiAnnotation {

    /**
     * false:接口aop拦截不打印body日志
     */
    boolean value() default true;
}
