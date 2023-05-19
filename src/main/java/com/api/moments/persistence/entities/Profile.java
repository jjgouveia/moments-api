package com.api.moments.persistence.entities;

import com.api.moments.services.user.response.UserResponse;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class Profile {
  @Id
  private UUID id;
  private UserResponse user;
  private String name;
  private String username;
  private String profilePicture;
  private String bio;
  private String location;
  private String website;
  private String birthday;

  public Profile() {
  }


  public Profile(User user, String name, String profilePicture, String bio, String location,
      String website, String birthday) {
    this.setId();
    this.user = new UserResponse(user);
    this.name = name;
    this.username = user.getUsername();
    this.profilePicture = profilePicture;
    this.bio = bio;
    this.location = location;
    this.website = website;
    this.birthday = birthday;
  }


  public void setId() {
    this.id = UUID.randomUUID();
  }
}
