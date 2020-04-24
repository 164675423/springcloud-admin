package com.zh.storage.service;

import java.io.IOException;
import java.io.InputStream;

/**
 * 文件服务的service
 *
 * @author zh
 * @date 2020/4/22
 */
public interface StorageProvider {
  String createFile(String fileName, InputStream inputStream, Integer type) throws IOException;

  InputStream getFile(String location) throws IOException;

  void deleteFile(String location) throws IOException;
}
