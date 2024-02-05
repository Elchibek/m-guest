package com.guest.app.repository;

import com.guest.app.domain.Entrance;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Entrance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntranceRepository extends MongoRepository<Entrance, String> {
    Page<Entrance> findAllByGuestBlockId(String guestBlockId, Pageable pageable);
    Optional<Entrance> findFirstByGuestBlockId(String guestBlockId);
}
