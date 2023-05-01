package com.api.moments.util;

import org.bson.types.Binary;
import org.springframework.core.convert.converter.Converter;

public class BinaryToStringConverter implements Converter<Binary, String> {

  @Override
  public String convert(Binary source) {
    return new String(source.getData());
  }
}

