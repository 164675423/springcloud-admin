package com.zh.am.common.exception.handler;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.zh.am.common.exception.BusinessException;
import com.zh.am.common.exception.DataValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 抛出的businessException/dataValidationException,统一处理为http status 400
 *
 * @author zh
 * @date 2020/1/3 11:29
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(value = BusinessException.class)
  public ResponseEntity<ErrorInfo> businessExceptionHandler(BusinessException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(e.getMessage()));
  }

  @ExceptionHandler(value = DataValidationException.class)
  public ResponseEntity<ErrorInfo> dataValidationExceptionHandler(DataValidationException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(e.getMessage()));
  }

  @ExceptionHandler(value = HystrixBadRequestException.class)
  public ResponseEntity<ErrorInfo> hystrixBadRequestExceptionHandler(HystrixBadRequestException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(e.getMessage()));
  }

  protected class ErrorInfo {
    private String Code;
    private String message;

    public ErrorInfo(String message) {
      this.message = message;
    }

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
