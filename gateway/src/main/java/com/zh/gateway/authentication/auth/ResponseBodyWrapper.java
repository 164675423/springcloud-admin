package com.zh.gateway.authentication.auth;

public class ResponseBodyWrapper<T> {
  private String message;
  private T payload;

  public ResponseBodyWrapper(String message) {
    this.message = message;
  }

  public ResponseBodyWrapper(T payload) {
    this.payload = payload;
  }

  public ResponseBodyWrapper(String message, T payload) {
    this.message = message;
    this.payload = payload;
  }

  public static <T> ResponseBodyWrapper<T> from(String message) {
    return new ResponseBodyWrapper<T>(message);
  }

  public static <T> ResponseBodyWrapper<T> from(T payload) {
    return new ResponseBodyWrapper<T>(payload);
  }

  public static <T> ResponseBodyWrapper<T> from(String message, T payload) {
    return new ResponseBodyWrapper<T>(message, payload);
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getPayload() {
    return payload;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }
}
