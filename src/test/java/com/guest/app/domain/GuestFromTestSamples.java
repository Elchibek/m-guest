package com.guest.app.domain;

import java.util.UUID;

public class GuestFromTestSamples {

    public static GuestFrom getGuestFromSample1() {
        return new GuestFrom().id("id1").name("name1");
    }

    public static GuestFrom getGuestFromSample2() {
        return new GuestFrom().id("id2").name("name2");
    }

    public static GuestFrom getGuestFromRandomSampleGenerator() {
        return new GuestFrom().id(UUID.randomUUID().toString()).name(UUID.randomUUID().toString());
    }
}
