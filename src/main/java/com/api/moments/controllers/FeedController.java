package com.api.moments.controllers;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.services.feed.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("moments/feed")
public class FeedController {
    @Autowired
    private FeedService feedService;

    @GetMapping
    public List<Moment> getFeed(@RequestHeader("Authorization") String userId) {
        var teste = this.feedService.getFeed(userId);
        return teste;
    }
}
