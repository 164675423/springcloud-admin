package com.zh.am.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

/**
 * 针对FileStorageClient的配置
 *
 * @author zh
 * @date 2020/5/1
 */
public class FileStorageClientConfig {
  @Bean
  public Encoder encoder(ObjectFactory<HttpMessageConverters> messageConverters) {
    return new FeignSpringFormEncoder(new SpringEncoder(messageConverters));
  }

  @Bean
  public Decoder decoder() {
    return new JacksonDecoder();
  }

  @Bean
  public ErrorDecoder errorDecoder() {
    return new FeignErrorDecoder();
  }
}

