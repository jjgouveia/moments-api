package com.api.moments.persistence.repositories;

import com.api.moments.persistence.entities.Moment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MomentRepository extends MongoRepository<Moment, UUID> {
    List<Moment> findByUserIdOrderByDateDesc(UUID userId);
}
