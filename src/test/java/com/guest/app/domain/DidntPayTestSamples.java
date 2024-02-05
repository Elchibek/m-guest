package com.guest.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class DidntPayTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static DidntPay getDidntPaySample1() {
        return new DidntPay().id("id1").countPerson(1).paid(1).explanation("explanation1");
    }

    public static DidntPay getDidntPaySample2() {
        return new DidntPay().id("id2").countPerson(2).paid(2).explanation("explanation2");
    }

    public static DidntPay getDidntPayRandomSampleGenerator() {
        return new DidntPay()
            .id(UUID.randomUUID().toString())
            .countPerson(intCount.incrementAndGet())
            .paid(intCount.incrementAndGet())
            .explanation(UUID.randomUUID().toString());
    }
}
