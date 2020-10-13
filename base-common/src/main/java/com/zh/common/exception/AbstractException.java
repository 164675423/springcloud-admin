package com.zh.common.exception;

import com.zh.common.constants.Enums;

public abstract class AbstractException extends RuntimeException {
  private static final long serialVersionUID = -4200762008865565990L;
  private String code;

  public AbstractException(String message) {
    super(message);
  }

  public AbstractException(String message, Throwable cause) {
    super(message, cause);
  }

  public AbstractException(String code, String message) {
    super(message);
    this.code = code;
  }

  public AbstractException(String code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }
}
