package com.zh.common.log.listener;

import com.zh.common.log.event.InternalExceptionEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.context.ApplicationListener;

@Slf4j
public class InternalExceptionEventListener implements ApplicationListener<InternalExceptionEvent> {
  @Override
  public void onApplicationEvent(InternalExceptionEvent event) {
    String traceId = TraceContext.traceId();
    log.info("traceId:{},{},请求参数:{} --- 系统异常:{}",
        traceId, event.getMethodName(), event.getInput(), event.getException().getMessage());

  }
}
