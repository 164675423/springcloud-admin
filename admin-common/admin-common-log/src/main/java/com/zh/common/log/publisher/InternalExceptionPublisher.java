package com.zh.common.log.publisher;

import com.zh.common.base.util.SpringUtils;
import com.zh.common.log.event.InternalExceptionEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

@Slf4j
public class InternalExceptionPublisher {
  public static void publish(Exception e, String methodName, String input) {
    InternalExceptionEvent event = new InternalExceptionEvent(TraceContext.traceId(), e, methodName, input);
    SpringUtils.publishEvent(event);
  }

}
