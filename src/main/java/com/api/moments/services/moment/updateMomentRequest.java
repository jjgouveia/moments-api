package com.api.moments.services.moment;

import lombok.Data;

@Data
public class updateMomentRequest {
  private String title;
  private String description;
  private String image;
}
