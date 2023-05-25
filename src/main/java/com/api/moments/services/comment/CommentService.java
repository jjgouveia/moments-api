package com.api.moments.services.comment;

import com.api.moments.persistence.entities.Comment;
import com.api.moments.persistence.entities.Moment;
import com.api.moments.persistence.repositories.CommentRepository;
import com.api.moments.services.comment.response.CommentResponse;
import com.api.moments.services.moment.MomentService;
import com.api.moments.services.security.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService implements ICommentService {
  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private MomentService momentService;

  @Autowired
  private IJwtService jwtService;

  @Override
  public List<CommentResponse> getComments(UUID momentId) {
    return this.commentRepository.findByMomentId(momentId);
  }

  @Override
  public CommentResponse createComment(String content, String token, UUID momentId) {
    UUID userId = this.jwtService.getUserId(token);
    Optional<Moment> comment = Optional.ofNullable(this.momentService.getByIdAux(momentId));

    if (comment.isPresent()) {
      Moment moment = comment.get();
      Comment comment1 = new Comment(content, userId, momentId);
      this.commentRepository.save(comment1);
      moment.getComments().add(comment1);
      this.momentService.update(momentId, moment);
      this.commentRepository.save(comment1);
      return new CommentResponse(comment1);
    } else {
      throw new RuntimeException("Moment not found");
    }
  }

  @Override
  public void deleteComment(String token, UUID commentId, UUID momentId) {
    UUID userId = this.jwtService.getUserId(token);
    Optional<Moment> moment = Optional.ofNullable(this.momentService.getByIdAux(momentId));

    Optional<Comment> comment = this.commentRepository.findById(commentId);

    if (moment.isPresent() && comment.isPresent()) {
      Moment moment1 = moment.get();
      if (comment.get().getUserId().equals(userId)) {
        Comment comment1 = this.commentRepository.findById(comment.get().getId()).orElse(null);
        assert comment1 != null;
        this.commentRepository.delete(comment1);
        moment1.getComments().remove(comment.get());
        this.momentService.update(momentId, moment1);

      } else {
        throw new RuntimeException("You are not the owner of this comment");
      }
    } else {
      throw new RuntimeException("Comment not found");
    }


  }

  @Override
  public void updateComment(String token, UUID commentId, UUID momentId, String content) {
    UUID userId = this.jwtService.getUserId(token);
    Optional<Moment> moment = Optional.ofNullable(this.momentService.getByIdAux(momentId));
    Optional<Comment> comment = this.commentRepository.findById(commentId);


    if (moment.isPresent() && comment.isPresent()) {
      Moment moment1 = moment.get();
      if (comment.get().getUserId().equals(userId)) {
        Comment comment1 = this.commentRepository.findById(comment.get().getId()).orElse(null);
        assert comment1 != null;
        comment1.setContent(content);
        this.commentRepository.save(comment1);
        moment1.getComments().remove(comment.get());
        moment1.getComments().add(comment1);
        this.momentService.update(momentId, moment1);

      } else {
        throw new RuntimeException("You are not the owner of this comment");
      }
    } else {
      throw new RuntimeException("Comment not found");
    }

  }
}
