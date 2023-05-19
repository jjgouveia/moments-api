package com.api.moments.services.moment;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.persistence.entities.User;
import com.api.moments.persistence.repositories.MomentRepository;
import com.api.moments.services.fileUpload.IFileUploadService;
import com.api.moments.services.moment.request.CreateMomentRequest;
import com.api.moments.services.moment.response.MomentResponse;
import com.api.moments.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
public class MomentServiceImpl implements MomentService {
  @Autowired
  private MomentRepository momentRepository;

  @Autowired
  private IFileUploadService fileUploadService;

  @Autowired
  private UserService userService;

  @Override
  public MomentResponse create(CreateMomentRequest createMomentRequest) {

    if (!Objects.requireNonNull(createMomentRequest.getImage().getContentType())
        .startsWith("image/")) {
      throw new RuntimeException("Only images are supported!");
    }

    User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());


    var imageUri = "";

    Moment moment = new Moment(createMomentRequest.getTitle(), createMomentRequest.getDescription(),
        createMomentRequest.getImageUrl(), createMomentRequest.getUserId(), user.getUsername());


    try {
      var fileName = moment.getId() + Objects.requireNonNull(
              createMomentRequest.getImage().getOriginalFilename())
          .substring(createMomentRequest.getImage().getOriginalFilename().lastIndexOf(".") + 1);

      imageUri = fileUploadService.upload(createMomentRequest.getImage(), fileName);
      moment.setImageUrl(imageUri);
      this.momentRepository.save(moment);
    } catch (Exception e) {
      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
    }

    MomentResponse response = new MomentResponse();
    response.setId(moment.getId());
    response.setTitle(moment.getTitle());
    response.setUsername(moment.getUsername());
    response.setDescription(moment.getDescription());
    response.setImageUrl(moment.getImageUrl());
    response.setUserId(moment.getUserId());
    response.setLikes(moment.getLikes());
    response.setComments(moment.getComments());
    response.setDate(moment.getDate());
    this.userService.updateUserMoments(moment.getUserId(), moment.getId());


    return response;
  }

  @Override
  public List<MomentResponse> getAll() {
    List<Moment> moments = this.momentRepository.findAll();
    List<MomentResponse> momentResponses = new ArrayList<>();
    for (Moment moment : moments) {
      momentResponses.add(new MomentResponse(moment));
    }
    return momentResponses;
  }

  @Override
  public List<MomentResponse> getMomentByUsername(String username) {
    return this.momentRepository.findByUsername(username);
  }

  @Override
  public MomentResponse getById(UUID id) {
    Moment moment = this.momentRepository.findById(id).orElse(null);
    assert moment != null;
    return toResponse(moment);
  }

  @Override
  public Moment getByIdAux(UUID id) {
    return this.momentRepository.findById(id).orElse(null);
  }

  @Override
  public void update(UUID id, Moment updateMomentRequest) {
    Moment moment = this.momentRepository.findById(id).orElse(null);
    var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

    if (moment == null)
      throw new RuntimeException("Moment not found");

    moment.setTitle(updateMomentRequest.getTitle());
    moment.setDescription(updateMomentRequest.getDescription());
    moment.setImageUrl(updateMomentRequest.getImageUrl());
    moment.setLikes(updateMomentRequest.getLikes());
    moment.setComments(updateMomentRequest.getComments());

    this.momentRepository.save(moment);
  }


  @Override
  public List<MomentResponse> getMomentsByOrderDescThroughUserId(UUID userId) {
    return this.momentRepository.findByUserIdOrderByDateDesc(userId);
  }

  @Override
  public void delete(UUID id) {
    var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    Moment moment = this.momentRepository.findById(id).orElse(null);

    if (moment == null)
      throw new RuntimeException("Moment not found");

    if (!moment.getUserId().equals(user.getId()))
      throw new RuntimeException("You can't delete this moment");

    this.momentRepository.delete(moment);
  }

  @Override
  public void updateLikes(UUID id, Moment updateMomentRequest) {
    Moment moment = this.momentRepository.findById(id).orElse(null);

    if (moment == null)
      throw new RuntimeException("Moment not found");
    moment.setLikes(updateMomentRequest.getLikes());

    this.momentRepository.save(moment);
  }

  public MomentResponse toResponse(Moment moment) {
    MomentResponse response = new MomentResponse();
    assert moment != null;
    response.setId(moment.getId());
    response.setTitle(moment.getTitle());
    response.setDescription(moment.getDescription());
    response.setUsername(moment.getUsername());
    response.setImageUrl(moment.getImageUrl());
    response.setUserId(moment.getUserId());
    response.setLikes(moment.getLikes());
    response.setComments(moment.getComments());
    response.setDate(moment.getDate());
    return response;
  }
}
