package com.guest.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class GuestBlockTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static GuestBlock getGuestBlockSample1() {
        return new GuestBlock().id("id1").name("name1").numEntrance(1).numFloor(1).numHouse(1);
    }

    public static GuestBlock getGuestBlockSample2() {
        return new GuestBlock().id("id2").name("name2").numEntrance(2).numFloor(2).numHouse(2);
    }

    public static GuestBlock getGuestBlockRandomSampleGenerator() {
        return new GuestBlock()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .numEntrance(intCount.incrementAndGet())
            .numFloor(intCount.incrementAndGet())
            .numHouse(intCount.incrementAndGet());
    }
}
