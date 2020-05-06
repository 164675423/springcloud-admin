package com.zh.am.feignClient.fallback;

import com.zh.am.common.FileData;
import com.zh.am.common.contract.ResponseBodyWrapper;
import com.zh.am.feignClient.FileStorageClient;
import feign.Response;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * FileStorageFallbackFactory
 *
 * @author zh
 * @date 2020/5/2
 */
@Component
public class FileStorageFallbackFactory implements FallbackFactory<FileStorageClient> {
  private Logger logger = LoggerFactory.getLogger(FileStorageFallbackFactory.class);

  @Override
  public FileStorageClient create(Throwable throwable) {

    return new FileStorageClient() {
      @Override
      public ResponseBodyWrapper<String> getFileById(String id) {
        logger.info("fall back");
        return null;
      }

      @Override
      public Response getFile(String id) {
        logger.info("fall back");
        return null;
      }

      @Override
      public ResponseBodyWrapper deleteFiles(String... id) {
        logger.info("fall back");
        return null;
      }

      @Override
      public ResponseBodyWrapper<List<FileData>> addFile(Integer type, MultipartFile... files) {
        logger.info("fall back");
        return null;
      }
    };
  }
}
