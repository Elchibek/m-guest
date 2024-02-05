package com.guest.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PutAwayDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PutAwayDTO.class);
        PutAwayDTO putAwayDTO1 = new PutAwayDTO();
        putAwayDTO1.setId("id1");
        PutAwayDTO putAwayDTO2 = new PutAwayDTO();
        assertThat(putAwayDTO1).isNotEqualTo(putAwayDTO2);
        putAwayDTO2.setId(putAwayDTO1.getId());
        assertThat(putAwayDTO1).isEqualTo(putAwayDTO2);
        putAwayDTO2.setId("id2");
        assertThat(putAwayDTO1).isNotEqualTo(putAwayDTO2);
        putAwayDTO1.setId(null);
        assertThat(putAwayDTO1).isNotEqualTo(putAwayDTO2);
    }
}
