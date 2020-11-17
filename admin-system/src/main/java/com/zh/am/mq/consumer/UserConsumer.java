package com.zh.am.mq.consumer;

import com.zh.am.domain.dao.WhitelistMapper;
import com.zh.am.domain.dto.user.UserMessage;
import com.zh.am.domain.entity.Whitelist;
import com.zh.common.base.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

//@Component
@Slf4j
public class UserConsumer {
  private final Processor processor;
  private final WhitelistMapper whitelistMapper;

  public UserConsumer(Processor processor, WhitelistMapper whitelistMapper) {
    this.processor = processor;
    this.whitelistMapper = whitelistMapper;
  }

  @KafkaListener(topics = "${application.kafka.consumer.topics.user}")
  @Transactional(rollbackFor = Exception.class)
  public void listen(ConsumerRecord<String, String> record, Acknowledgment ack) throws InterruptedException {
    String json = new String(record.value().getBytes(), Charset.forName("utf-8"));
    log.info("kafka收到消息,key:{} ,value:{}", record.key(), json);
    try {
      UserMessage userMessage = JacksonUtils.parse(json, UserMessage.class);
      //处理消息
      if (processor.valid(record.key(), json)) {
        Whitelist whitelist = new Whitelist();
        whitelist.setApiId(userMessage.getId());
        //消息存到redis
        processor.afterConsume(record.key(), json);
      }
    } catch (Exception e) {
      processor.onError(json, e);
    } finally {
      //手动提交
      Thread.sleep(200);
      ack.acknowledge();
    }

  }

}
