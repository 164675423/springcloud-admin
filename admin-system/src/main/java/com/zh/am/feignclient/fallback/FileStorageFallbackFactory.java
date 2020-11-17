package com.zh.am.feignclient.fallback;

import com.zh.am.domain.dto.common.FileData;
import com.zh.am.feignclient.FileStorageClient;
import com.zh.common.base.contract.ResponseBodyWrapper;
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
      public Response getFile(String id) {
        logger.info("fall back");
        return null;
      }

      @Override
      public ResponseBodyWrapper<String> deleteFiles(String... id) {
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
