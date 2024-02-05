package com.guest.app.domain;

import static com.guest.app.domain.GuestStaticTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuestStaticTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuestStatic.class);
        GuestStatic guestStatic1 = getGuestStaticSample1();
        GuestStatic guestStatic2 = new GuestStatic();
        assertThat(guestStatic1).isNotEqualTo(guestStatic2);

        guestStatic2.setId(guestStatic1.getId());
        assertThat(guestStatic1).isEqualTo(guestStatic2);

        guestStatic2 = getGuestStaticSample2();
        assertThat(guestStatic1).isNotEqualTo(guestStatic2);
    }
}
