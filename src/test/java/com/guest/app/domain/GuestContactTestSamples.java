package com.guest.app.domain;

import java.util.UUID;

public class GuestContactTestSamples {

    public static GuestContact getGuestContactSample1() {
        return new GuestContact().id("id1").name("name1").phone("phone1").explanation("explanation1");
    }

    public static GuestContact getGuestContactSample2() {
        return new GuestContact().id("id2").name("name2").phone("phone2").explanation("explanation2");
    }

    public static GuestContact getGuestContactRandomSampleGenerator() {
        return new GuestContact()
            .id(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString())
            .explanation(UUID.randomUUID().toString());
    }
}
