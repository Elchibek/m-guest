package com.guest.app.domain;

import static com.guest.app.domain.GuestTestSamples.*;
import static com.guest.app.domain.PutAwayTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PutAwayTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PutAway.class);
        PutAway putAway1 = getPutAwaySample1();
        PutAway putAway2 = new PutAway();
        assertThat(putAway1).isNotEqualTo(putAway2);

        putAway2.setId(putAway1.getId());
        assertThat(putAway1).isEqualTo(putAway2);

        putAway2 = getPutAwaySample2();
        assertThat(putAway1).isNotEqualTo(putAway2);
    }

    @Test
    void guestTest() throws Exception {
        PutAway putAway = getPutAwayRandomSampleGenerator();
        Guest guestBack = getGuestRandomSampleGenerator();

        putAway.setGuest(guestBack);
        assertThat(putAway.getGuest()).isEqualTo(guestBack);

        putAway.guest(null);
        assertThat(putAway.getGuest()).isNull();
    }
}
