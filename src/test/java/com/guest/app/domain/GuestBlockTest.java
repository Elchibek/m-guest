package com.guest.app.domain;

import static com.guest.app.domain.GuestBlockTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuestBlockTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuestBlock.class);
        GuestBlock guestBlock1 = getGuestBlockSample1();
        GuestBlock guestBlock2 = new GuestBlock();
        assertThat(guestBlock1).isNotEqualTo(guestBlock2);

        guestBlock2.setId(guestBlock1.getId());
        assertThat(guestBlock1).isEqualTo(guestBlock2);

        guestBlock2 = getGuestBlockSample2();
        assertThat(guestBlock1).isNotEqualTo(guestBlock2);
    }
}
