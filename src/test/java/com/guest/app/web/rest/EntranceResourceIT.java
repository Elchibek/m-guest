package com.guest.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.guest.app.IntegrationTest;
import com.guest.app.domain.Entrance;
import com.guest.app.domain.GuestBlock;
import com.guest.app.repository.EntranceRepository;
import com.guest.app.service.dto.EntranceDTO;
import com.guest.app.service.mapper.EntranceMapper;
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
 * Integration tests for the {@link EntranceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntranceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_ENTRANCE = 1;
    private static final Integer UPDATED_NUM_ENTRANCE = 2;

    private static final String ENTITY_API_URL = "/api/entrances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private EntranceRepository entranceRepository;

    @Autowired
    private EntranceMapper entranceMapper;

    @Autowired
    private MockMvc restEntranceMockMvc;

    private Entrance entrance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entrance createEntity() {
        Entrance entrance = new Entrance().name(DEFAULT_NAME).numEntrance(DEFAULT_NUM_ENTRANCE);
        // Add required entity
        GuestBlock guestBlock;
        guestBlock = GuestBlockResourceIT.createEntity();
        guestBlock.setId("fixed-id-for-tests");
        entrance.setGuestBlock(guestBlock);
        return entrance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entrance createUpdatedEntity() {
        Entrance entrance = new Entrance().name(UPDATED_NAME).numEntrance(UPDATED_NUM_ENTRANCE);
        // Add required entity
        GuestBlock guestBlock;
        guestBlock = GuestBlockResourceIT.createUpdatedEntity();
        guestBlock.setId("fixed-id-for-tests");
        entrance.setGuestBlock(guestBlock);
        return entrance;
    }

    @BeforeEach
    public void initTest() {
        entranceRepository.deleteAll();
        entrance = createEntity();
    }

    @Test
    void createEntrance() throws Exception {
        int databaseSizeBeforeCreate = entranceRepository.findAll().size();
        // Create the Entrance
        EntranceDTO entranceDTO = entranceMapper.toDto(entrance);
        restEntranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entranceDTO)))
            .andExpect(status().isCreated());

        // Validate the Entrance in the database
        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeCreate + 1);
        Entrance testEntrance = entranceList.get(entranceList.size() - 1);
        assertThat(testEntrance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEntrance.getNumEntrance()).isEqualTo(DEFAULT_NUM_ENTRANCE);
    }

    @Test
    void createEntranceWithExistingId() throws Exception {
        // Create the Entrance with an existing ID
        entrance.setId("existing_id");
        EntranceDTO entranceDTO = entranceMapper.toDto(entrance);

        int databaseSizeBeforeCreate = entranceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entranceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entrance in the database
        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = entranceRepository.findAll().size();
        // set the field null
        entrance.setName(null);

        // Create the Entrance, which fails.
        EntranceDTO entranceDTO = entranceMapper.toDto(entrance);

        restEntranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entranceDTO)))
            .andExpect(status().isBadRequest());

        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNumEntranceIsRequired() throws Exception {
        int databaseSizeBeforeTest = entranceRepository.findAll().size();
        // set the field null
        entrance.setNumEntrance(null);

        // Create the Entrance, which fails.
        EntranceDTO entranceDTO = entranceMapper.toDto(entrance);

        restEntranceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entranceDTO)))
            .andExpect(status().isBadRequest());

        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllEntrances() throws Exception {
        // Initialize the database
        entranceRepository.save(entrance);

        // Get all the entranceList
        restEntranceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrance.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].numEntrance").value(hasItem(DEFAULT_NUM_ENTRANCE)));
    }

    @Test
    void getEntrance() throws Exception {
        // Initialize the database
        entranceRepository.save(entrance);

        // Get the entrance
        restEntranceMockMvc
            .perform(get(ENTITY_API_URL_ID, entrance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entrance.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.numEntrance").value(DEFAULT_NUM_ENTRANCE));
    }

    @Test
    void getNonExistingEntrance() throws Exception {
        // Get the entrance
        restEntranceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingEntrance() throws Exception {
        // Initialize the database
        entranceRepository.save(entrance);

        int databaseSizeBeforeUpdate = entranceRepository.findAll().size();

        // Update the entrance
        Entrance updatedEntrance = entranceRepository.findById(entrance.getId()).orElseThrow();
        updatedEntrance.name(UPDATED_NAME).numEntrance(UPDATED_NUM_ENTRANCE);
        EntranceDTO entranceDTO = entranceMapper.toDto(updatedEntrance);

        restEntranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entranceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entranceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Entrance in the database
        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeUpdate);
        Entrance testEntrance = entranceList.get(entranceList.size() - 1);
        assertThat(testEntrance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEntrance.getNumEntrance()).isEqualTo(UPDATED_NUM_ENTRANCE);
    }

    @Test
    void putNonExistingEntrance() throws Exception {
        int databaseSizeBeforeUpdate = entranceRepository.findAll().size();
        entrance.setId(UUID.randomUUID().toString());

        // Create the Entrance
        EntranceDTO entranceDTO = entranceMapper.toDto(entrance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entranceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entrance in the database
        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchEntrance() throws Exception {
        int databaseSizeBeforeUpdate = entranceRepository.findAll().size();
        entrance.setId(UUID.randomUUID().toString());

        // Create the Entrance
        EntranceDTO entranceDTO = entranceMapper.toDto(entrance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntranceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entrance in the database
        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamEntrance() throws Exception {
        int databaseSizeBeforeUpdate = entranceRepository.findAll().size();
        entrance.setId(UUID.randomUUID().toString());

        // Create the Entrance
        EntranceDTO entranceDTO = entranceMapper.toDto(entrance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntranceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entranceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entrance in the database
        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateEntranceWithPatch() throws Exception {
        // Initialize the database
        entranceRepository.save(entrance);

        int databaseSizeBeforeUpdate = entranceRepository.findAll().size();

        // Update the entrance using partial update
        Entrance partialUpdatedEntrance = new Entrance();
        partialUpdatedEntrance.setId(entrance.getId());

        partialUpdatedEntrance.numEntrance(UPDATED_NUM_ENTRANCE);

        restEntranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntrance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntrance))
            )
            .andExpect(status().isOk());

        // Validate the Entrance in the database
        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeUpdate);
        Entrance testEntrance = entranceList.get(entranceList.size() - 1);
        assertThat(testEntrance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEntrance.getNumEntrance()).isEqualTo(UPDATED_NUM_ENTRANCE);
    }

    @Test
    void fullUpdateEntranceWithPatch() throws Exception {
        // Initialize the database
        entranceRepository.save(entrance);

        int databaseSizeBeforeUpdate = entranceRepository.findAll().size();

        // Update the entrance using partial update
        Entrance partialUpdatedEntrance = new Entrance();
        partialUpdatedEntrance.setId(entrance.getId());

        partialUpdatedEntrance.name(UPDATED_NAME).numEntrance(UPDATED_NUM_ENTRANCE);

        restEntranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntrance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntrance))
            )
            .andExpect(status().isOk());

        // Validate the Entrance in the database
        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeUpdate);
        Entrance testEntrance = entranceList.get(entranceList.size() - 1);
        assertThat(testEntrance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEntrance.getNumEntrance()).isEqualTo(UPDATED_NUM_ENTRANCE);
    }

    @Test
    void patchNonExistingEntrance() throws Exception {
        int databaseSizeBeforeUpdate = entranceRepository.findAll().size();
        entrance.setId(UUID.randomUUID().toString());

        // Create the Entrance
        EntranceDTO entranceDTO = entranceMapper.toDto(entrance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entranceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entrance in the database
        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchEntrance() throws Exception {
        int databaseSizeBeforeUpdate = entranceRepository.findAll().size();
        entrance.setId(UUID.randomUUID().toString());

        // Create the Entrance
        EntranceDTO entranceDTO = entranceMapper.toDto(entrance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntranceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entranceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entrance in the database
        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamEntrance() throws Exception {
        int databaseSizeBeforeUpdate = entranceRepository.findAll().size();
        entrance.setId(UUID.randomUUID().toString());

        // Create the Entrance
        EntranceDTO entranceDTO = entranceMapper.toDto(entrance);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntranceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entranceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entrance in the database
        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteEntrance() throws Exception {
        // Initialize the database
        entranceRepository.save(entrance);

        int databaseSizeBeforeDelete = entranceRepository.findAll().size();

        // Delete the entrance
        restEntranceMockMvc
            .perform(delete(ENTITY_API_URL_ID, entrance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entrance> entranceList = entranceRepository.findAll();
        assertThat(entranceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
