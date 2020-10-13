package com.zh.am.config.feign;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.form.ContentType;
import feign.form.MultipartFormContentProcessor;
import feign.form.spring.SpringFormEncoder;
import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;


/**
 * 对SpringFormEncoder的扩展，使其支持multipartFile[]的形式
 *
 * @author zh
 * @date 2020/5/1
 */
public class FeignSpringFormEncoder extends SpringFormEncoder {
  public FeignSpringFormEncoder(Encoder delegate) {
    super(delegate);
    MultipartFormContentProcessor processor = (MultipartFormContentProcessor) getContentProcessor(ContentType.MULTIPART);
    processor.addWriter(new SpringSingleMultipartFileWriter());
    processor.addWriter(new SpringManyMultipartFilesWriter());
  }

  @Override
  public void encode(Object object, Type bodyType, RequestTemplate template) throws EncodeException {
    // 单MultipartFile直接使用SpringFormEncoder中的encode方法
    if (bodyType.equals(MultipartFile.class)) {
      MultipartFile file = (MultipartFile) object;
      Map data = Collections.singletonMap(file.getName(), object);
      super.encode(data, MAP_STRING_WILDCARD, template);
      return;
    } else if (bodyType.equals(MultipartFile[].class)) {
      // MultipartFile数组处理
      MultipartFile[] file = (MultipartFile[]) object;
      if (file != null) {
        Map data = Collections.singletonMap(file.length == 0 ? "" : file[0].getName(), object);
        super.encode(data, MAP_STRING_WILDCARD, template);
        return;
      }
    }
    super.encode(object, bodyType, template);
  }
}
