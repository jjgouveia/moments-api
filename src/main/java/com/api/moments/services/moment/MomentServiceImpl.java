package com.api.moments.services.moment;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.persistence.repositories.MomentRepository;
import com.api.moments.services.messaging.IEventService;
import com.api.moments.services.moment.request.CreateMomentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class MomentServiceImpl implements MomentService {
  @Autowired
  private MomentRepository momentRepository;
  @Autowired
  private IEventService eventService;

  @Override
  public Moment create(CreateMomentRequest createMomentRequest) {

    Moment moment = new Moment(createMomentRequest.getTitle(), createMomentRequest.getDescription(),
        createMomentRequest.getImage(), createMomentRequest.getUserId());

    this.eventService.send("new-post", "Moment created: " + moment);
    return this.momentRepository.save(moment);
  }

  @Override
  public List<Moment> getAll() {
    return this.momentRepository.findAll();
  }

  @Override
  public Moment getById(UUID id) {
    return this.momentRepository.findById(id).orElse(null);
  }

  @Override
  public void update(UUID id, Moment updateMomentRequest) {
    Moment moment = this.momentRepository.findById(id).orElse(null);

    if (moment != null) {
      moment.setTitle(updateMomentRequest.getTitle());
      moment.setDescription(updateMomentRequest.getDescription());
      moment.setImage(updateMomentRequest.getImage());
      moment.setLikes(updateMomentRequest.getLikes());
      moment.setComments(updateMomentRequest.getComments());

      this.momentRepository.save(moment);
    } else {
      throw new RuntimeException("Moment not found");
    }
  }

  @Override
  public List<Moment> getMomentsByOrderDescThroughUserId(UUID userId) {
    return this.momentRepository.findByUserIdOrderByDateDesc(userId);
  }
}
