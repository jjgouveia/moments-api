package com.api.moments.services.follow;

import java.util.UUID;

public interface IFollowService {

    void saveFollow(UUID followerId, UUID followingId);

//    void removeFollow(String token, String userId);
//
//    boolean hasFollowed(String userId, String userIdToFollow);
}
