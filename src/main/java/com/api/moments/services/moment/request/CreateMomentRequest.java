package com.api.moments.services.moment.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
public class CreateMomentRequest {
    private String title;
    private String description;
    private MultipartFile image;
    private String imageUrl;
    private UUID userId;

    public CreateMomentRequest(String title, String description, MultipartFile image, UUID userId) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.setImage(image);
        this.setImageUrl(getImageUrl());
    }

    public void setUserId() {
        this.userId = UUID.randomUUID();
    }


}
