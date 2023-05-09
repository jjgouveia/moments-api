package com.api.moments.services.comment;

import java.util.UUID;

public interface ICommentService {
  void createComment(String content, String token, UUID momentId);

  void deleteComment(String token, UUID commentId, UUID momentId);

  void updateComment(String token, UUID commentId, UUID momentId, String content);
}
