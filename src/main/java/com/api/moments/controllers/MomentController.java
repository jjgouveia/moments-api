package com.api.moments.controllers;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.services.moment.CreateMomentRequest;
import com.api.moments.services.moment.MomentService;
import com.api.moments.services.security.IJwtService;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
  public Moment getMomentById(@PathVariable UUID momentId) {
    System.out.println("momentId: " + momentId);
    return this.momentService.getById(momentId);
  }

  @GetMapping("/image/{imageId}")
  public ResponseEntity<Resource> downloadImage(@PathVariable String imageId) throws IOException {
    GridFSFile file =
        gridFsTemplate.findOne(new Query(Criteria.where("_id").is(new ObjectId(imageId))));
    if (file == null) {
      return ResponseEntity.notFound().build();
    }

    GridFsResource resource = gridFsTemplate.getResource(file);
    return ResponseEntity.ok().contentType(MediaType.parseMediaType(resource.getContentType()))
        .body(new InputStreamResource(resource.getInputStream()));
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

    ObjectId objectId = gridFsTemplate.store(image.getInputStream(), image.getOriginalFilename(),
        image.getContentType());

    moment.setUserId(jwtService.getUserId(authorizationHeader));
    moment.setImage(objectId.toHexString());

    var response = this.momentService.create(moment);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
