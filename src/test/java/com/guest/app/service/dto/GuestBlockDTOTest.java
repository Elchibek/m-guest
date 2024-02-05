package com.guest.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GuestBlockDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GuestBlockDTO.class);
        GuestBlockDTO guestBlockDTO1 = new GuestBlockDTO();
        guestBlockDTO1.setId("id1");
        GuestBlockDTO guestBlockDTO2 = new GuestBlockDTO();
        assertThat(guestBlockDTO1).isNotEqualTo(guestBlockDTO2);
        guestBlockDTO2.setId(guestBlockDTO1.getId());
        assertThat(guestBlockDTO1).isEqualTo(guestBlockDTO2);
        guestBlockDTO2.setId("id2");
        assertThat(guestBlockDTO1).isNotEqualTo(guestBlockDTO2);
        guestBlockDTO1.setId(null);
        assertThat(guestBlockDTO1).isNotEqualTo(guestBlockDTO2);
    }
}
