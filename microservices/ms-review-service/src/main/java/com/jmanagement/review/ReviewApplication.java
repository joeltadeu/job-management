package com.jmanagement.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.jmanagement")
@EnableFeignClients
public class ReviewApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReviewApplication.class, args);
  }
}
