package com.api.moments.services.comment;

import com.api.moments.services.comment.response.CommentResponse;

import java.util.List;
import java.util.UUID;

public interface ICommentService {

  List<CommentResponse> getComments(UUID momentId);

  CommentResponse createComment(String content, String token, UUID momentId);

  void deleteComment(String token, UUID commentId, UUID momentId);

  void updateComment(String token, UUID commentId, UUID momentId, String content);
}
