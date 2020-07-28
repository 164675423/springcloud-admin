package com.zh.am;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 程序入口
 *
 * @author zh
 * @date 2020/2/12
 */
@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@EnableEurekaClient
@EnableScheduling
//@EnableKafka
@EnableFeignClients
public class AccessManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccessManagementApplication.class, args);
  }
}
