package com.guest.app.domain;

import static com.guest.app.domain.GuestFromTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuestFromTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuestFrom.class);
        GuestFrom guestFrom1 = getGuestFromSample1();
        GuestFrom guestFrom2 = new GuestFrom();
        assertThat(guestFrom1).isNotEqualTo(guestFrom2);

        guestFrom2.setId(guestFrom1.getId());
        assertThat(guestFrom1).isEqualTo(guestFrom2);

        guestFrom2 = getGuestFromSample2();
        assertThat(guestFrom1).isNotEqualTo(guestFrom2);
    }
}
