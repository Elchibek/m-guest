package com.guest.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuestHouseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuestHouseDTO.class);
        GuestHouseDTO guestHouseDTO1 = new GuestHouseDTO();
        guestHouseDTO1.setId("id1");
        GuestHouseDTO guestHouseDTO2 = new GuestHouseDTO();
        assertThat(guestHouseDTO1).isNotEqualTo(guestHouseDTO2);
        guestHouseDTO2.setId(guestHouseDTO1.getId());
        assertThat(guestHouseDTO1).isEqualTo(guestHouseDTO2);
        guestHouseDTO2.setId("id2");
        assertThat(guestHouseDTO1).isNotEqualTo(guestHouseDTO2);
        guestHouseDTO1.setId(null);
        assertThat(guestHouseDTO1).isNotEqualTo(guestHouseDTO2);
    }
}
