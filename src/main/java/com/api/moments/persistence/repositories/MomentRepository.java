package com.api.moments.persistence.repositories;

import com.api.moments.persistence.entities.Moment;
import com.api.moments.services.moment.response.MomentResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MomentRepository extends MongoRepository<Moment, UUID> {
    List<MomentResponse> findByUserIdOrderByDateDesc(UUID userId);
}
