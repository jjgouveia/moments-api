package com.api.moments.persistence.repositories;

import com.api.moments.persistence.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<User, UUID> {
  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
