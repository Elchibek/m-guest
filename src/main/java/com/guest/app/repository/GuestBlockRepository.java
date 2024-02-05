package com.guest.app.repository;

import com.guest.app.domain.GuestBlock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the GuestBlock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuestBlockRepository extends MongoRepository<GuestBlock, String> {}
