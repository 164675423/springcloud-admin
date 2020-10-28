package com.zh.storage.service.impl;

import com.zh.storage.config.StorageProperties;
import com.zh.storage.constant.Enums;
import com.zh.storage.service.StorageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

/**
 * 本地存储文件的实现类
 *
 * @author zh
 * @date 2020/4/22
 */
public class LocalStorageProvider implements StorageProvider {
  private final Logger logger = LoggerFactory.getLogger(LocalStorageProvider.class);
  private final StorageProperties properties;
  private String rootDirectory;

  public LocalStorageProvider(StorageProperties properties) {
    this.properties = properties;
    if (properties.getType().equals(StorageProperties.StorageType.Local)) {
      this.rootDirectory = properties.getLocal().getRootDirectory();
    }
  }

  @Override
  public String createFile(String fileName, InputStream inputStream, Integer type) throws IOException {
    String span = "";
    if (type == Enums.FileType.Domain.getIndex()) {
      span = Enums.FileType.Domain.toString().toLowerCase();
    } else if (type == Enums.FileType.Temp.getIndex()) {
      span = Enums.FileType.Temp.toString().toLowerCase();
    }
    Path path = Paths.get(rootDirectory, span, String.valueOf(new Date().getTime()));
    Path resolvePath = path.resolve(fileName);
    logger.info("创建文件,path:{}, 文件名:{}", path, fileName);
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }
    Files.copy(inputStream, resolvePath);
    return resolvePath.toString();
  }

  @Override
  public InputStream getFile(String location) throws IOException {
    Path path = Paths.get(location);
    if (Files.exists(path)) {
      InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ);
      return inputStream;
    }
    return null;
  }

  @Override
  public void deleteFile(String location) throws IOException {
    Path path = Paths.get(location);
    if (Files.exists(path)) {
      Files.delete(path);
    }
  }

}
