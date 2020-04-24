package com.zh.storage.config;

import com.zh.web.contract.ResponseBodyWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 集成swagger ui的相关配置
 * TODO: api参数排序，swagger默认的是按照字母排序,实际想要的结果应是参数顺序排序
 *
 * @author zh
 * @date 2020/1/12
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

  @Value("${spring.application.name}")
  public String appName;

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .pathMapping("/")
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.zh.storage.api"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(new ApiInfoBuilder()
            .title(appName)
            .build())
        .consumes(getAllContentTypes())
        .genericModelSubstitutes(ResponseBodyWrapper.class)
        .directModelSubstitute(LocalDateTime.class, String.class);
  }

  private Set<String> getAllContentTypes() {
    Set<String> consumes = new HashSet<>();
    // Add other media types if required in future
    consumes.add("multipart/form-data");
    return consumes;
  }
}
