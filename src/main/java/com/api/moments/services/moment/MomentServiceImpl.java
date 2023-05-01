package com.api.moments.services.moment;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.persistence.repositories.MomentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MomentServiceImpl implements MomentService {

  @Autowired
  private MomentRepository momentRepository;

  @Override
  public Moment create(CreateMomentRequest createMomentRequest) {
    Moment moment = new Moment(createMomentRequest.getTitle(), createMomentRequest.getDescription(),
        createMomentRequest.getImage());
    return this.momentRepository.save(moment);


  }

  @Override
  public List<Moment> getAll() {
    return this.momentRepository.findAll();
  }
}
