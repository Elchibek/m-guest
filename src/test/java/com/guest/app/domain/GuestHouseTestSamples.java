package com.guest.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class GuestHouseTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static GuestHouse getGuestHouseSample1() {
        return new GuestHouse().id("id1").name("name1").countPerson(1).backgroundColor("backgroundColor1");
    }

    public static GuestHouse getGuestHouseSample2() {
        return new GuestHouse().id("id2").name("name2").countPerson(2).backgroundColor("backgroundColor2");
    }

    public static GuestHouse getGuestHouseRandomSampleGenerator() {
        return new GuestHouse()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .countPerson(intCount.incrementAndGet())
            .backgroundColor(UUID.randomUUID().toString());
    }
}
