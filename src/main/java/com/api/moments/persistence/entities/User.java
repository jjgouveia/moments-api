package com.api.moments.persistence.entities;

import com.api.moments.util.Timestamp;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
@Data
public class User {
  @Id
  private UUID id;
  private String username;
  private String email;
  private String password;
  private String role;
  private List<UUID> followers;
  private List<UUID> following;
  @CreatedDate
  private LocalDateTime date;

  public User(String username, String email) {
    this.setId();
    this.username = username;
    this.email = email;
    this.setRole("USER");
    this.followers = new ArrayList<>();
    this.following = new ArrayList<>();
    this.date = Timestamp.getTimestamp();
  }

  public void setId() {
    this.id = UUID.randomUUID();
  }


}
