package com.api.moments.controllers;

import com.api.moments.services.moment.MomentService;
import com.api.moments.services.moment.request.CreateMomentRequest;
import com.api.moments.services.moment.response.MomentResponse;
import com.api.moments.services.security.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
public class MomentController extends BaseController {

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private MomentService momentService;

    @GetMapping("/all")
    public List<MomentResponse> getAllMoments() {
        return this.momentService.getAll();
    }

    @GetMapping("/{momentId}")
    public ResponseEntity<MomentResponse> getMomentById(@PathVariable UUID momentId) {
        var moment = this.momentService.getById(momentId);
        if (Objects.isNull(moment)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moment);
    }


    @PostMapping("/new-moment")
    public ResponseEntity<MomentResponse> newMoment(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestParam("image") MultipartFile image, @RequestParam("title") String title,
            @RequestParam("description") String description) throws IOException {

        try {
            var userId = jwtService.getUserId(authorizationHeader);
            var createMomentRequest = new CreateMomentRequest(title, description, userId);
            var moment = this.momentService.create(createMomentRequest, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(moment);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{momentId}")
    public ResponseEntity<String> deleteMoment(@PathVariable UUID momentId) {
        try {
            this.momentService.delete(momentId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
