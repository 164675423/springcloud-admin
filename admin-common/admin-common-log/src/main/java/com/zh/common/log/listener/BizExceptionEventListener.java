package com.zh.common.log.listener;

import com.zh.common.log.event.BizExceptionEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.context.ApplicationListener;

@Slf4j
public class BizExceptionEventListener implements ApplicationListener<BizExceptionEvent> {
  @Override
  public void onApplicationEvent(BizExceptionEvent event) {
    String traceId = TraceContext.traceId();
    log.info("traceId:{},{},请求参数:{} --- 业务异常:{}",
        traceId, event.getMethodName(), event.getInput(), event.getException().getMessage());
  }
}
