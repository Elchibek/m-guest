package com.guest.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class GuestContactMapperTest {

    private GuestContactMapper guestContactMapper;

    @BeforeEach
    public void setUp() {
        guestContactMapper = new GuestContactMapperImpl();
    }
}
