package com.zh.web.handler;

import com.zh.am.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 抛出的businessException,统一处理为http status 400
 *
 * @author zh
 * @date 2020/1/3 11:29
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = BusinessException.class)
  public ResponseEntity<ErrorInfo> exceptionHandler(BusinessException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(e.getCode(), e.getMessage()));
  }

  protected class ErrorInfo {
    private String Code;
    private String message;

    public ErrorInfo(String code, String message) {
      Code = code;
      this.message = message;
    }

    public String getCode() {
      return Code;
    }

    public void setCode(String code) {
      Code = code;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }
}
