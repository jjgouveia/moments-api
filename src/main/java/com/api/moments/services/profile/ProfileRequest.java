package com.api.moments.services.profile;

import lombok.Data;

@Data
public class ProfileRequest {
    private String name;
    private String profilePicture;
    private String bio;
    private String location;
    private String website;
    private String birthday;

    public ProfileRequest(String name, String bio,
                          String location, String website, String birthday) {
        this.name = name;
        this.profilePicture = getProfilePicture();
        this.bio = bio;
        this.location = location;
        this.website = website;
        this.birthday = birthday;
    }
}
