package com.api.moments.services.moment;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.persistence.repositories.MomentRepository;
import com.api.moments.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MomentServiceImpl implements MomentService {

  @Autowired
  private MomentRepository momentRepository;

  @Autowired
  private IEventService eventService;

  @Override
  public Moment create(CreateMomentRequest createMomentRequest) {

    Moment moment = new Moment(createMomentRequest.getTitle(), createMomentRequest.getDescription(),
        createMomentRequest.getImage());

    this.eventService.send("new-post", "Moment created: " + moment);
    return this.momentRepository.save(moment);
  }

  @Override
  public List<Moment> getAll() {
    return this.momentRepository.findAll();
  }
}
