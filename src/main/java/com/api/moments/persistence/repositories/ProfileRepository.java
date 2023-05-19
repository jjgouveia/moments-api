package com.api.moments.persistence.repositories;

import com.api.moments.persistence.entities.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, UUID> {
  Profile findByUsername(String username);

  Profile findByUserId(UUID userId);
}
