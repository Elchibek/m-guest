package com.guest.app.repository;

import com.guest.app.domain.Guest;
import com.guest.app.domain.GuestHouse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Guest entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuestRepository extends MongoRepository<Guest, String> {
    // https://www.mongodb.com/docs/manual/reference/operator/query/text/
    // https://www.baeldung.com/java-faker
    // https://www.baeldung.com/mongodb-return-specific-fields
    @Query("{guestFromId: ?0, guestBlockId: ?1}")
    Page<Guest> findAll(@Nullable String guestFromId, @Nullable String guestBlockId, Pageable pageable);

    @Query(value = "{ }", fields = "{ 'countDidntPay' : 1, 'countPerson' : 1, 'totalPrice' : 1, 'restOfTheDay' : 1 }")
    List<Guest> getAllInclude();

    // @Aggregation(pipeline = {
    //         "{ '$match': { 'startDate': { $gt : ?0, $lt: ?1 } }",
    // })
    // // "{'$group':{'_id':{'startDate': }}, 'countPerson':{ $sum: 1}}}",
    // // "{'$sort':{'startDate':-1}}"
    // Page<Guest> getStatic(String startDate, String endDate);

    @Query(value = "{ 'guestHouse.id': ?0}", fields = "{ 'countPerson' : 1 }")
    List<Guest> getAllCountHousePerson(String guestHouseId);

    @Query(value = "{guestBlockId: ?0}", count = true)
    long countGuestBlock(String guestBlockId);

    Optional<Guest> findFirstByParentId(String parentId);

    long deleteByParentId(String parentId);
}
