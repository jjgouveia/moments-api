package com.api.moments.models;

import java.time.LocalDate;
import java.util.UUID;

public class MomentModel {

  private UUID id;
  private String title;
  private String description;
  private String image;
  private LocalDate date;
  private String author;
  private String likes;
  private String comments;

  public UUID getId() {
    return id;
  }

  public void setId() {
    this.id = UUID.randomUUID();
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public String getImage() {
    return image;
  }

  public LocalDate getDate() {
    return date;
  }

  public String getAuthor() {
    return author;
  }

  public String getLikes() {
    return likes;
  }

  public String getComments() {
    return comments;
  }

  public void setId(UUID id2) {}
}
