package com.zh.storage.service;

import com.zh.storage.domain.vo.FileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {
  /**
   * 上传文件
   *
   * @param type
   * @param files
   * @return
   * @throws IOException
   */
  List<FileVO> addFiles(Integer type, MultipartFile... files) throws IOException;

  /**
   * 获取文件
   *
   * @param ids 文件id
   * @return
   */
  List<FileVO> getFiles(String... ids) throws IOException;

  /**
   * 删除文件
   *
   * @param ids 文件id
   */
  void deleteFiles(String... ids) throws IOException;
}
