package com.guest.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuestStaticDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuestStaticDTO.class);
        GuestStaticDTO guestStaticDTO1 = new GuestStaticDTO();
        guestStaticDTO1.setId("id1");
        GuestStaticDTO guestStaticDTO2 = new GuestStaticDTO();
        assertThat(guestStaticDTO1).isNotEqualTo(guestStaticDTO2);
        guestStaticDTO2.setId(guestStaticDTO1.getId());
        assertThat(guestStaticDTO1).isEqualTo(guestStaticDTO2);
        guestStaticDTO2.setId("id2");
        assertThat(guestStaticDTO1).isNotEqualTo(guestStaticDTO2);
        guestStaticDTO1.setId(null);
        assertThat(guestStaticDTO1).isNotEqualTo(guestStaticDTO2);
    }
}
