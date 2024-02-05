package com.guest.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class EntranceTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Entrance getEntranceSample1() {
        return new Entrance().id("id1").name("name1").numEntrance(1);
    }

    public static Entrance getEntranceSample2() {
        return new Entrance().id("id2").name("name2").numEntrance(2);
    }

    public static Entrance getEntranceRandomSampleGenerator() {
        return new Entrance().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString()).numEntrance(intCount.incrementAndGet());
    }
}
