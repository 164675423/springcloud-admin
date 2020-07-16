package com.zh.health;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class HealthApplication {

  public static void main(String[] args) {
    SpringApplication.run(HealthApplication.class, args);
  }

}
