package com.api.moments.services.moment;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.persistence.entities.User;
import com.api.moments.persistence.repositories.MomentRepository;
import com.api.moments.services.fileUpload.IFileUploadService;
import com.api.moments.services.messaging.IEventService;
import com.api.moments.services.moment.request.CreateMomentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class MomentServiceImpl implements MomentService {
  @Autowired
  private MomentRepository momentRepository;
  @Autowired
  private IEventService eventService;

  @Autowired
  private IFileUploadService fileUploadService;

  @Override
  public Moment create(CreateMomentRequest createMomentRequest, MultipartFile image) {

    User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

    var imageUri = "";

    Moment moment = new Moment(createMomentRequest.getTitle(), createMomentRequest.getDescription(),
        createMomentRequest.getImageUrl(), createMomentRequest.getUserId());

    try {
      var fileName = user.getId() + Objects.requireNonNull(image.getOriginalFilename())
          .substring(image.getOriginalFilename().lastIndexOf(".") + 1);

      imageUri = fileUploadService.upload(image, fileName);
      moment.setImageUrl(imageUri);
      this.momentRepository.save(moment);
    } catch (Exception e) {
      imageUri = "";
    }
    return moment;
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
      moment.setImageUrl(updateMomentRequest.getImageUrl());
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
