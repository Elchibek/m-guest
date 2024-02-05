package com.guest.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntranceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntranceDTO.class);
        EntranceDTO entranceDTO1 = new EntranceDTO();
        entranceDTO1.setId("id1");
        EntranceDTO entranceDTO2 = new EntranceDTO();
        assertThat(entranceDTO1).isNotEqualTo(entranceDTO2);
        entranceDTO2.setId(entranceDTO1.getId());
        assertThat(entranceDTO1).isEqualTo(entranceDTO2);
        entranceDTO2.setId("id2");
        assertThat(entranceDTO1).isNotEqualTo(entranceDTO2);
        entranceDTO1.setId(null);
        assertThat(entranceDTO1).isNotEqualTo(entranceDTO2);
    }
}
