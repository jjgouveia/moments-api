package com.api.moments.services.follow;

import com.api.moments.persistence.entities.Follow;
import com.api.moments.persistence.repositories.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FollowService implements IFollowService {

    @Autowired
    private FollowRepository followRepository;


    @Override
    public void saveFollow(UUID followerId, UUID followingId) {
        this.followRepository.save(new Follow(followerId, followingId));

    }
}
