package com.api.moments.persistence.entities;

import com.api.moments.util.Timestamp;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
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
  @CreatedDate
  private LocalDateTime date;

  public User(String username, String email, String password) {
    this.setId();
    this.username = username;
    this.email = email;
    this.password = password;
    this.setRole("USER");
    this.date = Timestamp.getTimestamp();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public UUID getId() {
    return id;
  }

  public void setId() {
    this.id = UUID.randomUUID();
  }
}
