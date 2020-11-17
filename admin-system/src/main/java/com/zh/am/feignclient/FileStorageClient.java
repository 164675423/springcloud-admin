package com.zh.am.feignclient;

import com.zh.am.config.application.DefaultFeignClientConfig;
import com.zh.am.domain.dto.common.FileData;
import com.zh.common.base.contract.ResponseBodyWrapper;
import com.zh.common.base.exception.DataValidationException;
import feign.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@FeignClient(name = "admin-file-storage", configuration = DefaultFeignClientConfig.class)
public interface FileStorageClient {
  /**
   * 获取文件
   *
   * @param id
   * @return 文件流
   */
  @RequestMapping(method = RequestMethod.GET, value = "/api/v1/files/{id}")
  Response getFile(@PathVariable String id);

  default FileData getFileStream(String id) throws IOException {
    Response response = getFile(id);
    if (response == null) {
      throw new DataValidationException("文件服务未返回文件");
    }
    Map<String, Collection<String>> headers = response.headers();
    List<String> collect = new ArrayList<>();
    if (headers.get(HttpHeaders.CONTENT_DISPOSITION) != null) {
      collect = headers.get(HttpHeaders.CONTENT_DISPOSITION).stream().collect(Collectors.toList());
    }
    if (CollectionUtils.isEmpty(collect)) {
      throw new DataValidationException("文件服务返回的 response header 错误");
    }
    String fileName;
    try {
      fileName = URLDecoder.decode(StringUtils.substringAfter(collect.get(0), "UTF-8''"), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new DataValidationException("文件服务返回文件名错误");
    }
    InputStream stream = response.body().asInputStream();
    FileData fileData = new FileData();
    fileData.setFileId(id);
    fileData.setFileName(fileName);
    fileData.setStream(stream);
    return fileData;
  }

  /**
   * 删除文件
   *
   * @param id
   */
  @RequestMapping(method = RequestMethod.DELETE, value = "/api/v1/files/{id}")
  ResponseBodyWrapper<String> deleteFiles(@PathVariable String... id);

  /**
   * 新增文件
   *
   * @param files
   * @return
   */
  @RequestMapping(method = RequestMethod.POST, value = "/api/v1/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseBodyWrapper<List<FileData>> addFile(@RequestParam(value = "type") Integer type, @RequestPart(value = "file") MultipartFile... files);
}
