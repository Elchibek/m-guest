package com.guest.app.domain;

import static com.guest.app.domain.ArrivalDepartureStaticTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArrivalDepartureStaticTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArrivalDepartureStatic.class);
        ArrivalDepartureStatic arrivalDepartureStatic1 = getArrivalDepartureStaticSample1();
        ArrivalDepartureStatic arrivalDepartureStatic2 = new ArrivalDepartureStatic();
        assertThat(arrivalDepartureStatic1).isNotEqualTo(arrivalDepartureStatic2);

        arrivalDepartureStatic2.setId(arrivalDepartureStatic1.getId());
        assertThat(arrivalDepartureStatic1).isEqualTo(arrivalDepartureStatic2);

        arrivalDepartureStatic2 = getArrivalDepartureStaticSample2();
        assertThat(arrivalDepartureStatic1).isNotEqualTo(arrivalDepartureStatic2);
    }
}
