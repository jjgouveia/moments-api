package com.api.moments.persistence.repositories;

import com.api.moments.persistence.entities.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FollowRepository extends MongoRepository<Follow, UUID> {
}
