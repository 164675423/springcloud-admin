package com.zh.am.config;

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
}
