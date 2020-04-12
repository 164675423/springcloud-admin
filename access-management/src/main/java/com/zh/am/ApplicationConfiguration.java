package com.zh.am;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class ApplicationConfiguration {
  /**
   * 自定义的加密类
   *
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new com.zh.am.authentication.PasswordEncoder();
  }

}
