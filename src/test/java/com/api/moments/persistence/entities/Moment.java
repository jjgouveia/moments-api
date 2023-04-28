package com.api.moments.persistence.entities;

import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Moment {

  @Id
  private UUID id;

  private String title;
  private String description;

  // public Moments(
  //   String title,
  //   String description,
  //   String image,
  //   LocalDate date,
  //   String author,
  //   String likes,
  //   String comments
  // ) {
  //   this.setId();
  //   this.title = title;
  //   this.description = description;
  //   this.image = image;
  //   this.date = date;
  //   this.author = author;
  //   this.likes = likes;
  //   this.comments = comments;
  // }

  public UUID getId() {
    return id;
  }

  protected void setId() {
    this.id = UUID.randomUUID();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
