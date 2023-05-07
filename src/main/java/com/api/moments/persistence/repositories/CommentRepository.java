package com.api.moments.persistence.repositories;

import com.api.moments.persistence.entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends MongoRepository<Comment, UUID> {
    Comment findByMomentIdAndUserId(UUID momentId, UUID userId);

}
