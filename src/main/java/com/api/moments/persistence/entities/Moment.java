package com.api.moments.persistence.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class Moment {
  @Id
  private UUID id;
  private String title;
  private String description;

  public Moment() {
    setId();
  }

  public UUID getId() {
    return id;
  }

  public void setId() {
    this.id = UUID.randomUUID();
  }
}
