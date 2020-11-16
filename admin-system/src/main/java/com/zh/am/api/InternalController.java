package com.zh.am.api;

import com.zh.am.feignclient.FileStorageClient;
import com.zh.common.contract.ResponseBodyWrapper;
import com.zh.common.log.aspect.annotation.ApiLog;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 可匿名访问的api
 *
 * @author zh
 * @date 2020/3/5
 */
@RestController
@RequestMapping("internal/api/v1/internal")
@Slf4j
public class InternalController {
  private final FileStorageClient fileStorageClient;

  public InternalController(FileStorageClient fileStorageClient) {
    this.fileStorageClient = fileStorageClient;
  }

  @GetMapping
  @ApiLog(value = "internal", description = "internal description")
  public ResponseBodyWrapper internal(StatusList list) {
    log.info(list.toString());
    return ResponseBodyWrapper.from("internal api");
  }

  @Data
  public static class StatusList {
    private List<Integer> status;
  }
}
