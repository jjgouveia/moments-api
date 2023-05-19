package com.api.moments.services.profile;


import com.api.moments.persistence.entities.Profile;
import com.api.moments.persistence.entities.User;
import com.api.moments.persistence.repositories.ProfileRepository;
import com.api.moments.services.fileUpload.IFileUploadService;
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
    var profile = this.profileRepository.findByUsername(username);

    if (profile == null) {
      throw new RuntimeException("Profile not found");
    }

    return new ProfileResponse(profile);
  }

  @Override
  public ProfileResponse getProfileByUserId(UUID userId) {
    var profile = this.profileRepository.findByUserId(userId);

    if (profile == null) {
      throw new RuntimeException("Profile not found");
    }

    return new ProfileResponse(profile);
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
}
