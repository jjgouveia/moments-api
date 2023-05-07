package com.api.moments.persistence.entities;

import com.api.moments.util.Timestamp;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Data
public class Follow {
    @Id
    private UUID id;

    private UUID followerId;

    private UUID followingId;
    
    private LocalDateTime followAt;

    public Follow(UUID followerId, UUID followingId) {
        this.setId();
        this.followerId = followerId;
        this.followingId = followingId;
        this.followAt = Timestamp.getTimestamp();
    }

    public void setId() {
        this.id = UUID.randomUUID();
    }
}
