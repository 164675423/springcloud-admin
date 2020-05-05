package com.zh.am.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 业务异常
 *
 * @author zh
 * @date 2020/1/3 11:26
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends AbstractException {

  private static final long serialVersionUID = 2394137313066029674L;

  public BusinessException(String message) {
    super(message);
  }

  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }

  public BusinessException(String code, String message) {
    super(code, message);
  }

  public BusinessException(String code, String message, Throwable cause) {
    super(code, message, cause);
  }
}
