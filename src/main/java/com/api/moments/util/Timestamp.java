package com.api.moments.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Timestamp {
  public static LocalDateTime getTimestamp() {
    ZonedDateTime utcDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
    ZonedDateTime brDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
    LocalDateTime brLocalDateTime = brDateTime.toLocalDateTime();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    String brDateTimeStr = brLocalDateTime.format(formatter);

    return LocalDateTime.parse(brDateTimeStr, formatter);
  }
}
