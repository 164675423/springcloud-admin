package com.zh.common.base.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * spring工具类
 *
 * @author zh
 * @date 2020/10/29
 */
@Slf4j
@Component
public class SpringUtils implements ApplicationContextAware {

  /**
   * spring上下文
   */
  private static ApplicationContext context;

  private SpringUtils() {
  }

  public static Map<String, Object> getBeanWithAnnotation(Class<? extends Annotation> annotation) {
    if (context == null) {
      return null;
    }
    return context.getBeansWithAnnotation(annotation);
  }

  public static <T> T getBean(Class<T> clazz) {
    if (context == null || clazz == null)
      return null;
    return context.getBean(clazz);
  }

  public static void publishEvent(ApplicationEvent event) {
    if (context == null) {
      return;
    }
    try {
      context.publishEvent(event);
    } catch (Exception ex) {
      log.error(ex.getMessage());
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }


}
