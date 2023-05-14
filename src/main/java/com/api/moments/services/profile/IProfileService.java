package com.api.moments.services.profile;

import org.springframework.web.multipart.MultipartFile;

public interface IProfileService {
  ProfileResponse getProfile(String userId);

  ProfileResponse createProfile(ProfileRequest profileRequest, MultipartFile image);
}
