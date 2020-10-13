package com.zh.common.exception;

public class DataValidationException extends AbstractException {
  private static final long serialVersionUID = -7794183314980770294L;

  public DataValidationException(String message) {
    super(message);
  }

  public DataValidationException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataValidationException(String code, String message) {
    super(code, message);
  }

  public DataValidationException(String code, String message, Throwable cause) {
    super(code, message, cause);
  }
}
