package com.api.moments.services;

import com.api.moments.persistence.entities.Moment;

import java.util.List;

public interface MomentService {
  Moment create(Moment moment);

  List<Moment> getAll();
}
