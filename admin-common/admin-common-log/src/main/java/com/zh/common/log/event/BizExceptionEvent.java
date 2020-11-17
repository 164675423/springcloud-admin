package com.zh.common.log.event;

import com.zh.common.base.exception.AbstractException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class BizExceptionEvent extends ApplicationEvent {
  private AbstractException exception;
  private String methodName;
  private String input;

  public BizExceptionEvent(Object source, AbstractException exception, String methodName, String input) {
    super(source);
    this.exception = exception;
    this.input = input;
    this.methodName = methodName;
  }
}
