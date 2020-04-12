package com.zh.am.redis.impl;

import com.zh.am.redis.IRedisService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements IRedisService {
  private final StringRedisTemplate stringRedisTemplate;

  public RedisServiceImpl(StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }

  @Override
  public void set(String key, String value) {
    stringRedisTemplate.opsForValue().set(key, value);
  }

  @Override
  public void set(String key, String value, long expire) {
    stringRedisTemplate.opsForValue().set(key, value);
    stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
  }

  @Override
  public String get(String key) {
    return stringRedisTemplate.opsForValue().get(key);
  }

  @Override
  public boolean expire(String key, long expire) {
    return stringRedisTemplate.expire(key, expire, TimeUnit.SECONDS);
  }

  @Override
  public void remove(String key) {
    stringRedisTemplate.delete(key);
  }

  @Override
  public Boolean containsKey(String key) {
    return stringRedisTemplate.hasKey(key);
  }

}
