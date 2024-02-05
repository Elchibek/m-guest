package com.guest.app.repository;

import com.guest.app.domain.ArrivalDepartureStatic;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ArrivalDepartureStatic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArrivalDepartureStaticRepository extends MongoRepository<ArrivalDepartureStatic, String> {
    @Query("{'startDate': ?0}")
    Optional<ArrivalDepartureStatic> getOneStartDate(String startDate);

    @Query("{'endDate': ?0}")
    Optional<ArrivalDepartureStatic> getOneEndDateDate(String endDate);
}
