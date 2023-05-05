package com.api.moments.services.like;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.persistence.repositories.LikeRepository;
import com.api.moments.persistence.repositories.MomentRepository;
import com.api.moments.services.moment.MomentService;
import com.api.moments.services.security.IJwtService;
import com.api.moments.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LikeService implements ILikeService {
  @Autowired
  private MomentService momentService;

  @Autowired
  private MomentRepository momentRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private IJwtService jwtService;

  @Autowired
  private LikeRepository likeRepository;


  @Override
  public Optional<Moment> addLike(String token, UUID momentId) {
    UUID userId = this.jwtService.getUserId(token);
    Optional<Moment> moment = this.momentRepository.findById(momentId);

    if (moment.isPresent()) {
      Moment moment1 = moment.get();
      if (!moment1.getLikes().contains(userId)) {
        //        moment1.getLikes().add(userId);
        moment1.addLike(userId.toString());
        this.momentRepository.save(moment1);
      }
    } else {
      throw new RuntimeException("Moment not found");
    }
    return moment;
  }

  @Override
  public void removeLike(UUID userId, UUID momentId) {
    Optional<Moment> moment = momentRepository.findById(momentId);
    if (moment.isPresent()) {
      Moment moment1 = moment.get();
      if (moment1.getLikes().contains(userId)) {
        moment1.getLikes().remove(userId);
        this.momentRepository.save(moment1);

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
