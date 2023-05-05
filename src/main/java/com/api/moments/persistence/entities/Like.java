package com.api.moments.persistence.entities;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class Like {
  @Id
  private UUID id;
  @NotNull
  private UUID userId;
  @NotNull
  private UUID momentId;
}
