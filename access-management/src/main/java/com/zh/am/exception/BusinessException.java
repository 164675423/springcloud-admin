package com.zh.am.exception;

/**
 * 业务异常
 *
 * @author zh
 * @date 2020/1/3 11:26
 */
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
