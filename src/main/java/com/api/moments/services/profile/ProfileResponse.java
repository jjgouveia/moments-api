package com.api.moments.services.profile;

import com.api.moments.persistence.entities.Profile;
import com.api.moments.services.user.response.UserResponse;
import lombok.Data;

import java.util.UUID;

@Data
public class ProfileResponse {
    private UUID id;
    private UserResponse user;
    private String username;
    private String name;
    private String profilePicture;
    private String bio;
    private String location;
    private String website;
    private String birthday;

    public ProfileResponse() {
    }

    public ProfileResponse(Profile profile) {
        this.id = profile.getId();
        this.user = profile.getUser();
        this.name = profile.getName();
        this.username = profile.getUsername();
        this.profilePicture = profile.getProfilePicture();
        this.bio = profile.getBio();
        this.location = profile.getLocation();
        this.website = profile.getWebsite();
        this.birthday = profile.getBirthday();
    }
}
