package com.guest.app.repository;

import com.guest.app.domain.Floor;
import java.util.List;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Floor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FloorRepository extends MongoRepository<Floor, String> {
    Page<Floor> findAllByEntranceId(String entranceId, Pageable pageabl);
}
