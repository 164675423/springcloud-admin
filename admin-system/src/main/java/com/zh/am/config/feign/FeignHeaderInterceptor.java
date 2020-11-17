package com.zh.am.config.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * FeignHeaderInterceptor
 *
 * @author zh
 * @date 2020/11/17
 */
@Configuration
@Slf4j
public class FeignHeaderInterceptor implements RequestInterceptor {
  @Override
  public void apply(RequestTemplate requestTemplate) {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attributes != null) {
      HttpServletRequest request = attributes.getRequest();
      Map<String, String> headers = findCustomerHeader(request);
      if (!CollectionUtils.isEmpty(headers)) {
        headers.forEach((k, v) -> requestTemplate.header(k, v));
      }
      log.info("customer headers:" + headers.toString());
    }
  }

  private Map<String, String> findCustomerHeader(HttpServletRequest request) {
    HashMap<String, String> map = new HashMap<>();
    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String element = headerNames.nextElement();
      if (element.startsWith("x-")) {
        map.put(element, request.getHeader(element));
      }
    }
    return map;
  }
}
