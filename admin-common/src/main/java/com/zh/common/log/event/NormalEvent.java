package com.zh.common.log.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 无异常的情况下对应的event
 *
 * @author zh
 * @date 2020/10/29
 */
@Getter
@Setter
public class NormalEvent extends ApplicationEvent {
  private String methodName;
  private String input;
  private String output;

  public NormalEvent(Object source, String methodName, String input, String output) {
    super(source);
    this.methodName = methodName;
    this.input = input;
    this.output = output;
  }
}
