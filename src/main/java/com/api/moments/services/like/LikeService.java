package com.api.moments.services.like;

import com.api.moments.persistence.entities.Moment;
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


  @Override
  public void addLike(String token, UUID momentId) {
    UUID userId = this.jwtService.getUserId(token);
    Optional<Moment> moment = Optional.ofNullable(this.momentService.getById(momentId));

    if (moment.isPresent()) {
      Moment moment1 = moment.get();
      if (!moment1.getLikes().contains(userId)) {
        //        moment1.getLikes().add(userId);
        moment1.addLike(userId.toString());
        this.momentService.update(momentId, moment1);
      }
    } else {
      throw new RuntimeException("Moment not found");
    }
  }

  @Override
  public void removeLike(UUID userId, UUID momentId) {
    Optional<Moment> moment = Optional.ofNullable(this.momentService.getById(momentId));

    if (moment.isPresent()) {
      Moment moment1 = moment.get();
      if (moment1.getLikes().contains(userId)) {
        moment1.getLikes().remove(userId);
        this.momentService.update(momentId, moment1);

      }
    } else {
      throw new RuntimeException("Moment not found");
    }
  }

  @Override
  public boolean hasLiked(UUID userId, UUID momentId) {
    Moment optionalMoment = this.momentService.getById(momentId);
    if (optionalMoment != null && optionalMoment.getId().toString().equals(momentId.toString())) {
      Moment moment = optionalMoment;
      return moment.getLikes().contains(userId.toString());
    } else {
      throw new RuntimeException("Moment not found");
    }
  }
}
