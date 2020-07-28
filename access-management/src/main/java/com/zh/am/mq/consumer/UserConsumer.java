package com.zh.am.mq.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

//@Component
public class UserConsumer {
  private Logger logger = LoggerFactory.getLogger(UserConsumer.class);

  @KafkaListener(topics = "${application.kafka.topics.user}")
  public void listen(ConsumerRecord<String, String> record) {
    logger.info("kafka收到消息,key:{} ,value:{}", record.key(), record.value());
  }
}
