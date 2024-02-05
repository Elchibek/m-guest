package com.guest.app.domain;

import static com.guest.app.domain.GuestContactTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuestContactTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuestContact.class);
        GuestContact guestContact1 = getGuestContactSample1();
        GuestContact guestContact2 = new GuestContact();
        assertThat(guestContact1).isNotEqualTo(guestContact2);

        guestContact2.setId(guestContact1.getId());
        assertThat(guestContact1).isEqualTo(guestContact2);

        guestContact2 = getGuestContactSample2();
        assertThat(guestContact1).isNotEqualTo(guestContact2);
    }
}
