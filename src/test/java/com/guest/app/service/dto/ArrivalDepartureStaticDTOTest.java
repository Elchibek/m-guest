package com.guest.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArrivalDepartureStaticDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArrivalDepartureStaticDTO.class);
        ArrivalDepartureStaticDTO arrivalDepartureStaticDTO1 = new ArrivalDepartureStaticDTO();
        arrivalDepartureStaticDTO1.setId("id1");
        ArrivalDepartureStaticDTO arrivalDepartureStaticDTO2 = new ArrivalDepartureStaticDTO();
        assertThat(arrivalDepartureStaticDTO1).isNotEqualTo(arrivalDepartureStaticDTO2);
        arrivalDepartureStaticDTO2.setId(arrivalDepartureStaticDTO1.getId());
        assertThat(arrivalDepartureStaticDTO1).isEqualTo(arrivalDepartureStaticDTO2);
        arrivalDepartureStaticDTO2.setId("id2");
        assertThat(arrivalDepartureStaticDTO1).isNotEqualTo(arrivalDepartureStaticDTO2);
        arrivalDepartureStaticDTO1.setId(null);
        assertThat(arrivalDepartureStaticDTO1).isNotEqualTo(arrivalDepartureStaticDTO2);
    }
}
