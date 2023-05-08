package com.api.moments.services.moment.request;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UpdateMomentRequest {
  private String title;
  private String description;
  private String image;
  private List<UUID> likes;
}
