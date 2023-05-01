package com.api.moments.services.moment;

import com.api.moments.persistence.entities.Moment;

import java.util.List;

public interface MomentService {
  Moment create(CreateMomentRequest createMomentRequest);

  List<Moment> getAll();
}
