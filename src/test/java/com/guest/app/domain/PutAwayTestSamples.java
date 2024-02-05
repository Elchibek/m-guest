package com.guest.app.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class PutAwayTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PutAway getPutAwaySample1() {
        return new PutAway().id("id1").countPerson(1).explanation("explanation1");
    }

    public static PutAway getPutAwaySample2() {
        return new PutAway().id("id2").countPerson(2).explanation("explanation2");
    }

    public static PutAway getPutAwayRandomSampleGenerator() {
        return new PutAway()
            .id(UUID.randomUUID().toString())
            .countPerson(intCount.incrementAndGet())
            .explanation(UUID.randomUUID().toString());
    }
}
