package com.api.moments.persistence.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Data
@Document
public class Moment {
  @Id
  private UUID id;
  private String title;
  private String description;
  @CreatedDate
  private LocalDateTime date;

  public Moment() {
    this.setId();
    this.date = this.getTimestamp();
  }

  private LocalDateTime getTimestamp() {
    ZonedDateTime utcDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
    ZonedDateTime brDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("America/Sao_Paulo"));
    LocalDateTime brLocalDateTime = brDateTime.toLocalDateTime();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    String brDateTimeStr = brLocalDateTime.format(formatter);

    return LocalDateTime.parse(brDateTimeStr, formatter);
  }

  public UUID getId() {
    return id;
  }

  public void setId() {
    this.id = UUID.randomUUID();
  }
}
