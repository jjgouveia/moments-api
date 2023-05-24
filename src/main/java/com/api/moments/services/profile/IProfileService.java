package com.api.moments.services.profile;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IProfileService {
  ProfileResponse getProfileByUsername(String username);

  ProfileResponse getProfileByUserId(UUID userId);

  ProfileResponse createProfile(ProfileRequest profileRequest, MultipartFile image);

  ProfileResponse updateProfile(ProfileRequest profileRequest, MultipartFile image);
}
