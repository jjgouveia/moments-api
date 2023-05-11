package com.api.moments.persistence.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document
public class Profile {
    @Id
    private UUID id;
    private String name;
    private String profilePicture;
    private String bio;
    private String location;
    private String website;
    private String birthday;
}
