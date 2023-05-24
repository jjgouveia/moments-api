package com.api.moments.services.feed;

import com.api.moments.persistence.entities.User;
import com.api.moments.services.moment.MomentService;
import com.api.moments.services.moment.response.MomentResponse;
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
  private UserService userService;

  @Autowired
  private IJwtService jwtService;

  @Autowired
  private MomentService momentService;

  @Override
  public List<MomentResponse> getFeed(String token, int page, int pageSize) {
    UUID userId = this.jwtService.getUserId(token);
    User user = this.userService.getUserById(userId);
    List<UUID> followingIds = user.getFollowing();
    followingIds.add(user.getId());
    List<MomentResponse> feed = new ArrayList<>();

    if (page <= 0 || pageSize < 1) {
      page = 1;
      pageSize = 4;
    }

    int startIndex = (page - 1) * pageSize;
    int endIndex = startIndex + pageSize;

    for (UUID followingId : followingIds) {
      List<MomentResponse> moments =
          this.momentService.getMomentsByOrderDescThroughUserId(followingId);
      feed.addAll(moments);
    }

    feed.sort((m1, m2) -> m2.getDate().compareTo(m1.getDate()));

    if (endIndex > feed.size()) {
      endIndex = feed.size();
    }

    return feed.subList(startIndex, endIndex);
  }

}
