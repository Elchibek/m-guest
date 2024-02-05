package com.guest.app.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class GuestStaticMapperTest {

    private GuestStaticMapper guestStaticMapper;

    @BeforeEach
    public void setUp() {
        guestStaticMapper = new GuestStaticMapperImpl();
    }
}
