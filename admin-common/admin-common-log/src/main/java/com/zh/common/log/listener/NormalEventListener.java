package com.zh.common.log.listener;

import com.zh.common.log.event.NormalEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.skywalking.apm.toolkit.trace.TraceContext;
import org.springframework.context.ApplicationListener;

@Slf4j
public class NormalEventListener implements ApplicationListener<NormalEvent> {
  @Override
  public void onApplicationEvent(NormalEvent normalEvent) {
    String traceId = TraceContext.traceId();
    log.info("traceId:{} --- {} --- ,请求参数:{} --- 返回值:{}",
        traceId, normalEvent.getMethodName(), normalEvent.getInput(), normalEvent.getOutput());
  }
}
