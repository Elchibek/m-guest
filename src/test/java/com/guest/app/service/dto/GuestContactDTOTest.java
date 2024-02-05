package com.guest.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuestContactDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuestContactDTO.class);
        GuestContactDTO guestContactDTO1 = new GuestContactDTO();
        guestContactDTO1.setId("id1");
        GuestContactDTO guestContactDTO2 = new GuestContactDTO();
        assertThat(guestContactDTO1).isNotEqualTo(guestContactDTO2);
        guestContactDTO2.setId(guestContactDTO1.getId());
        assertThat(guestContactDTO1).isEqualTo(guestContactDTO2);
        guestContactDTO2.setId("id2");
        assertThat(guestContactDTO1).isNotEqualTo(guestContactDTO2);
        guestContactDTO1.setId(null);
        assertThat(guestContactDTO1).isNotEqualTo(guestContactDTO2);
    }
}
