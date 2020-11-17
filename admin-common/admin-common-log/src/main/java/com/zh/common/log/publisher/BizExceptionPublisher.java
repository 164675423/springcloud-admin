package com.zh.common.log.publisher;

import com.zh.common.base.exception.AbstractException;
import com.zh.common.base.util.SpringUtils;
import com.zh.common.log.event.BizExceptionEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

@Slf4j
public class BizExceptionPublisher {
  public static void publish(AbstractException e, String methodName, String input) {
    BizExceptionEvent event = new BizExceptionEvent(TraceContext.traceId(), e, methodName, input);
    SpringUtils.publishEvent(event);
  }

}
