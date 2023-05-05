package com.api.moments.services.moment;

import com.api.moments.persistence.entities.Moment;

import java.util.List;
import java.util.UUID;

public interface MomentService {
  Moment create(CreateMomentRequest createMomentRequest);

  List<Moment> getAll();

  Moment getById(UUID id);

  void update(UUID id, Moment updateMomentRequest);
}
