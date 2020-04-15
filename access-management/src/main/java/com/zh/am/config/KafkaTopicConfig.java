package com.zh.am.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * kafka topics
 *
 * @author zh
 * @date 2020/4/15
 */
@Configuration
public class KafkaTopicConfig {
  private static final Integer DEFAULT_PARTITIONS = 5;
  private static final Short REPLICATION = 1;

  @Value("${application.kafka.topics.user}")
  private String userTopic;

  @Bean
  public NewTopic user() {
    return new NewTopic(userTopic, DEFAULT_PARTITIONS, REPLICATION);
  }

}
