package com.guest.app.domain;

import static com.guest.app.domain.FloorTestSamples.*;
import static com.guest.app.domain.GuestHouseTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuestHouseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuestHouse.class);
        GuestHouse guestHouse1 = getGuestHouseSample1();
        GuestHouse guestHouse2 = new GuestHouse();
        assertThat(guestHouse1).isNotEqualTo(guestHouse2);

        guestHouse2.setId(guestHouse1.getId());
        assertThat(guestHouse1).isEqualTo(guestHouse2);

        guestHouse2 = getGuestHouseSample2();
        assertThat(guestHouse1).isNotEqualTo(guestHouse2);
    }

    @Test
    void floorTest() throws Exception {
        GuestHouse guestHouse = getGuestHouseRandomSampleGenerator();
        Floor floorBack = getFloorRandomSampleGenerator();

        guestHouse.setFloor(floorBack);
        assertThat(guestHouse.getFloor()).isEqualTo(floorBack);

        guestHouse.floor(null);
        assertThat(guestHouse.getFloor()).isNull();
    }
}
