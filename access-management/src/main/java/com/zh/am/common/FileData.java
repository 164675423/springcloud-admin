package com.zh.am.common;

import java.io.InputStream;

public class FileData {
  private String fileId;
  private String fileName;
  private String contentType;
  private Long size;
  private InputStream stream;

  public InputStream getStream() {
    return stream;
  }

  public void setStream(InputStream stream) {
    this.stream = stream;
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

}
