package com.api.moments.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UncontrolledController extends BaseController {
  @GetMapping("/doodly")
  public ResponseEntity<Object> getData() {
    Object data = new Object() {
      public final String name = "John Doe";
      public final int age = 30;
    };
    return ResponseEntity.ok().body(data);
  }

}
