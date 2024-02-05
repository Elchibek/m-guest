package com.guest.app.domain;

import static com.guest.app.domain.EntranceTestSamples.*;
import static com.guest.app.domain.FloorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.guest.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FloorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Floor.class);
        Floor floor1 = getFloorSample1();
        Floor floor2 = new Floor();
        assertThat(floor1).isNotEqualTo(floor2);

        floor2.setId(floor1.getId());
        assertThat(floor1).isEqualTo(floor2);

        floor2 = getFloorSample2();
        assertThat(floor1).isNotEqualTo(floor2);
    }

    @Test
    void entranceTest() throws Exception {
        Floor floor = getFloorRandomSampleGenerator();
        Entrance entranceBack = getEntranceRandomSampleGenerator();

        floor.setEntrance(entranceBack);
        assertThat(floor.getEntrance()).isEqualTo(entranceBack);

        floor.entrance(null);
        assertThat(floor.getEntrance()).isNull();
    }
}
