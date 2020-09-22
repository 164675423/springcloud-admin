package com.zh.am.aop;

import com.zh.am.common.exception.AbstractException;
import com.zh.am.util.JacksonUtils;
import io.swagger.annotations.ApiOperation;
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
public class ApiOperationAspect {

  @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
  public void pointCut() {
  }

  @Around(value = "pointCut()")
  public Object proceed(ProceedingJoinPoint joinPoint) throws Throwable {
    Object object = null;
    Object[] args = joinPoint.getArgs();
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    String operationValue = getApiOperationValue(signature);
    try {
      object = joinPoint.proceed();
    } catch (AbstractException e) {
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
    ApiOperation apiOperation = signature.getMethod().getAnnotation(ApiOperation.class);
    String declaringTypeName = signature.getDeclaringTypeName();
    String methodName = signature.getMethod().getName();
    String apiDesc = "";
    if (apiOperation != null && StringUtils.isNotBlank(apiOperation.value())) {
      apiDesc = apiOperation.value();
    }
    return declaringTypeName + "." + methodName + ":" + apiDesc;
  }
}
