package com.api.moments.services.moment;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.persistence.repositories.MomentRepository;
import com.api.moments.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class MomentServiceImpl implements MomentService {



  private final UUID mockUserId = UUID.fromString("9fee7c16-d941-4a61-9297-710e0bc4a73a");
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


}
