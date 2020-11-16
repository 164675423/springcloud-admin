package com.zh.storage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zh.common.exception.DataValidationException;
import com.zh.storage.dao.FileMapper;
import com.zh.storage.domain.entity.File;
import com.zh.storage.domain.vo.FileVO;
import com.zh.storage.service.FileStorageService;
import com.zh.storage.service.StorageProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageServiceImpl extends ServiceImpl<FileMapper, File> implements FileStorageService {
  private final FileMapper fileMapper;
  private final StorageProvider storageProvider;

  public FileStorageServiceImpl(FileMapper fileMapper, StorageProvider storageProvider) {
    this.fileMapper = fileMapper;
    this.storageProvider = storageProvider;
  }

  @Override
  @Transactional
  public List<FileVO> addFiles(Integer type, MultipartFile... files) throws IOException {
    List<FileVO> list = new ArrayList<>();
    for (MultipartFile file : files) {
      try (InputStream inputStream = file.getInputStream()) {
        //生成文件
        String location = storageProvider.createFile(file.getOriginalFilename(), inputStream, type);
        //插入文件至数据库
        File meta = new File();
        meta.setId(UUID.randomUUID().toString());
        meta.setContentType(file.getContentType());
        meta.setType(type);
        meta.setFileName(file.getOriginalFilename());
        meta.setLocation(location);
        meta.setSize(file.getSize());
        fileMapper.insert(meta);

        list.add(new FileVO(meta.getId(), meta.getFileName(), meta.getContentType(), meta.getSize()));
      }
    }
    return list;
  }

  @Override
  public List<FileVO> getFiles(String... ids) throws IOException {
    List<FileVO> list = new ArrayList<>();
    for (String id : ids) {
      File file = fileMapper.selectById(id);
      if (file == null) {
        throw new DataValidationException("file not found");
      }
      InputStream stream = storageProvider.getFile(file.getLocation());
      FileVO fileVO = new FileVO();
      fileVO.setContentType(file.getContentType());
      fileVO.setSize(file.getSize());
      fileVO.setFileId(file.getId());
      fileVO.setFileName(file.getFileName());
      fileVO.setInputStream(stream);
      list.add(fileVO);
    }
    return list;
  }

  @Override
  @Transactional
  public void deleteFiles(String... ids) throws IOException {
    for (String id : ids) {
      File file = fileMapper.selectById(id);
      if (file == null) {
        throw new DataValidationException("file not found");
      }
      fileMapper.deleteById(id);
      storageProvider.deleteFile(file.getLocation());
    }
  }
}
