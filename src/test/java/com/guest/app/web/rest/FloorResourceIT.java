package com.guest.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.guest.app.IntegrationTest;
import com.guest.app.domain.Entrance;
import com.guest.app.domain.Floor;
import com.guest.app.repository.FloorRepository;
import com.guest.app.service.dto.FloorDTO;
import com.guest.app.service.mapper.FloorMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link FloorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FloorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_FLOOR = 1;
    private static final Integer UPDATED_NUM_FLOOR = 2;

    private static final String DEFAULT_BACKGROUND_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_COLOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/floors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private FloorMapper floorMapper;

    @Autowired
    private MockMvc restFloorMockMvc;

    private Floor floor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Floor createEntity() {
        Floor floor = new Floor().name(DEFAULT_NAME).numFloor(DEFAULT_NUM_FLOOR).backgroundColor(DEFAULT_BACKGROUND_COLOR);
        // Add required entity
        Entrance entrance;
        entrance = EntranceResourceIT.createEntity();
        entrance.setId("fixed-id-for-tests");
        floor.setEntrance(entrance);
        return floor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Floor createUpdatedEntity() {
        Floor floor = new Floor().name(UPDATED_NAME).numFloor(UPDATED_NUM_FLOOR).backgroundColor(UPDATED_BACKGROUND_COLOR);
        // Add required entity
        Entrance entrance;
        entrance = EntranceResourceIT.createUpdatedEntity();
        entrance.setId("fixed-id-for-tests");
        floor.setEntrance(entrance);
        return floor;
    }

    @BeforeEach
    public void initTest() {
        floorRepository.deleteAll();
        floor = createEntity();
    }

    @Test
    void createFloor() throws Exception {
        int databaseSizeBeforeCreate = floorRepository.findAll().size();
        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);
        restFloorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isCreated());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeCreate + 1);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFloor.getNumFloor()).isEqualTo(DEFAULT_NUM_FLOOR);
        assertThat(testFloor.getBackgroundColor()).isEqualTo(DEFAULT_BACKGROUND_COLOR);
    }

    @Test
    void createFloorWithExistingId() throws Exception {
        // Create the Floor with an existing ID
        floor.setId("existing_id");
        FloorDTO floorDTO = floorMapper.toDto(floor);

        int databaseSizeBeforeCreate = floorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFloorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = floorRepository.findAll().size();
        // set the field null
        floor.setName(null);

        // Create the Floor, which fails.
        FloorDTO floorDTO = floorMapper.toDto(floor);

        restFloorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isBadRequest());

        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNumFloorIsRequired() throws Exception {
        int databaseSizeBeforeTest = floorRepository.findAll().size();
        // set the field null
        floor.setNumFloor(null);

        // Create the Floor, which fails.
        FloorDTO floorDTO = floorMapper.toDto(floor);

        restFloorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isBadRequest());

        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllFloors() throws Exception {
        // Initialize the database
        floorRepository.save(floor);

        // Get all the floorList
        restFloorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(floor.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].numFloor").value(hasItem(DEFAULT_NUM_FLOOR)))
            .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR)));
    }

    @Test
    void getFloor() throws Exception {
        // Initialize the database
        floorRepository.save(floor);

        // Get the floor
        restFloorMockMvc
            .perform(get(ENTITY_API_URL_ID, floor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(floor.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.numFloor").value(DEFAULT_NUM_FLOOR))
            .andExpect(jsonPath("$.backgroundColor").value(DEFAULT_BACKGROUND_COLOR));
    }

    @Test
    void getNonExistingFloor() throws Exception {
        // Get the floor
        restFloorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingFloor() throws Exception {
        // Initialize the database
        floorRepository.save(floor);

        int databaseSizeBeforeUpdate = floorRepository.findAll().size();

        // Update the floor
        Floor updatedFloor = floorRepository.findById(floor.getId()).orElseThrow();
        updatedFloor.name(UPDATED_NAME).numFloor(UPDATED_NUM_FLOOR).backgroundColor(UPDATED_BACKGROUND_COLOR);
        FloorDTO floorDTO = floorMapper.toDto(updatedFloor);

        restFloorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, floorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFloor.getNumFloor()).isEqualTo(UPDATED_NUM_FLOOR);
        assertThat(testFloor.getBackgroundColor()).isEqualTo(UPDATED_BACKGROUND_COLOR);
    }

    @Test
    void putNonExistingFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();
        floor.setId(UUID.randomUUID().toString());

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFloorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, floorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();
        floor.setId(UUID.randomUUID().toString());

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();
        floor.setId(UUID.randomUUID().toString());

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFloorWithPatch() throws Exception {
        // Initialize the database
        floorRepository.save(floor);

        int databaseSizeBeforeUpdate = floorRepository.findAll().size();

        // Update the floor using partial update
        Floor partialUpdatedFloor = new Floor();
        partialUpdatedFloor.setId(floor.getId());

        partialUpdatedFloor.numFloor(UPDATED_NUM_FLOOR).backgroundColor(UPDATED_BACKGROUND_COLOR);

        restFloorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFloor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFloor))
            )
            .andExpect(status().isOk());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFloor.getNumFloor()).isEqualTo(UPDATED_NUM_FLOOR);
        assertThat(testFloor.getBackgroundColor()).isEqualTo(UPDATED_BACKGROUND_COLOR);
    }

    @Test
    void fullUpdateFloorWithPatch() throws Exception {
        // Initialize the database
        floorRepository.save(floor);

        int databaseSizeBeforeUpdate = floorRepository.findAll().size();

        // Update the floor using partial update
        Floor partialUpdatedFloor = new Floor();
        partialUpdatedFloor.setId(floor.getId());

        partialUpdatedFloor.name(UPDATED_NAME).numFloor(UPDATED_NUM_FLOOR).backgroundColor(UPDATED_BACKGROUND_COLOR);

        restFloorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFloor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFloor))
            )
            .andExpect(status().isOk());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
        Floor testFloor = floorList.get(floorList.size() - 1);
        assertThat(testFloor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFloor.getNumFloor()).isEqualTo(UPDATED_NUM_FLOOR);
        assertThat(testFloor.getBackgroundColor()).isEqualTo(UPDATED_BACKGROUND_COLOR);
    }

    @Test
    void patchNonExistingFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();
        floor.setId(UUID.randomUUID().toString());

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFloorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, floorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();
        floor.setId(UUID.randomUUID().toString());

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(floorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFloor() throws Exception {
        int databaseSizeBeforeUpdate = floorRepository.findAll().size();
        floor.setId(UUID.randomUUID().toString());

        // Create the Floor
        FloorDTO floorDTO = floorMapper.toDto(floor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFloorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(floorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Floor in the database
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFloor() throws Exception {
        // Initialize the database
        floorRepository.save(floor);

        int databaseSizeBeforeDelete = floorRepository.findAll().size();

        // Delete the floor
        restFloorMockMvc
            .perform(delete(ENTITY_API_URL_ID, floor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Floor> floorList = floorRepository.findAll();
        assertThat(floorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
