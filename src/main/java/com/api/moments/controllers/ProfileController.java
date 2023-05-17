package com.api.moments.controllers;


import com.api.moments.services.profile.IProfileService;
import com.api.moments.services.profile.ProfileRequest;
import com.api.moments.services.profile.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

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

    @GetMapping("/profile/{username}")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token,
                                        @PathVariable("username") String username) {
        try {
            var profile = profileService.getProfileByUsername(username);
            return ResponseEntity.status(HttpStatus.OK).body(profile);
        } catch (Exception e) {
            if (e.getMessage().equals("Profile not found"))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/profile/userId/{id}")
    public ResponseEntity<?> getProfileByUserId(@RequestHeader("Authorization") String token,
                                                @PathVariable("id") String id) {
        try {
            var profile = profileService.getProfileByUserId(UUID.fromString(id));
            return ResponseEntity.status(HttpStatus.OK).body(profile);
        } catch (Exception e) {
            if (e.getMessage().equals("Profile not found"))
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
