package com.guest.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.guest.app.IntegrationTest;
import com.guest.app.domain.Floor;
import com.guest.app.domain.GuestHouse;
import com.guest.app.repository.GuestHouseRepository;
import com.guest.app.service.dto.GuestHouseDTO;
import com.guest.app.service.mapper.GuestHouseMapper;
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
 * Integration tests for the {@link GuestHouseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GuestHouseResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_EMPTY = false;
    private static final Boolean UPDATED_IS_EMPTY = true;

    private static final Integer DEFAULT_COUNT_PERSON = 1;
    private static final Integer UPDATED_COUNT_PERSON = 2;

    private static final String DEFAULT_BACKGROUND_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_COLOR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/guest-houses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GuestHouseRepository guestHouseRepository;

    @Autowired
    private GuestHouseMapper guestHouseMapper;

    @Autowired
    private MockMvc restGuestHouseMockMvc;

    private GuestHouse guestHouse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuestHouse createEntity() {
        GuestHouse guestHouse = new GuestHouse()
            .name(DEFAULT_NAME)
            .isEmpty(DEFAULT_IS_EMPTY)
            .countPerson(DEFAULT_COUNT_PERSON)
            .backgroundColor(DEFAULT_BACKGROUND_COLOR);
        // Add required entity
        Floor floor;
        floor = FloorResourceIT.createEntity();
        floor.setId("fixed-id-for-tests");
        guestHouse.setFloor(floor);
        return guestHouse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuestHouse createUpdatedEntity() {
        GuestHouse guestHouse = new GuestHouse()
            .name(UPDATED_NAME)
            .isEmpty(UPDATED_IS_EMPTY)
            .countPerson(UPDATED_COUNT_PERSON)
            .backgroundColor(UPDATED_BACKGROUND_COLOR);
        // Add required entity
        Floor floor;
        floor = FloorResourceIT.createUpdatedEntity();
        floor.setId("fixed-id-for-tests");
        guestHouse.setFloor(floor);
        return guestHouse;
    }

    @BeforeEach
    public void initTest() {
        guestHouseRepository.deleteAll();
        guestHouse = createEntity();
    }

    @Test
    void createGuestHouse() throws Exception {
        int databaseSizeBeforeCreate = guestHouseRepository.findAll().size();
        // Create the GuestHouse
        GuestHouseDTO guestHouseDTO = guestHouseMapper.toDto(guestHouse);
        restGuestHouseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestHouseDTO)))
            .andExpect(status().isCreated());

        // Validate the GuestHouse in the database
        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeCreate + 1);
        GuestHouse testGuestHouse = guestHouseList.get(guestHouseList.size() - 1);
        assertThat(testGuestHouse.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGuestHouse.getIsEmpty()).isEqualTo(DEFAULT_IS_EMPTY);
        assertThat(testGuestHouse.getCountPerson()).isEqualTo(DEFAULT_COUNT_PERSON);
        assertThat(testGuestHouse.getBackgroundColor()).isEqualTo(DEFAULT_BACKGROUND_COLOR);
    }

    @Test
    void createGuestHouseWithExistingId() throws Exception {
        // Create the GuestHouse with an existing ID
        guestHouse.setId("existing_id");
        GuestHouseDTO guestHouseDTO = guestHouseMapper.toDto(guestHouse);

        int databaseSizeBeforeCreate = guestHouseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuestHouseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestHouseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GuestHouse in the database
        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = guestHouseRepository.findAll().size();
        // set the field null
        guestHouse.setName(null);

        // Create the GuestHouse, which fails.
        GuestHouseDTO guestHouseDTO = guestHouseMapper.toDto(guestHouse);

        restGuestHouseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestHouseDTO)))
            .andExpect(status().isBadRequest());

        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllGuestHouses() throws Exception {
        // Initialize the database
        guestHouseRepository.save(guestHouse);

        // Get all the guestHouseList
        restGuestHouseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guestHouse.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isEmpty").value(hasItem(DEFAULT_IS_EMPTY.booleanValue())))
            .andExpect(jsonPath("$.[*].countPerson").value(hasItem(DEFAULT_COUNT_PERSON)))
            .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR)));
    }

    @Test
    void getGuestHouse() throws Exception {
        // Initialize the database
        guestHouseRepository.save(guestHouse);

        // Get the guestHouse
        restGuestHouseMockMvc
            .perform(get(ENTITY_API_URL_ID, guestHouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guestHouse.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isEmpty").value(DEFAULT_IS_EMPTY.booleanValue()))
            .andExpect(jsonPath("$.countPerson").value(DEFAULT_COUNT_PERSON))
            .andExpect(jsonPath("$.backgroundColor").value(DEFAULT_BACKGROUND_COLOR));
    }

    @Test
    void getNonExistingGuestHouse() throws Exception {
        // Get the guestHouse
        restGuestHouseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingGuestHouse() throws Exception {
        // Initialize the database
        guestHouseRepository.save(guestHouse);

        int databaseSizeBeforeUpdate = guestHouseRepository.findAll().size();

        // Update the guestHouse
        GuestHouse updatedGuestHouse = guestHouseRepository.findById(guestHouse.getId()).orElseThrow();
        updatedGuestHouse
            .name(UPDATED_NAME)
            .isEmpty(UPDATED_IS_EMPTY)
            .countPerson(UPDATED_COUNT_PERSON)
            .backgroundColor(UPDATED_BACKGROUND_COLOR);
        GuestHouseDTO guestHouseDTO = guestHouseMapper.toDto(updatedGuestHouse);

        restGuestHouseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestHouseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestHouseDTO))
            )
            .andExpect(status().isOk());

        // Validate the GuestHouse in the database
        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeUpdate);
        GuestHouse testGuestHouse = guestHouseList.get(guestHouseList.size() - 1);
        assertThat(testGuestHouse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuestHouse.getIsEmpty()).isEqualTo(UPDATED_IS_EMPTY);
        assertThat(testGuestHouse.getCountPerson()).isEqualTo(UPDATED_COUNT_PERSON);
        assertThat(testGuestHouse.getBackgroundColor()).isEqualTo(UPDATED_BACKGROUND_COLOR);
    }

    @Test
    void putNonExistingGuestHouse() throws Exception {
        int databaseSizeBeforeUpdate = guestHouseRepository.findAll().size();
        guestHouse.setId(UUID.randomUUID().toString());

        // Create the GuestHouse
        GuestHouseDTO guestHouseDTO = guestHouseMapper.toDto(guestHouse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestHouseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestHouseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestHouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestHouse in the database
        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGuestHouse() throws Exception {
        int databaseSizeBeforeUpdate = guestHouseRepository.findAll().size();
        guestHouse.setId(UUID.randomUUID().toString());

        // Create the GuestHouse
        GuestHouseDTO guestHouseDTO = guestHouseMapper.toDto(guestHouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestHouseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestHouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestHouse in the database
        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGuestHouse() throws Exception {
        int databaseSizeBeforeUpdate = guestHouseRepository.findAll().size();
        guestHouse.setId(UUID.randomUUID().toString());

        // Create the GuestHouse
        GuestHouseDTO guestHouseDTO = guestHouseMapper.toDto(guestHouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestHouseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestHouseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuestHouse in the database
        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGuestHouseWithPatch() throws Exception {
        // Initialize the database
        guestHouseRepository.save(guestHouse);

        int databaseSizeBeforeUpdate = guestHouseRepository.findAll().size();

        // Update the guestHouse using partial update
        GuestHouse partialUpdatedGuestHouse = new GuestHouse();
        partialUpdatedGuestHouse.setId(guestHouse.getId());

        partialUpdatedGuestHouse.name(UPDATED_NAME);

        restGuestHouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuestHouse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuestHouse))
            )
            .andExpect(status().isOk());

        // Validate the GuestHouse in the database
        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeUpdate);
        GuestHouse testGuestHouse = guestHouseList.get(guestHouseList.size() - 1);
        assertThat(testGuestHouse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuestHouse.getIsEmpty()).isEqualTo(DEFAULT_IS_EMPTY);
        assertThat(testGuestHouse.getCountPerson()).isEqualTo(DEFAULT_COUNT_PERSON);
        assertThat(testGuestHouse.getBackgroundColor()).isEqualTo(DEFAULT_BACKGROUND_COLOR);
    }

    @Test
    void fullUpdateGuestHouseWithPatch() throws Exception {
        // Initialize the database
        guestHouseRepository.save(guestHouse);

        int databaseSizeBeforeUpdate = guestHouseRepository.findAll().size();

        // Update the guestHouse using partial update
        GuestHouse partialUpdatedGuestHouse = new GuestHouse();
        partialUpdatedGuestHouse.setId(guestHouse.getId());

        partialUpdatedGuestHouse
            .name(UPDATED_NAME)
            .isEmpty(UPDATED_IS_EMPTY)
            .countPerson(UPDATED_COUNT_PERSON)
            .backgroundColor(UPDATED_BACKGROUND_COLOR);

        restGuestHouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuestHouse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuestHouse))
            )
            .andExpect(status().isOk());

        // Validate the GuestHouse in the database
        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeUpdate);
        GuestHouse testGuestHouse = guestHouseList.get(guestHouseList.size() - 1);
        assertThat(testGuestHouse.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuestHouse.getIsEmpty()).isEqualTo(UPDATED_IS_EMPTY);
        assertThat(testGuestHouse.getCountPerson()).isEqualTo(UPDATED_COUNT_PERSON);
        assertThat(testGuestHouse.getBackgroundColor()).isEqualTo(UPDATED_BACKGROUND_COLOR);
    }

    @Test
    void patchNonExistingGuestHouse() throws Exception {
        int databaseSizeBeforeUpdate = guestHouseRepository.findAll().size();
        guestHouse.setId(UUID.randomUUID().toString());

        // Create the GuestHouse
        GuestHouseDTO guestHouseDTO = guestHouseMapper.toDto(guestHouse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestHouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, guestHouseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestHouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestHouse in the database
        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGuestHouse() throws Exception {
        int databaseSizeBeforeUpdate = guestHouseRepository.findAll().size();
        guestHouse.setId(UUID.randomUUID().toString());

        // Create the GuestHouse
        GuestHouseDTO guestHouseDTO = guestHouseMapper.toDto(guestHouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestHouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestHouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestHouse in the database
        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGuestHouse() throws Exception {
        int databaseSizeBeforeUpdate = guestHouseRepository.findAll().size();
        guestHouse.setId(UUID.randomUUID().toString());

        // Create the GuestHouse
        GuestHouseDTO guestHouseDTO = guestHouseMapper.toDto(guestHouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestHouseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(guestHouseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuestHouse in the database
        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGuestHouse() throws Exception {
        // Initialize the database
        guestHouseRepository.save(guestHouse);

        int databaseSizeBeforeDelete = guestHouseRepository.findAll().size();

        // Delete the guestHouse
        restGuestHouseMockMvc
            .perform(delete(ENTITY_API_URL_ID, guestHouse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GuestHouse> guestHouseList = guestHouseRepository.findAll();
        assertThat(guestHouseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
