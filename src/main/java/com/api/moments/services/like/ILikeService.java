package com.api.moments.services.like;

import java.util.UUID;

public interface ILikeService {
  void addLike(String token, UUID momentId);

  void removeLike(UUID userId, UUID momentId);

  boolean hasLiked(UUID userId, UUID momentId);

}

