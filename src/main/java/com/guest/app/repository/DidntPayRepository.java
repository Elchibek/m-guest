package com.guest.app.repository;

import com.guest.app.domain.DidntPay;
import com.guest.app.domain.GuestContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the DidntPay entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DidntPayRepository extends MongoRepository<DidntPay, String> {
    Page<DidntPay> findAllByGuestId(String guestId, Pageable page);

    long deleteByGuestId(String guestId);
}
