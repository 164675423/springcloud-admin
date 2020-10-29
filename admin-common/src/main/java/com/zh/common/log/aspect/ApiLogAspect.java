package com.zh.common.log.aspect;

import com.zh.common.exception.AbstractException;
import com.zh.common.log.aspect.annotation.ApiLog;
import com.zh.common.log.publisher.BizExceptionPublisher;
import com.zh.common.log.publisher.InternalExceptionPublisher;
import com.zh.common.log.publisher.NormalPublisher;
import com.zh.common.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
@Slf4j
public class ApiLogAspect {

  @Pointcut("@annotation(com.zh.common.log.aspect.annotation.ApiLog)")
  public void pointCut() {
  }

  @Around(value = "pointCut()")
  public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
    Object object;
    Object[] args = joinPoint.getArgs();
    //方法签名
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String operationValue = getApiOperationValue(signature);
    //api的入参
    String input = JacksonUtils.parse(args);
    try {
      object = joinPoint.proceed();
    } catch (Exception e) {
      //对异常进行处理，可用于系统分析
      if (e instanceof AbstractException) {
        BizExceptionPublisher.publish((AbstractException) e, operationValue, input);
      } else {
        InternalExceptionPublisher.publish(e, operationValue, input);
      }
      throw e;
    }
    String result = JacksonUtils.parse(object);
    //无异常情况下记录访问日志
    NormalPublisher.publish(operationValue, input, result);
    return object;
  }

  private String getApiOperationValue(MethodSignature signature) {
    ApiLog apiOperation = signature.getMethod().getAnnotation(ApiLog.class);
    String declaringTypeName = signature.getDeclaringTypeName();
    String methodName = signature.getMethod().getName();
    String api = "";
    String description = "";
    if (apiOperation != null && StringUtils.isNotBlank(apiOperation.value())) {
      api = apiOperation.value();
      description = apiOperation.description();
    }
    return declaringTypeName
        .concat(".")
        .concat(methodName)
        .concat(":")
        .concat(api)
        .concat(",")
        .concat(description);
  }
}
