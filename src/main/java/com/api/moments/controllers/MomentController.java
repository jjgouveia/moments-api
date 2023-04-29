package com.api.moments.controllers;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.services.MomentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/moments")
public class MomentController {

  @Autowired
  private MomentService momentService;

  @GetMapping
  public List<Moment> getAllMoments() {
    return this.momentService.getAll();
  }

  @PostMapping
  public Moment newMoment(@RequestBody Moment moment) {
    return this.momentService.create(moment);
  }
}
