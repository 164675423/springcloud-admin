package com.zh.common.log.config;


import com.zh.common.log.aspect.ApiLogAspect;
import com.zh.common.log.listener.BizExceptionEventListener;
import com.zh.common.log.listener.InternalExceptionEventListener;
import com.zh.common.log.listener.NormalEventListener;
import com.zh.common.log.publisher.BizExceptionPublisher;
import com.zh.common.log.publisher.InternalExceptionPublisher;
import com.zh.common.log.publisher.NormalPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 日志相关的配置类
 *
 * @author zh
 * @date 2020/10/29
 */
@Configuration
@Slf4j
public class LogConfiguration {
  @PostConstruct
  public void init() {
    log.info("-----------------logConfiguration init");
  }

  @Bean
  public ApiLogAspect apiLogAspect() {
    return new ApiLogAspect();
  }

  @Bean
  public BizExceptionEventListener bizExceptionEventListener() {
    return new BizExceptionEventListener();
  }

  @Bean
  public InternalExceptionEventListener internalExceptionEventListener() {
    return new InternalExceptionEventListener();
  }

  @Bean
  public NormalEventListener normalEventListener() {
    return new NormalEventListener();
  }

  @Bean
  public BizExceptionPublisher bizExceptionPublisher() {
    return new BizExceptionPublisher();
  }

  @Bean
  public InternalExceptionPublisher internalExceptionPublisher() {
    return new InternalExceptionPublisher();
  }

  @Bean
  public NormalPublisher normalPublisher() {
    return new NormalPublisher();
  }

}
