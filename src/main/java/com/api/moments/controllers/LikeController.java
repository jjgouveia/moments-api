package com.api.moments.controllers;

import com.api.moments.services.like.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class LikeController extends BaseController {
  @Autowired
  private ILikeService likeService;

  @PostMapping("/{momentId}/like")
  public ResponseEntity<String> addLike(@PathVariable UUID momentId,
      @RequestHeader("Authorization") String token) {
    try {
      likeService.addLike(token, momentId);
      return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    } catch (Exception e) {
      if (e.getMessage().equals("You have already liked this moment"))
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }

  @PostMapping("/{momentId}/like/remove")
  public ResponseEntity<String> removeLike(@PathVariable UUID momentId,
      @RequestHeader("Authorization") String token) {
    try {
      likeService.removeLike(token, momentId);
      return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    } catch (Exception e) {
      if (e.getMessage().equals("You have not liked this moment"))
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
  }
}
