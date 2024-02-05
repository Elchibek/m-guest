package com.guest.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DidntPayDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DidntPayDTO.class);
        DidntPayDTO didntPayDTO1 = new DidntPayDTO();
        didntPayDTO1.setId("id1");
        DidntPayDTO didntPayDTO2 = new DidntPayDTO();
        assertThat(didntPayDTO1).isNotEqualTo(didntPayDTO2);
        didntPayDTO2.setId(didntPayDTO1.getId());
        assertThat(didntPayDTO1).isEqualTo(didntPayDTO2);
        didntPayDTO2.setId("id2");
        assertThat(didntPayDTO1).isNotEqualTo(didntPayDTO2);
        didntPayDTO1.setId(null);
        assertThat(didntPayDTO1).isNotEqualTo(didntPayDTO2);
    }
}
