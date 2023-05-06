package com.api.moments.services.like;

import java.util.UUID;

public interface ILikeService {
  void addLike(String token, UUID momentId);

  void removeLike(String token, UUID momentId);

  boolean hasLiked(String userId, UUID momentId);

}

