package com.zh.am.mq.consumer;

import com.zh.am.service.IRedisService;
import com.zh.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Processor {
  private static final String PREFIX = "kafka:message:";
  private final IRedisService redisService;
  @Value("${spring.kafka.consumer.group-id}")
  private String group;

  public Processor(IRedisService redisService) {
    this.redisService = redisService;
  }

  public boolean valid(String messageKey, String value) {
    // 检查消息是否重复
    Boolean exists = redisService.containsKey(PREFIX + group + ":" + messageKey);
    if (exists) {
      throw new BusinessException("message exists");
    }
    return true;
  }

  public final void onError(String message, Exception e) {
    //TODO. 记录消费失败的消息
    log.error("消息消费失败,{}, {}", e.getCause(), message);
  }

  public final void afterConsume(String messageKey, String value) {
    String key = PREFIX + group + ":" + messageKey;
    redisService.set(key, value);
    redisService.expire(key, 60 * 60 * 24);
  }
}
