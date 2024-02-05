package com.guest.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class GuestMapperTest {

    private GuestMapper guestMapper;

    @BeforeEach
    public void setUp() {
        guestMapper = new GuestMapperImpl();
    }
}
