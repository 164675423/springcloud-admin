package com.zh.storage.web.handler;

import com.zh.common.exception.BusinessException;
import com.zh.common.exception.DataValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 抛出的businessException,统一处理为http status 400
 *
 * @author zh
 * @date 2020/1/3 11:29
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {BusinessException.class, DataValidationException.class})
  public ResponseEntity<ErrorInfo> exceptionHandler(RuntimeException e) {
    e.printStackTrace();
    if (e instanceof BusinessException) {
      BusinessException exception = (BusinessException) e;
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(exception.getCode(), exception.getMessage()));
    } else if (e instanceof DataValidationException) {
      DataValidationException exception = (DataValidationException) e;
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(exception.getCode(), exception.getMessage()));
    }
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
