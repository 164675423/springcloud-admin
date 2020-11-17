package com.zh.gateway.authentication.config;

import com.zh.gateway.authentication.auth.DefaultTokenService;
import com.zh.gateway.authentication.auth.PasswordEncoderImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class ApplicationConfiguration {
  @Value("${security.oauth2.resource.jwt.key-value}")
  public String jwtKeyValue;

  @Value("${spring.application.name}")
  public String applicationName;

  /**
   * 注入默认的token service
   *
   * @return
   */
  @Bean
  public DefaultTokenService defaultTokenService() {
    SecretKeySpec key = new SecretKeySpec(jwtKeyValue.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    return new DefaultTokenService(key);
  }

  /**
   * 注入密码处理的实现类
   *
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new PasswordEncoderImpl();
  }
}
