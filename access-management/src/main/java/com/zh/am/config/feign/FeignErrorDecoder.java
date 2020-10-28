package com.zh.am.config.feign;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.zh.common.util.JacksonUtils;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义feign errorDecoder，增加对 badRequest的处理
 * feign默认的errorDecoder会处理http status != 2xx的所有请求，如代理服务返回400时，不应触发hystrix Fallbacks，因此需要单独处理
 * <see href="https://github.com/OpenFeign/feign/#basics"></see>
 *
 * @author zh
 * @date 2020/5/2
 */
@Component
@Slf4j
public class FeignErrorDecoder extends ErrorDecoder.Default {

  @Override
  public Exception decode(String methodKey, Response response) {
    Exception exception = null;
    String body = null;
    try {
      body = Util.toString(response.body().asReader());
    } catch (IOException e) {
      log.info("response 转换为reader失败");
      exception = e;
    }
    switch (response.status()) {
      case 400:
        ErrorBody errorbody;
        if (StringUtils.isNotBlank(body)) {
          try {
            //尝试从response中获取error message
            errorbody = JacksonUtils.parse(body, ErrorBody.class);
          } catch (Exception e) {
            log.info("response.body中不包含message");
            errorbody = null;
          }
          if (errorbody != null) {
            String message = errorbody.getMessage();
            exception = new HystrixBadRequestException(message);
          }
        }
        break;
    }

    //默认情况下调用feign自带的error decoder
    if (exception == null) {
      return super.decode(methodKey, response);
    } else {
      return exception;
    }
  }

  protected static class ErrorBody {
    private String message;

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }
}
