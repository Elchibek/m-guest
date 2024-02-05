package com.guest.app.domain;

import static com.guest.app.domain.EntranceTestSamples.*;
import static com.guest.app.domain.GuestBlockTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntranceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entrance.class);
        Entrance entrance1 = getEntranceSample1();
        Entrance entrance2 = new Entrance();
        assertThat(entrance1).isNotEqualTo(entrance2);

        entrance2.setId(entrance1.getId());
        assertThat(entrance1).isEqualTo(entrance2);

        entrance2 = getEntranceSample2();
        assertThat(entrance1).isNotEqualTo(entrance2);
    }

    @Test
    void guestBlockTest() throws Exception {
        Entrance entrance = getEntranceRandomSampleGenerator();
        GuestBlock guestBlockBack = getGuestBlockRandomSampleGenerator();

        entrance.setGuestBlock(guestBlockBack);
        assertThat(entrance.getGuestBlock()).isEqualTo(guestBlockBack);

        entrance.guestBlock(null);
        assertThat(entrance.getGuestBlock()).isNull();
    }
}
