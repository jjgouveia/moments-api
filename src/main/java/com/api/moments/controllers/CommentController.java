package com.api.moments.controllers;

import com.api.moments.services.comment.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class CommentController extends BaseController {
    @Autowired
    private ICommentService commentService;

    @PostMapping("/{momentId}/comment")
    public ResponseEntity<String> addComment(@RequestParam String content,
                                             @RequestHeader("Authorization") String token, @PathVariable UUID momentId) {
        try {
            commentService.createComment(content, token, momentId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("{momentId}/comment/{commentId}/remove")
    public ResponseEntity<String> removeComment(@RequestHeader("Authorization") String token,
                                                @PathVariable UUID momentId, @PathVariable UUID commentId) {
        try {
            commentService.deleteComment(token, commentId, momentId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("{momentId}/comment/{commentId}/update")
    public ResponseEntity<String> updateComment(@RequestHeader("Authorization") String token,
                                                @PathVariable UUID momentId, @PathVariable UUID commentId, @RequestParam String content) {
        try {
            commentService.updateComment(token, commentId, momentId, content);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}
