package com.zh.storage.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件存储相关的配置
 *
 * @author zh
 * @date 2020/4/22
 */
@Component
@ConfigurationProperties("file.storage")
public class StorageProperties {
  private StorageType type;
  private Local local;

  public StorageType getType() {
    return type;
  }

  public void setType(StorageType type) {
    this.type = type;
  }

  public Local getLocal() {
    return local;
  }

  public void setLocal(Local local) {
    this.local = local;
  }

  /**
   * 存储类型
   */
  public enum StorageType {
    Local,
    Ftp,
  }

  /**
   * 本地文件系统存储.
   */
  public static class Local {
    /**
     * 本地存储的根路径.
     */
    private String rootDirectory;

    public String getRootDirectory() {
      return rootDirectory;
    }

    public void setRootDirectory(String rootDirectory) {
      this.rootDirectory = rootDirectory;
    }
  }

}
