package com.zh.storage.api;

import com.google.common.base.Strings;
import com.zh.storage.constant.Enums;
import com.zh.storage.domain.vo.FileVO;
import com.zh.storage.exception.BusinessException;
import com.zh.storage.service.FileStorageService;
import com.zh.web.contract.ResponseBodyWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
  @GetMapping(value = "{id}")
  public ResponseEntity<InputStreamResource> getFile(@PathVariable String id) throws IOException {
    List<FileVO> files = fileStorageService.getFiles(id);
    FileVO file = files.get(0);
    MediaType contentType;
    try {
      contentType = MediaType.valueOf(file.getContentType());
    } catch (InvalidMediaTypeException ex) {
      contentType = MediaType.APPLICATION_OCTET_STREAM;
    }
    HttpHeaders headers = new HttpHeaders();
    //http header为Content-Disposition时，控制用户请求所得的内容存为一个文件的时候提供一个默认的文件名
    headers.setContentType(contentType);
    headers.setContentLength(file.getSize());
    headers.set(HttpHeaders.CONTENT_DISPOSITION, getAttachmentValue(file.getFileName()));
    return new ResponseEntity<>(new InputStreamResource(file.getInputStream()), headers, HttpStatus.OK);
  }

  /**
   * 输出 Content-Disposition 附件格式的值，包括 UTF-8 编码的文件名.
   *
   * @param fileName 文件名称
   * @return Http Header Value
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Content-Disposition">Content-Disposition on MDN</a>
   */
  private String getAttachmentValue(String fileName) {
    StringBuilder builder = new StringBuilder("attachment");
    if (!Strings.isNullOrEmpty(fileName)) {
      builder.append("; filename=\"").append(fileName).append('\"');
      try {
        builder.append("; filename*=UTF-8''").append(URLEncoder.encode(fileName, "UTF-8"));
      } catch (UnsupportedEncodingException ex) {
        // silent ignore this error
      }
    }
    return builder.toString();
  }

  /**
   * TODO swagger ui构建MultipartFile[]时无法传参，暂时使用postman进行调试
   *
   * @param files
   * @return
   * @throws IOException
   */

  @ApiOperation(value = "上传文件信息")
  @PostMapping
  public ResponseBodyWrapper<List<FileVO>> uploadFiles(@RequestParam(value = "type", required = false) Integer type,
                                                       @RequestParam(value = "file") MultipartFile... files) throws IOException {
    if (files.length == 0) {
      throw new BusinessException("上传的文件不可为空");
    }
    if (type == null) {
      type = Enums.FileType.Domain.getIndex();
    }
    List<FileVO> fileVOS = fileStorageService.addFiles(type, files);
    return new ResponseBodyWrapper<>(fileVOS);
  }

  @ApiOperation(value = "删除文件")
  @DeleteMapping("{id}")
  public ResponseBodyWrapper delete(@PathVariable String... id) throws IOException {
    fileStorageService.deleteFiles(id);
    return new ResponseBodyWrapper("success");
  }
}
