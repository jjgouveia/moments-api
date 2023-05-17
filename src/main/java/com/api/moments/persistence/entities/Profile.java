package com.api.moments.persistence.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class Profile {
    @Id
    private UUID id;
    private UUID userId;
    private String name;
    private String username;
    private String profilePicture;
    private String bio;
    private String location;
    private String website;
    private String birthday;


    public Profile(UUID userId, String name, String username, String profilePicture, String bio,
                   String location, String website, String birthday) {
        this.setId();
        this.setUserId(userId);
        this.name = name;
        this.username = username;
        this.profilePicture = profilePicture;
        this.bio = bio;
        this.location = location;
        this.website = website;
        this.birthday = birthday;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setId() {
        this.id = UUID.randomUUID();
    }
}
