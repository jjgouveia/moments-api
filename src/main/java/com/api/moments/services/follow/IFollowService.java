package com.api.moments.services.follow;

public interface IFollowService {
    void addFollow(String token, String userId);

    void removeFollow(String token, String userId);

    boolean hasFollowed(String userId, String userIdToFollow);
}
