package com.zh.am.service;

/**
 * redis相关操作
 * 对象及数组等均存储为json形式
 *
 * @author zh
 * @date 2020/4/12
 */
public interface RedisService {
  /**
   * 存储数据
   */
  void set(String key, String value);

  /**
   * 存储数据并设置超期时间,单位为秒
   */
  void set(String key, String value, long expire);

  /**
   * 获取数据
   */
  String get(String key);

  /**
   * 设置超期时间,单位为秒
   */
  boolean expire(String key, long expire);

  /**
   * 删除数据
   */
  void remove(String key);

  /**
   * 是否包含key
   */
  Boolean containsKey(String key);
}
