package com.api.moments.services.like;

import com.api.moments.persistence.entities.Moment;

import java.util.Optional;
import java.util.UUID;

public interface ILikeService {
  Optional<Moment> addLike(String token, UUID momentId);

  void removeLike(UUID userId, UUID momentId);

  boolean hasLiked(UUID userId, UUID momentId);

}

