package com.guest.app.repository;

import com.guest.app.domain.Guest;
import com.guest.app.domain.GuestContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the GuestContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuestContactRepository extends MongoRepository<GuestContact, String> {
    Page<GuestContact> findAllByGuestId(String guestId, Pageable page);

    long deleteByGuestId(String guestId);
}
