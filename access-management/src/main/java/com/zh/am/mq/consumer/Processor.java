package com.zh.am.mq.consumer;

import com.zh.am.common.exception.BusinessException;
import com.zh.am.mq.Message;
import com.zh.am.service.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Processor {
  private static final String PREFIX = "kafka:message:";
  private final IRedisService redisService;
  //  @Value("${spring.kafka.consumer.group-id}")
  private String group;

  public Processor(IRedisService redisService) {
    this.redisService = redisService;
  }

  public <T extends Message> boolean valid(T message) {
    // 检查消息是否重复
    Boolean exists = redisService.containsKey(PREFIX + group + ":" + message.getKey());
    if (exists) {
      throw new BusinessException("message exists");
    }
    return true;
  }

  public final void onError(String message, Exception e) {
    log.error("消息消费失败, {},{}", message, e.getMessage());
  }

}
