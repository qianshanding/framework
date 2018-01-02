package com.qsd.framework.commons.aop;

import com.alibaba.fastjson.JSON;
import com.qsd.framework.commons.exception.BizException;
import com.qsd.framework.commons.exception.ErrorCodes;
import com.qsd.framework.commons.exception.ErrorLevel;
import com.qsd.framework.commons.exception.ParamCheckException;
import com.qsd.framework.commons.model.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Service拦截, 主要完成:
 * 1. 异常处理和异常返回值封装
 * 2. 日志记录
 */
@Component
@Aspect
public class ServiceAspect implements InitializingBean {

    private static final Logger accessLogger = LogManager.getLogger("AccessLog");    // 接入日志
    private static final Logger logger = LogManager.getLogger(ServiceAspect.class);// 普通日志


    public static final String MDC_TRADE_ID = "INNER_TRACE_ID";

    @Override
    public void afterPropertiesSet() {
    }

    @Around("@annotation(com.qsd.framework.commons.aop.ApiAnnotation)")
    public Object process(ProceedingJoinPoint joinPoint) {
        Object obj = null;
        long startTime = System.currentTimeMillis();
        try {
            obj = joinPoint.proceed();
        } catch (Throwable ex) {
            obj = parseException(joinPoint, ex);
        } finally {
            long rt = System.currentTimeMillis() - startTime;
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            ApiAnnotation annotation = method.getAnnotation(ApiAnnotation.class);
            boolean hideResultData = annotation != null && !annotation.value();
            recordLog(joinPoint, method, hideResultData, rt, obj);
        }
        return obj;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, Method method, boolean hideResultData, long rt, Object resultObj) {
        String methodString = method.getDeclaringClass().getName() + "." + method.getName();
        StringBuilder sb = new StringBuilder()
                .append(" args=").append(JSON.toJSONString(joinPoint.getArgs()))
                .append(" rt=").append(rt);
        // 结果是ResultModel
        if (resultObj != null && resultObj instanceof Result) {
            Result result = (Result) resultObj;
            // 打印code, message
            sb.append(" method=").append(methodString).append(" code=").append(result.getCode()).append(" msg=").append(result.getMessage());
            // 根据不同情况打印data
            if (hideResultData) {
                accessLogger.info(sb.toString());
                return;
            }
            if (result.getData() == null) {
                sb.append(" data=null");
                accessLogger.info(sb.toString());
                return;
            }
            // 对于列表，隐藏数据
            if (result.getData() instanceof Collection) {
                sb.append(" data=").append("Collection(size=").append(((Collection) result.getData()).size()).append(")");
                accessLogger.info(sb.toString());
                return;
            }
            // 通常数据
            sb.append(" data=").append(result.getData().toString());
            accessLogger.info(sb.toString());
            return;
        }
        // 结果不是Result
        if (hideResultData) {
            accessLogger.info(sb.toString());
            return;
        }
        String resultString = resultObj != null ? resultObj.toString() : "";
        sb.append(" result=").append(resultString);
        accessLogger.info(sb.toString());
    }

    private static Result parseException(ProceedingJoinPoint joinPoint, Throwable ex) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String message = new StringBuilder("exception=").append(ex.getClass().getSimpleName())
                .append(", method=").append(method.toString())
                .append(", args=").append(JSON.toJSONString(joinPoint.getArgs()))
                .toString();

        Result result = new Result();
        if (ex instanceof ParamCheckException) {
            ParamCheckException pex = (ParamCheckException) ex;
            logger.info(message, ex);
            result.setCode(pex.getErrorCode());
            result.setMessage(pex.getMessage());
        } else if (ex instanceof BizException) {
            BizException bizException = (BizException) ex;
            if (bizException.getErrorLevel() == ErrorLevel.INFO) {
                logger.info(message, ex);
            } else if (bizException.getErrorLevel() == ErrorLevel.WARN) {
                logger.warn(message, ex);
            } else if (bizException.getErrorLevel() == ErrorLevel.ERROR) {
                logger.error(message, ex);
            } else {
                logger.debug(message, ex);
            }
            result.setCode(bizException.getErrorCode());
            result.setMessage(bizException.getMessage());
        } else {
            logger.warn(message, ex);
            result.setCode(ErrorCodes.OTHER_EXCEPTION.getCode());
            result.setMessage(ex.getMessage());
        }
        return result;
    }
}
