package com.guest.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class FloorTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Floor getFloorSample1() {
        return new Floor().id("id1").name("name1").numFloor(1).backgroundColor("backgroundColor1");
    }

    public static Floor getFloorSample2() {
        return new Floor().id("id2").name("name2").numFloor(2).backgroundColor("backgroundColor2");
    }

    public static Floor getFloorRandomSampleGenerator() {
        return new Floor()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .numFloor(intCount.incrementAndGet())
            .backgroundColor(UUID.randomUUID().toString());
    }
}
