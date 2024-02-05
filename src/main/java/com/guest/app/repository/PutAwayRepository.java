package com.guest.app.repository;

import com.guest.app.domain.PutAway;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the PutAway entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PutAwayRepository extends MongoRepository<PutAway, String> {
    Optional<PutAway> deleteByGuestId(String guestId);
}
