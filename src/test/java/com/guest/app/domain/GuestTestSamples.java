package com.guest.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class GuestTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Guest getGuestSample1() {
        return new Guest()
            .id("id1")
            .name("name1")
            .guestInstitution("guestInstitution1")
            .responsible("responsible1")
            .countDidntPay(1)
            .countPerson(1)
            .explanation("explanation1")
            .price(1)
            .totalPrice(1);
    }

    public static Guest getGuestSample2() {
        return new Guest()
            .id("id2")
            .name("name2")
            .guestInstitution("guestInstitution2")
            .responsible("responsible2")
            .countDidntPay(2)
            .countPerson(2)
            .explanation("explanation2")
            .price(2)
            .totalPrice(2);
    }

    public static Guest getGuestRandomSampleGenerator() {
        return new Guest()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .guestInstitution(UUID.randomUUID().toString())
            .responsible(UUID.randomUUID().toString())
            .countDidntPay(intCount.incrementAndGet())
            .countPerson(intCount.incrementAndGet())
            .explanation(UUID.randomUUID().toString())
            .price(intCount.incrementAndGet())
            .totalPrice(intCount.incrementAndGet());
    }
}
