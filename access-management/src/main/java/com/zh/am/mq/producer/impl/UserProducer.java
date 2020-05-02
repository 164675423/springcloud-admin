package com.zh.am.mq.producer.impl;

import com.zh.am.domain.dto.user.UserMessage;
import com.zh.am.domain.entity.User;
import com.zh.am.exception.DataValidationException;
import com.zh.am.mq.producer.IUserProducer;
import com.zh.am.util.JacksonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Component
public class UserProducer implements IUserProducer {
  private final Logger logger = LoggerFactory.getLogger(UserProducer.class);
  private final KafkaTemplate<String, String> kafkaTemplate;
  @Value("${application.kafka.topics.user}")
  private String topic;

  public UserProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void addOrUpdateUser(User user) {
    UserMessage message = new UserMessage();
    message.setId(user.getId());
    message.setName(user.getName());
    message.setUsername(user.getUsername());
    message.setReadonly(user.getReadonly());
    try {
      String key = UUID.randomUUID().toString();
      //默认发送为异步，调整为同步
      kafkaTemplate.send(topic, key, JacksonUtils.parse(message)).get();
      logger.info("kafka 发送消息,key:{} ,value:{}", key, JacksonUtils.parse(message));
    } catch (InterruptedException | ExecutionException e) {
      logger.info("kafka 发送消息失败,{},{}", e.getCause(), e.getMessage());
      throw new DataValidationException("kafka error. ");
    }
  }
}
