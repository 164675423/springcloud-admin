package com.zh.am;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@EnableDiscoveryClient
//@EnableKafka
@EnableFeignClients
public class AccessManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccessManagementApplication.class, args);
  }
}
