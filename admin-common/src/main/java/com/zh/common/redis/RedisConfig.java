package com.zh.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * redis相关配置
 *
 * @author zh
 * @date 2020/4/12
 */
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
@Slf4j
public class RedisConfig {

  /**
   * 使用Jackson2JsonRedisSerializer作为redisTemplate序列化器,springboot默认为JdkSerializationRedisSerializer
   * <see href="https://docs.spring.io/spring-data/redis/docs/2.2.6.RELEASE/reference/html/#mapping.fundamentals"/>
   *
   * @param lettuceConnectionFactory
   * @return
   */
  @Bean
  public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
    RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();

    // key-value结构序列化数据结构
    redisTemplate.setKeySerializer(stringRedisSerializer);
    redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);

    // hash数据结构序列化方式,必须这样否则存hash 就是基于jdk序列化的
    redisTemplate.setHashKeySerializer(stringRedisSerializer);
    redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);

    // 启用默认序列化方式
    redisTemplate.setEnableDefaultSerializer(true);
    redisTemplate.setDefaultSerializer(genericJackson2JsonRedisSerializer);

    redisTemplate.setConnectionFactory(lettuceConnectionFactory);
    return redisTemplate;
  }

  /**
   * 单机模式
   *
   * @param redissonProperties
   * @return
   */
  @Bean
  @ConditionalOnProperty(name = "spring.redisson.enable", havingValue = "true")
  public RedissonClient redissonClient(RedissonProperties redissonProperties) {
    String redisPrefix = "redis://";
    Config config = new Config();
    SingleServerConfig serverConfig = config.useSingleServer()
        .setAddress(redisPrefix + redissonProperties.getAddress())
        .setTimeout(redissonProperties.getTimeout())
        .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
        .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());
    if (StringUtils.isNotBlank(redissonProperties.getPassword())) {
      serverConfig.setPassword(redissonProperties.getPassword());
    }
    return Redisson.create(config);
  }

  @Bean
  public RedissonLockUtil redissLockUtil(RedissonClient redissonClient) {
    RedissonLockUtil redissonLockUtil = new RedissonLockUtil();
    redissonLockUtil.setRedissonClient(redissonClient);
    return redissonLockUtil;
  }
}
