package com.api.moments.persistence.repositories;

import com.api.moments.persistence.entities.Comment;
import com.api.moments.services.comment.response.CommentResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends MongoRepository<Comment, UUID> {
  Comment findByMomentIdAndUserId(UUID momentId, UUID userId);

  List<CommentResponse> findByMomentId(UUID momentId);
}
