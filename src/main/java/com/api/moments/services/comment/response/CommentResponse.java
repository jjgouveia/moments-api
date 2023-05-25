package com.api.moments.services.comment.response;

import com.api.moments.persistence.entities.Comment;
import lombok.Data;

@Data
public class CommentResponse {
  private String id;
  private String content;
  private String createdAt;
  private String userId;
  private String momentId;

  public CommentResponse(Comment comment) {
    this.setId(comment.getId().toString());
    this.setContent(comment.getContent());
    this.setCreatedAt(comment.getCreatedAt().toString());
    this.setUserId(comment.getUserId().toString());
    this.setMomentId(comment.getMomentId().toString());
  }

}
