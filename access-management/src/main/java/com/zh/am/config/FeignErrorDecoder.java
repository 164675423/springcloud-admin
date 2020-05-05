package com.zh.am.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 自定义feign errorDecoder，增加对 badRequest的处理
 * feign默认的errorDecoder会处理http status != 2xx的所有请求，如代理服务返回400时，不应触发hystrix Fallbacks，因此需要单独处理
 * <see href="https://github.com/OpenFeign/feign/#basics"></see>
 *
 * @author zh
 * @date 2020/5/2
 */
@Component
public class FeignErrorDecoder implements ErrorDecoder {
  private Logger logger = LoggerFactory.getLogger(FeignErrorDecoder.class);

  @Override
  public Exception decode(String methodKey, Response response) {
    logger.info(String.valueOf(response.status()));
    return null;
  }
}
