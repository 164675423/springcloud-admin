package com.zh.common.base.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class JacksonUtils {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
      .setTimeZone(TimeZone.getTimeZone("GMT+8"));

  /**
   * object转换为json字符串
   *
   * @param obj
   * @return
   */
  public static String parse(Object obj) {
    try {
      String val = OBJECT_MAPPER.writeValueAsString(obj);
      return val;
    } catch (JsonProcessingException e) {
      throw new RuntimeException("object to string convert failed.");
    }
  }

  /**
   * json字符串转换为对象
   *
   * @param jsonString
   * @param clazz
   * @param <T>
   * @return
   */
  public static <T> T parse(String jsonString, Class<T> clazz) {
    try {
      T val = OBJECT_MAPPER.readValue(jsonString, clazz);
      return val;
    } catch (IOException e) {
      throw new RuntimeException("json to object convert failed.");
    }
  }

  /**
   * json字符串转换为集合
   *
   * @param jsonString
   * @param eClass
   * @param <T>
   * @return
   */
  public static <T> List<T> parseList(String jsonString, Class<T> eClass) {
    try {
      return OBJECT_MAPPER.readValue(jsonString, OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, eClass));
    } catch (IOException e) {
      throw new RuntimeException("json to list convert failed.");
    }
  }

  /**
   * json字符串转换为map
   *
   * @param jsonString
   * @param kClass
   * @param vClass
   * @param <K>
   * @param <V>
   * @return
   */
  public static <K, V> Map<K, V> parseMap(String jsonString, Class<K> kClass, Class<V> vClass) {
    try {
      return OBJECT_MAPPER.readValue(jsonString, OBJECT_MAPPER.getTypeFactory().constructMapType(Map.class, kClass, vClass));
    } catch (IOException e) {
      throw new RuntimeException("json to map convert failed.");
    }
  }
}
