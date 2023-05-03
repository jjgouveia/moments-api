package com.api.moments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class MomentsApplication {

  public static void main(String[] args) {
    SpringApplication.run(MomentsApplication.class, args);
  }

}
