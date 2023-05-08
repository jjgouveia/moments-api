package com.api.moments.controllers;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.services.feed.FeedService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("moments/feed")
public class FeedController {
  @Autowired
  private FeedService feedService;

  @GetMapping
  public ResponseEntity<List<Moment>> getFeed(HttpServletRequest request) {
    try {
      var feed = feedService.getFeed(request.getAttribute("userId").toString());
      return ResponseEntity.status(HttpStatus.OK).body(feed);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
