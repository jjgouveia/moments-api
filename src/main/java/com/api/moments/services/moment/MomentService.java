package com.api.moments.services.moment;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.services.moment.request.CreateMomentRequest;
import com.api.moments.services.moment.response.MomentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface MomentService {
  MomentResponse create(CreateMomentRequest createMomentRequest, MultipartFile image);

  List<MomentResponse> getAll();

  MomentResponse getById(UUID id);

  Moment getByIdAux(UUID id);

  void update(UUID id, Moment updateMomentRequest);

  List<MomentResponse> getMomentsByOrderDescThroughUserId(UUID userId);

  void delete(UUID id);

  void updateLikes(UUID id, Moment updateMomentRequest);
}
