package com.api.moments.services.profile;

import com.api.moments.persistence.entities.Profile;
import lombok.Data;

import java.util.UUID;

@Data
public class ProfileResponse {
    private UUID id;
    private UUID userId;
    private String name;
    private String username;
    private String profilePicture;
    private String bio;
    private String location;
    private String website;
    private String birthday;

    public ProfileResponse(Profile profile) {
        this.id = profile.getId();
        this.userId = profile.getUserId();
        this.username = profile.getUsername();
        this.name = profile.getName();
        this.profilePicture = profile.getProfilePicture();
        this.bio = profile.getBio();
        this.location = profile.getLocation();
        this.website = profile.getWebsite();
        this.birthday = profile.getBirthday();
    }
}
