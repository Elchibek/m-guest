package com.guest.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class GuestStaticTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static GuestStatic getGuestStaticSample1() {
        return new GuestStatic()
            .id("id1")
            .countPerson(1)
            .toDayDeparture(1)
            .toMorrowDeparture(1)
            .totalPrice(1)
            .totalDidntPay(1)
            .numOfApartments(1)
            .affordableApartments(1);
    }

    public static GuestStatic getGuestStaticSample2() {
        return new GuestStatic()
            .id("id2")
            .countPerson(2)
            .toDayDeparture(2)
            .toMorrowDeparture(2)
            .totalPrice(2)
            .totalDidntPay(2)
            .numOfApartments(2)
            .affordableApartments(2);
    }

    public static GuestStatic getGuestStaticRandomSampleGenerator() {
        return new GuestStatic()
            .id(UUID.randomUUID().toString())
            .countPerson(intCount.incrementAndGet())
            .toDayDeparture(intCount.incrementAndGet())
            .toMorrowDeparture(intCount.incrementAndGet())
            .totalPrice(intCount.incrementAndGet())
            .totalDidntPay(intCount.incrementAndGet())
            .numOfApartments(intCount.incrementAndGet())
            .affordableApartments(intCount.incrementAndGet());
    }
}
