package com.api.moments.services.feed;

import com.api.moments.persistence.entities.User;
import com.api.moments.services.moment.MomentService;
import com.api.moments.services.moment.response.MomentResponse;
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
    private MomentService momentService;

    @Override
    public List<MomentResponse> getFeed(String token) {
        User user = this.userService.getUserById(UUID.fromString(token));
        List<UUID> followingIds = user.getFollowing();
        List<MomentResponse> feed = new ArrayList<>();
        for (UUID followingId : followingIds) {
            List<MomentResponse> moments = this.momentService.getMomentsByOrderDescThroughUserId(followingId);
            feed.addAll(moments);
        }

        MomentResponse momentReponse = new MomentResponse();


        feed.sort((m1, m2) -> m2.getDate().compareTo(m1.getDate()));
        return feed;

    }

}
