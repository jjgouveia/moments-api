package com.api.moments.services.moment;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.services.moment.request.CreateMomentRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MomentService {
  Moment create(CreateMomentRequest createMomentRequest, MultipartFile image);

  List<Moment> getAll();

  Moment getById(UUID id);

  void update(UUID id, Moment updateMomentRequest);

  List<Moment> getMomentsByOrderDescThroughUserId(UUID userId);
}
