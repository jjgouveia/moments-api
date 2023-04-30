package com.api.moments.services;

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
  public Moment create(Moment moment) {
    return this.momentRepository.save(moment);
  }

  @Override
  public List<Moment> getAll() {
    return this.momentRepository.findAll();
  }
}
