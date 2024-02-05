package com.guest.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ArrivalDepartureStaticTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ArrivalDepartureStatic getArrivalDepartureStaticSample1() {
        return new ArrivalDepartureStatic().id("id1").countPerson(1);
    }

    public static ArrivalDepartureStatic getArrivalDepartureStaticSample2() {
        return new ArrivalDepartureStatic().id("id2").countPerson(2);
    }

    public static ArrivalDepartureStatic getArrivalDepartureStaticRandomSampleGenerator() {
        return new ArrivalDepartureStatic().id(UUID.randomUUID().toString()).countPerson(intCount.incrementAndGet());
    }
}
