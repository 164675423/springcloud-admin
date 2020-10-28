package com.zh.storage.config;

import com.zh.storage.service.StorageProvider;
import com.zh.storage.service.impl.LocalStorageProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  /**
   * 注入文件服务
   *
   * @param properties
   * @return
   */
  @Bean
  public StorageProvider storageProvider(StorageProperties properties) {
    if (properties.getType().equals(StorageProperties.StorageType.Local)) {
      if (StringUtils.isBlank(properties.getLocal().getRootDirectory())) {
        throw new IllegalArgumentException("请配置 file.storage.local.root-directory属性");
      }
      return new LocalStorageProvider(properties);
    }
    return null;
  }
}
