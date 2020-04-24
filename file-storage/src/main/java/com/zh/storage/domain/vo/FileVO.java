package com.zh.storage.domain.vo;

import java.io.InputStream;

public class FileVO {
  private String fileId;
  private String fileName;
  private String contentType;
  private Long size;

  private InputStream inputStream;

  public FileVO() {
  }

  public FileVO(String fileId, String fileName, String contentType, Long size) {
    this.fileId = fileId;
    this.fileName = fileName;
    this.contentType = contentType;
    this.size = size;
  }

  public InputStream getInputStream() {
    return inputStream;
  }

  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public Long getSize() {
    return size;
  }

  public void setSize(Long size) {
    this.size = size;
  }

  public String getFileId() {
    return fileId;
  }

  public void setFileId(String fileId) {
    this.fileId = fileId;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
