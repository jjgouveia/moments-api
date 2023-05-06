package com.api.moments.persistence.entities;

import com.api.moments.util.Timestamp;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
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
  private LocalDateTime date;

  public Like(UUID userId, UUID momentId) {
    this.setId();
    this.setUserId(userId);
    this.setMomentId(momentId);
    this.date = Timestamp.getTimestamp();
  }

  public void setId() {
    this.id = UUID.randomUUID();
  }
}
