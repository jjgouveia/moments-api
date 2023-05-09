package com.api.moments.services.moment.response;

import com.api.moments.persistence.entities.Comment;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class MomentResponse {
    private String id;
    private String userId;
    private String title;
    private String description;
    private String imageUrl;
    private List<UUID> likes;
    private List<Comment> comments;
    private String date;

}
