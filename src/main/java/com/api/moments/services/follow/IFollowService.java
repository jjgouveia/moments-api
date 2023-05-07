package com.api.moments.services.follow;

import java.util.UUID;

public interface IFollowService {

    void saveFollow(UUID followerId, UUID followingId);

    void deleteFollow(UUID followerId, UUID id);
    
}
