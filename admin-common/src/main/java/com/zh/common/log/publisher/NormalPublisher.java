package com.zh.common.log.publisher;

import com.zh.common.log.event.NormalEvent;
import com.zh.common.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;

@Slf4j
public class NormalPublisher {
  public static void publish(String methodName, String input, String output) {
    NormalEvent event = new NormalEvent(TraceContext.traceId(), methodName, input, output);
    SpringUtils.publishEvent(event);
  }

}
