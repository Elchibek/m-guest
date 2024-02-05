package com.guest.app.domain;

import static com.guest.app.domain.EntranceTestSamples.*;
import static com.guest.app.domain.FloorTestSamples.*;
import static com.guest.app.domain.GuestBlockTestSamples.*;
import static com.guest.app.domain.GuestFromTestSamples.*;
import static com.guest.app.domain.GuestHouseTestSamples.*;
import static com.guest.app.domain.GuestTestSamples.*;
import static com.guest.app.domain.PutAwayTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GuestTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Guest.class);
        Guest guest1 = getGuestSample1();
        Guest guest2 = new Guest();
        assertThat(guest1).isNotEqualTo(guest2);

        guest2.setId(guest1.getId());
        assertThat(guest1).isEqualTo(guest2);

        guest2 = getGuestSample2();
        assertThat(guest1).isNotEqualTo(guest2);
    }

    @Test
    void putAwayTest() throws Exception {
        Guest guest = getGuestRandomSampleGenerator();
        PutAway putAwayBack = getPutAwayRandomSampleGenerator();

        guest.addPutAway(putAwayBack);
        assertThat(guest.getPutAways()).containsOnly(putAwayBack);
        assertThat(putAwayBack.getGuest()).isEqualTo(guest);

        guest.removePutAway(putAwayBack);
        assertThat(guest.getPutAways()).doesNotContain(putAwayBack);
        assertThat(putAwayBack.getGuest()).isNull();

        guest.putAways(new HashSet<>(Set.of(putAwayBack)));
        assertThat(guest.getPutAways()).containsOnly(putAwayBack);
        assertThat(putAwayBack.getGuest()).isEqualTo(guest);

        guest.setPutAways(new HashSet<>());
        assertThat(guest.getPutAways()).doesNotContain(putAwayBack);
        assertThat(putAwayBack.getGuest()).isNull();
    }

    @Test
    void guestBlockTest() throws Exception {
        Guest guest = getGuestRandomSampleGenerator();
        GuestBlock guestBlockBack = getGuestBlockRandomSampleGenerator();

        guest.setGuestBlock(guestBlockBack);
        assertThat(guest.getGuestBlock()).isEqualTo(guestBlockBack);

        guest.guestBlock(null);
        assertThat(guest.getGuestBlock()).isNull();
    }

    @Test
    void entranceTest() throws Exception {
        Guest guest = getGuestRandomSampleGenerator();
        Entrance entranceBack = getEntranceRandomSampleGenerator();

        guest.setEntrance(entranceBack);
        assertThat(guest.getEntrance()).isEqualTo(entranceBack);

        guest.entrance(null);
        assertThat(guest.getEntrance()).isNull();
    }

    @Test
    void floorTest() throws Exception {
        Guest guest = getGuestRandomSampleGenerator();
        Floor floorBack = getFloorRandomSampleGenerator();

        guest.setFloor(floorBack);
        assertThat(guest.getFloor()).isEqualTo(floorBack);

        guest.floor(null);
        assertThat(guest.getFloor()).isNull();
    }

    @Test
    void guestHouseTest() throws Exception {
        Guest guest = getGuestRandomSampleGenerator();
        GuestHouse guestHouseBack = getGuestHouseRandomSampleGenerator();

        guest.setGuestHouse(guestHouseBack);
        assertThat(guest.getGuestHouse()).isEqualTo(guestHouseBack);

        guest.guestHouse(null);
        assertThat(guest.getGuestHouse()).isNull();
    }

    @Test
    void guestFromTest() throws Exception {
        Guest guest = getGuestRandomSampleGenerator();
        GuestFrom guestFromBack = getGuestFromRandomSampleGenerator();

        guest.setGuestFrom(guestFromBack);
        assertThat(guest.getGuestFrom()).isEqualTo(guestFromBack);

        guest.guestFrom(null);
        assertThat(guest.getGuestFrom()).isNull();
    }
}
