package com.api.moments.controllers;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.services.moment.MomentService;
import com.api.moments.services.moment.request.CreateMomentRequest;
import com.api.moments.services.security.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/moments")
public class MomentController {

  @Autowired
  private IJwtService jwtService;

  @Autowired
  private MomentService momentService;

  @Autowired
  private GridFsTemplate gridFsTemplate;

  @GetMapping
  public List<Moment> getAllMoments() {

    return this.momentService.getAll();
  }

  @GetMapping("/{momentId}")
  public ResponseEntity<Moment> getMomentById(@PathVariable UUID momentId) {
    Moment moment = this.momentService.getById(momentId);
    if (Objects.isNull(moment)) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(moment);
  }


  @PostMapping
  public ResponseEntity<Moment> newMoment(
      @RequestHeader("Authorization") String authorizationHeader,
      @RequestParam("image") MultipartFile image, @RequestParam("title") String title,
      @RequestParam("description") String description) throws IOException {


    try {
      jwtService.isValidToken(authorizationHeader);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    var moment = new CreateMomentRequest(title, description);

    if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
      return ResponseEntity.badRequest().build();
    }

    moment.setUserId(jwtService.getUserId(authorizationHeader));

    var response = this.momentService.create(moment, image);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
