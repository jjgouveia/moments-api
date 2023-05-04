package com.api.moments.persistence.entities;

import com.api.moments.util.Timestamp;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document
public class Moment {
  @Id
  private UUID id;
  @NotNull
  private String title;
  @NotNull
  private String description;
  @NotNull
  private String image;
  @NotNull
  private UUID userId;

  private int likes;

  @CreatedDate
  private LocalDateTime date;

  public Moment(String title, String description, String image, UUID userId) {
    this.setId();
    this.date = Timestamp.getTimestamp();
    this.title = title;
    this.description = description;
    this.image = image;
    this.setUserId(userId);
    this.likes = 0;
  }

  public UUID getId() {
    return id;
  }

  public void setId() {
    this.id = UUID.randomUUID();
  }
}
