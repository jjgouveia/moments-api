package com.api.moments.services.feed;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.persistence.entities.User;
import com.api.moments.services.moment.MomentService;
import com.api.moments.services.security.IJwtService;
import com.api.moments.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FeedService implements IFeedService {

  @Autowired
  private IJwtService jwtService;

  @Autowired
  private UserService userService;

  @Autowired
  private MomentService momentService;

  @Override
  public List<Moment> getFeed(String token) {

    UUID userId = this.jwtService.getUserId(token);
    User user = this.userService.getUserById(userId);

    List<UUID> followingIds = user.getFollowing();

    List<Moment> feed = new ArrayList<>();
    for (UUID followingId : followingIds) {
      List<Moment> moments = this.momentService.getMomentsByOrderDescThroughUserId(followingId);
      feed.addAll(moments);
    }

    feed.sort((m1, m2) -> m2.getDate().compareTo(m1.getDate()));
    return feed;

  }

}
