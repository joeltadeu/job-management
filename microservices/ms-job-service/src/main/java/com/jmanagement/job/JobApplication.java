package com.jmanagement.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.jmanagement")
@EnableFeignClients
public class JobApplication {

  public static void main(String[] args) {
    SpringApplication.run(JobApplication.class, args);
  }
}
