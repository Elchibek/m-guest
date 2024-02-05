package com.guest.app.repository;

import com.guest.app.domain.GuestFrom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the GuestFrom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuestFromRepository extends MongoRepository<GuestFrom, String> {}
