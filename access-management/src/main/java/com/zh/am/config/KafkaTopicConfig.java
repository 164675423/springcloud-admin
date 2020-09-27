package com.zh.am.config;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * kafka topics
 *
 * @author zh
 * @date 2020/4/15
 */
@Configuration
public class KafkaTopicConfig {
  private static final Integer DEFAULT_PARTITIONS = 1;
  private static final Short REPLICATION = 1;
  private final ConsumerFactory consumerFactory;
  private final KafkaTemplate kafkaTemplate;

  @Value("${application.kafka.producer.topics.user}")
  private String userTopic;
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServer;

  public KafkaTopicConfig(ConsumerFactory consumerFactory, KafkaTemplate kafkaTemplate) {
    this.consumerFactory = consumerFactory;
    this.kafkaTemplate = kafkaTemplate;
  }

  /**
   * 创建一个kafka管理类，相当于rabbitMQ的管理类rabbitAdmin,没有此bean无法自定义的使用adminClient创建topic
   *
   * @return
   */
  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> props = new HashMap<>();
    //配置Kafka实例的连接地址
    props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
    KafkaAdmin admin = new KafkaAdmin(props);
    return admin;
  }

  /**
   * kafka客户端，在spring中创建这个bean之后可以注入并且创建topic
   *
   * @return
   */
  @Bean
  public AdminClient adminClient() {
    AdminClient client = AdminClient.create(kafkaAdmin().getConfig());
    ArrayList<NewTopic> newTopics = new ArrayList<>();

    //创建topic
    newTopics.add(new NewTopic(userTopic, DEFAULT_PARTITIONS, REPLICATION));

    client.createTopics(newTopics);
    return client;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory containerFactory() {
    ConcurrentKafkaListenerContainerFactory factory = new ConcurrentKafkaListenerContainerFactory();
    factory.setConsumerFactory(consumerFactory);
    // 最大重试次数3次
    factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate), 3));
    return factory;
  }
}
