package com.zh.common.log.aspect;

import com.zh.common.base.exception.AbstractException;
import com.zh.common.base.util.JacksonUtils;
import com.zh.common.log.aspect.annotation.ApiLog;
import com.zh.common.log.publisher.BizExceptionPublisher;
import com.zh.common.log.publisher.InternalExceptionPublisher;
import com.zh.common.log.publisher.NormalPublisher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.core.annotation.SynthesizingMethodParameter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Slf4j
@Order(-1)
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
    Method method = signature.getMethod();

    //api的入参
    String input = getArgs(method, args);
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

  private String getArgs(Method method, Object[] args) {
    //请求参数
    final Map<String, Object> paraMap = new HashMap<>(16);

    for (int i = 0; i < args.length; i++) {
      // 读取方法参数
      MethodParameter methodParam = new SynthesizingMethodParameter(method, i);
      methodParam.initParameterNameDiscovery(new DefaultParameterNameDiscoverer());

      RequestBody requestBody = methodParam.getParameterAnnotation(RequestBody.class);
      String parameterName = methodParam.getParameterName();
      Object value = args[i];
      // 如果是body的json则是对象
      if (requestBody != null && value != null) {
        paraMap.putAll(BeanMap.create(value));
        continue;
      }
      // 处理特殊类型
      if (value instanceof HttpServletRequest) {
        paraMap.putAll(((HttpServletRequest) value).getParameterMap());
      } else if (value instanceof WebRequest) {
        paraMap.putAll(((WebRequest) value).getParameterMap());
      } else if (value instanceof MultipartFile) {
        MultipartFile multipartFile = (MultipartFile) value;
        String name = multipartFile.getName();
        String fileName = multipartFile.getOriginalFilename();
        paraMap.put("file", fileName);
      } else {
        // 参数名
        RequestParam requestParam = methodParam.getParameterAnnotation(RequestParam.class);
        String paraName;
        if (requestParam != null && StringUtils.isNotBlank(requestParam.value())) {
          paraName = requestParam.value();
        } else {
          paraName = methodParam.getParameterName();
        }
        paraMap.put(paraName, value);
      }
    }
    return JacksonUtils.parse(paraMap);
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
