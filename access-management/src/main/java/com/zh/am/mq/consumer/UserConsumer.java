package com.zh.am.mq.consumer;

import com.zh.am.common.exception.DataValidationException;
import com.zh.am.domain.dao.WhitelistMapper;
import com.zh.am.domain.dto.user.UserMessage;
import com.zh.am.domain.entity.Whitelist;
import com.zh.am.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

@Component
@Slf4j
public class UserConsumer {
  private final Processor processor;
  private final WhitelistMapper whitelistMapper;

  public UserConsumer(Processor processor, WhitelistMapper whitelistMapper) {
    this.processor = processor;
    this.whitelistMapper = whitelistMapper;
  }

  @KafkaListener(topics = "${application.kafka.topics.user}")
  @Transactional(rollbackFor = Exception.class)
  public void listen(ConsumerRecord<String, String> record, Acknowledgment ack) {
    String json = new String(record.value().getBytes(), Charset.forName("utf-8"));
    try {
      UserMessage userMessage = JacksonUtils.parse(json, UserMessage.class);
      log.info("kafka收到消息,key:{} ,value:{}", record.key(), record.value());
      if (processor.valid(userMessage)) {
        execute(userMessage);
      }
    } catch (Exception e) {
      processor.onError(json, e);
    } finally {
      //手动提交
      ack.acknowledge();
    }
  }

  /**
   * 处理业务逻辑
   */
  private void execute(UserMessage userMessage) {
    Whitelist whitelist = new Whitelist();
    whitelist.setApiId(userMessage.getKey());
    if (whitelistMapper.insert(whitelist) == 0) {
      throw new DataValidationException("consume failed.");
    }
  }

}
