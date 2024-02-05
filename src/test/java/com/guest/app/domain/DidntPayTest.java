package com.guest.app.domain;

import static com.guest.app.domain.DidntPayTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DidntPayTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DidntPay.class);
        DidntPay didntPay1 = getDidntPaySample1();
        DidntPay didntPay2 = new DidntPay();
        assertThat(didntPay1).isNotEqualTo(didntPay2);

        didntPay2.setId(didntPay1.getId());
        assertThat(didntPay1).isEqualTo(didntPay2);

        didntPay2 = getDidntPaySample2();
        assertThat(didntPay1).isNotEqualTo(didntPay2);
    }
}
