package com.zh.storage.api;

import com.zh.storage.domain.vo.FileVO;
import com.zh.storage.service.FileStorageService;
import com.zh.web.contract.ResponseBodyWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "文件服务")
@RestController
@RequestMapping("api/v1/files")
public class FileController {
  private final FileStorageService fileStorageService;

  public FileController(FileStorageService fileStorageService) {
    this.fileStorageService = fileStorageService;
  }

  @ApiOperation("获取文件信息")
  @GetMapping
  public ResponseBodyWrapper<List<FileVO>> getFile(String id) {
    List<FileVO> files = fileStorageService.getFiles(id);
    return new ResponseBodyWrapper(files);
  }
}
