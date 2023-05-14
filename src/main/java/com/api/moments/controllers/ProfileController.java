package com.api.moments.controllers;


import com.api.moments.services.profile.IProfileService;
import com.api.moments.services.profile.ProfileRequest;
import com.api.moments.services.profile.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ProfileController extends BaseController {

  @Autowired
  private IProfileService profileService;

  @PostMapping("/profile")
  public ResponseEntity<ProfileResponse> createProfile(@RequestHeader("Authorization") String token,
      @RequestParam("image") MultipartFile image, @RequestParam("name") String name,
      @RequestParam("bio") String bio, @RequestParam("location") String location,
      @RequestParam("website") String website, @RequestParam("birthday") String birthday) {
    try {
      var request = new ProfileRequest(name, bio, location, website, birthday);
      var profile = profileService.createProfile(request, image);
      return ResponseEntity.ok(profile);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
