package com.zh.common.log.aspect;

import com.zh.common.exception.AbstractException;
import com.zh.common.log.aspect.annotation.ApiLog;
import com.zh.common.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ApiLogAspect {

  @Pointcut("@annotation(com.zh.common.log.aspect.annotation.ApiLog)")
  public void pointCut() {
  }

  @Around(value = "pointCut()")
  public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
    Object object;
    Object[] args = joinPoint.getArgs();
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String operationValue = getApiOperationValue(signature);
    try {
      object = joinPoint.proceed();
    } catch (AbstractException e) {
      //TODO 使用spring event
      log.info("{} --- ,请求参数:{} --- 业务异常:{}", operationValue, JacksonUtils.parse(args), e.getMessage());
      throw e;
    } catch (Exception e) {
      log.info("{} --- ,请求参数:{} --- 系统异常:{}", operationValue, JacksonUtils.parse(args), e.getMessage());
      throw e;
    } finally {
      //TODO 记录异常日志，用于业务分析

    }
    String result = JacksonUtils.parse(object);
    log.info("{} --- ,请求参数:{} --- 返回值:{}", operationValue, JacksonUtils.parse(args), result);
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
        .concat("----")
        .concat(description);
  }
}
