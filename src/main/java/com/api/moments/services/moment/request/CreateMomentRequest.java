package com.api.moments.services.moment.request;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateMomentRequest {
  private String title;
  private String description;
  private String imageUrl;
  private UUID userId;

  public CreateMomentRequest(String title, String description) {
    this.setUserId();
    this.title = title;
    this.description = description;
    this.setImageUrl(this.getImageUrl());
  }

  public void setUserId() {
    this.userId = UUID.randomUUID();
  }


}
