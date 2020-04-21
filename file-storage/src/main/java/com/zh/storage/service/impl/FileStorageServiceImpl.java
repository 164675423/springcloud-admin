package com.zh.storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.storage.dao.FileMapper;
import com.zh.storage.domain.vo.FileVO;
import com.zh.storage.entity.File;
import com.zh.storage.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileStorageServiceImpl extends ServiceImpl<FileMapper, File> implements FileStorageService {
  private final FileMapper fileMapper;

  public FileStorageServiceImpl(FileMapper fileMapper) {
    this.fileMapper = fileMapper;
  }

  @Override
  public List<FileVO> addFiles(MultipartFile... file) {
    return null;
  }

  @Override
  public List<FileVO> getFiles(String... ids) {
    return null;
  }

  @Override
  public void deleteFiles(String... ids) {

  }
}
