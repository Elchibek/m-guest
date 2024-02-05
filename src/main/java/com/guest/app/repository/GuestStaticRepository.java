package com.guest.app.repository;

import com.guest.app.domain.GuestStatic;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the GuestStatic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuestStaticRepository extends MongoRepository<GuestStatic, String> {
    @Query("{'isArchive': ?0}")
    Optional<GuestStatic> getOneIsArchive(boolean isArchive);
}
