package com.zh.am.api;

import com.zh.am.common.contract.ResponseBodyWrapper;
import com.zh.am.feignClient.FileStorageClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 可匿名访问的api
 *
 * @author zh
 * @date 2020/3/5
 */
@RestController
@RequestMapping("internal/api/v1/internal")
public class InternalController {
  private final FileStorageClient fileStorageClient;

  public InternalController(FileStorageClient fileStorageClient) {
    this.fileStorageClient = fileStorageClient;
  }

  @GetMapping
  public ResponseBodyWrapper internal() {
    return ResponseBodyWrapper.from("internal api");
  }
}
