package com.guest.app.repository;

import com.guest.app.domain.Entrance;
import com.guest.app.domain.GuestHouse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the GuestHouse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuestHouseRepository extends MongoRepository<GuestHouse, String> {
    Page<GuestHouse> findAllByFloorId(String floorId, Pageable pageable);

    Optional<GuestHouse> findFirstByGuestBlockId(String guestBlockId);
    // Long countByIsEmpty(Boolean isEmpty);

    // @Query(value = "{'productDetails.productType': {$regex: ?0, $options: 'i'},
    // 'sourceDescriptor': ?1}", count = true)
    // public Long countFetchedDocumentsForCategory(String cat, String
    // sourceDescriptor);
}
