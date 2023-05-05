package com.api.moments.controllers;

import com.api.moments.services.like.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/moment/like")
public class LikeController {
  @Autowired
  private ILikeService likeService;

  @PostMapping("/{momentId}")
  public ResponseEntity<String> addLike(@PathVariable UUID momentId,
      @RequestHeader("Authorization") String token) {
    try {
      likeService.addLike(token, momentId);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
}
