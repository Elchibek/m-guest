package com.guest.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuestFromDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuestFromDTO.class);
        GuestFromDTO guestFromDTO1 = new GuestFromDTO();
        guestFromDTO1.setId("id1");
        GuestFromDTO guestFromDTO2 = new GuestFromDTO();
        assertThat(guestFromDTO1).isNotEqualTo(guestFromDTO2);
        guestFromDTO2.setId(guestFromDTO1.getId());
        assertThat(guestFromDTO1).isEqualTo(guestFromDTO2);
        guestFromDTO2.setId("id2");
        assertThat(guestFromDTO1).isNotEqualTo(guestFromDTO2);
        guestFromDTO1.setId(null);
        assertThat(guestFromDTO1).isNotEqualTo(guestFromDTO2);
    }
}
