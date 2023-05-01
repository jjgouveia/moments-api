package com.api.moments.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.Arrays;

@Configuration
public class MongoConfig {

  @Bean
  public MongoCustomConversions mongoCustomConversions() {
    Converter[] converters = {new BinaryToStringConverter()};
    return new MongoCustomConversions(Arrays.asList(converters));
  }

  public MongoMappingContext mongoMappingContext() {
    return new MongoMappingContext();
  }
}
