package com.api.moments.persistence.repositories;

import com.api.moments.persistence.entities.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface LikeRepository extends MongoRepository<Like, UUID> {
  Like findByMomentIdAndUserId(UUID momentId, UUID userId);
}
