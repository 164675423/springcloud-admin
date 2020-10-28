package com.zh.am.config.application;

import com.google.common.collect.Lists;
import com.zh.common.context.LoginUser;
import com.zh.common.contract.ResponseBodyWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

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
  public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
  public static final String AUTHORIZATION_HEADER = "X-USER-ID";

  @Value("${spring.application.name}")
  public String appName;

  @Bean
  public Docket docket() {
    return new Docket(DocumentationType.SWAGGER_2)
        .pathMapping("/")
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.zh.am.api"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(new ApiInfoBuilder()
            .title(appName)
            .description("X-USER-ID:  c7426bca-e69c-4072-b48f-6f259cb0d48b")
            .build())
        .securitySchemes(Arrays.asList(authorization()))
        .securityContexts(Arrays.asList(securityContext()))
        .ignoredParameterTypes(LoginUser.class)
        .genericModelSubstitutes(ResponseBodyWrapper.class)
        .directModelSubstitute(LocalDateTime.class, String.class);
  }

  private ApiKey authorization() {
    return new ApiKey(AUTHORIZATION_HEADER, AUTHORIZATION_HEADER, "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .forPaths(regex(DEFAULT_INCLUDE_PATTERN))
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope
        = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Lists.newArrayList(
        new SecurityReference(AUTHORIZATION_HEADER, authorizationScopes));
  }
}
