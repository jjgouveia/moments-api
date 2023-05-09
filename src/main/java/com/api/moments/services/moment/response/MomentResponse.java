package com.api.moments.services.moment.response;

import com.api.moments.persistence.entities.Comment;
import com.api.moments.persistence.entities.Moment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class MomentResponse {
    private UUID id;
    private UUID userId;
    private String title;
    private String description;
    private String imageUrl;
    private List<UUID> likes;
    private List<Comment> comments;
    private LocalDateTime date;


    public MomentResponse() {
    }

    public MomentResponse(Moment moment) {
        this.id = moment.getId();
        this.title = moment.getTitle();
        this.description = moment.getDescription();
        this.imageUrl = moment.getImageUrl();
        this.userId = moment.getUserId();
        this.likes = moment.getLikes();
        this.comments = moment.getComments();
        this.date = moment.getDate();
    }
}
