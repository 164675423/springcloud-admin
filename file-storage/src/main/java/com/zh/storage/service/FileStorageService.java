package com.zh.storage.service;

import com.zh.storage.domain.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileStorageService {
  /**
   * 上传文件
   *
   * @param file
   * @return
   */
  List<FileVO> addFiles(MultipartFile... file);

  /**
   * 获取文件
   *
   * @param ids 文件id
   * @return
   */
  List<FileVO> getFiles(String... ids);

  /**
   * 删除文件
   *
   * @param ids 文件id
   */
  void deleteFiles(String... ids);
}
