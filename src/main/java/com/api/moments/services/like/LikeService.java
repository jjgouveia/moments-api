package com.api.moments.services.like;

import com.api.moments.persistence.entities.Like;
import com.api.moments.persistence.entities.Moment;
import com.api.moments.persistence.repositories.LikeRepository;
import com.api.moments.services.moment.MomentService;
import com.api.moments.services.security.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LikeService implements ILikeService {
  @Autowired
  private MomentService momentService;

  @Autowired
  private IJwtService jwtService;

  @Autowired
  private LikeRepository likeRepository;


  @Override
  public void addLike(String token, UUID momentId) {
    UUID userId = this.jwtService.getUserId(token);
    Optional<Moment> moment = Optional.ofNullable(this.momentService.getById(momentId));

    if (moment.isPresent()) {
      Moment moment1 = moment.get();
      if (!moment1.getLikes().contains(userId)) {
        moment1.getLikes().add(userId);
        Like like = new Like(userId, momentId);
        this.likeRepository.save(like);
        this.momentService.update(momentId, moment1);
      }
    } else {
      throw new RuntimeException("Moment not found");
    }
  }

  @Override
  public void removeLike(String token, UUID momentId) {
    UUID userId = this.jwtService.getUserId(token);
    Optional<Moment> moment = Optional.ofNullable(this.momentService.getById(momentId));

    if (moment.isPresent()) {
      Moment moment1 = moment.get();
      if (moment1.getLikes().contains(userId)) {
        moment1.getLikes().remove(userId);
        Like like = this.likeRepository.findByMomentIdAndUserId(momentId, userId);
        this.likeRepository.delete(like);
        this.momentService.update(momentId, moment1);
      }
    } else {
      throw new RuntimeException("Moment not found");
    }
  }

  @Override
  public boolean hasLiked(String token, UUID momentId) {
    UUID userId = this.jwtService.getUserId(token);
    Optional<Moment> moment = Optional.ofNullable(this.momentService.getById(momentId));

    if (moment.isPresent()) {
      Moment moment1 = moment.get();
      return moment1.getLikes().contains(userId);
    } else {
      throw new RuntimeException("Moment not found");
    }
  }
}
