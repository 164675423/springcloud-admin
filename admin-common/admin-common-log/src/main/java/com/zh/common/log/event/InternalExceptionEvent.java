package com.zh.common.log.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class InternalExceptionEvent extends ApplicationEvent {
  private Exception exception;
  private String methodName;
  private String input;

  public InternalExceptionEvent(Object source, Exception exception, String methodName, String input) {
    super(source);
    this.exception = exception;
    this.input = input;
    this.methodName = methodName;
  }
}
