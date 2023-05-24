package com.api.moments.services.profile;


import com.api.moments.persistence.entities.Profile;
import com.api.moments.persistence.entities.User;
import com.api.moments.persistence.repositories.ProfileRepository;
import com.api.moments.services.fileUpload.IFileUploadService;
import com.api.moments.services.user.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Service
public class ProfileService implements IProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private IFileUploadService fileUploadService;


    @Override
    public ProfileResponse getProfileByUsername(String username) {
        Profile profile = this.profileRepository.findByUsername(username);

        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (profile == null) {
            throw new RuntimeException("Profile not found");
        }

        ProfileResponse response = new ProfileResponse();
        response.setId(profile.getId());
        response.setBio(profile.getBio());
        response.setBirthday(profile.getBirthday());
        response.setLocation(profile.getLocation());
        response.setName(profile.getName());
        response.setProfilePicture(profile.getProfilePicture());
        response.setWebsite(profile.getWebsite());
        response.setUser(new UserResponse(user));


        return response;
    }

    @Override
    public ProfileResponse getProfileByUserId(UUID userId) {
        var profile = this.profileRepository.findByUserId(userId);

        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        if (profile == null) {
            throw new RuntimeException("Profile not found");
        }

        ProfileResponse response = new ProfileResponse();
        response.setId(profile.getId());
        response.setUsername(profile.getUsername());
        response.setBio(profile.getBio());
        response.setBirthday(profile.getBirthday());
        response.setLocation(profile.getLocation());
        response.setName(profile.getName());
        response.setProfilePicture(profile.getProfilePicture());
        response.setWebsite(profile.getWebsite());
        response.setUser(new UserResponse(user));


        return response;
    }

    @Override
    public ProfileResponse createProfile(ProfileRequest profileRequest, MultipartFile image) {

        if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
            throw new RuntimeException("Only images are supported!");
        }

        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        var imageUri = "";

        var profile = new Profile(user, profileRequest.getName(), profileRequest.getProfilePicture(),
                profileRequest.getBio(), profileRequest.getLocation(), profileRequest.getWebsite(),
                profileRequest.getBirthday());

        try {
            var fileName = profile.getId() + Objects.requireNonNull(image.getOriginalFilename())
                    .substring(image.getOriginalFilename().lastIndexOf(".") + 1);

            imageUri = fileUploadService.upload(image, fileName);
            profile.setProfilePicture(imageUri);
            this.profileRepository.save(profile);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        return new ProfileResponse(profile);

    }

    @Override
    public ProfileResponse updateProfile(ProfileRequest profileRequest, MultipartFile image) {

        if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
            throw new RuntimeException("Only images are supported!");
        }

        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        var imageUri = "";

        var profile = this.profileRepository.findByUserId(user.getId());

        if (profile == null) {
            return this.createProfile(profileRequest, image);
        }

        profile.setName(profileRequest.getName());
        profile.setBio(profileRequest.getBio());
        profile.setLocation(profileRequest.getLocation());
        profile.setWebsite(profileRequest.getWebsite());
        profile.setBirthday(profileRequest.getBirthday());

        try {
            var fileName = profile.getId() + Objects.requireNonNull(image.getOriginalFilename())
                    .substring(image.getOriginalFilename().lastIndexOf(".") + 1);

            imageUri = fileUploadService.upload(image, fileName);
            profile.setProfilePicture(imageUri);
            this.profileRepository.save(profile);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }

        return new ProfileResponse(profile);
    }
}
