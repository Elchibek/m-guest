package com.guest.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.guest.app.IntegrationTest;
import com.guest.app.domain.GuestBlock;
import com.guest.app.repository.GuestBlockRepository;
import com.guest.app.service.dto.GuestBlockDTO;
import com.guest.app.service.mapper.GuestBlockMapper;
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
 * Integration tests for the {@link GuestBlockResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GuestBlockResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_ENTRANCE = 1;
    private static final Integer UPDATED_NUM_ENTRANCE = 2;

    private static final Integer DEFAULT_NUM_FLOOR = 1;
    private static final Integer UPDATED_NUM_FLOOR = 2;

    private static final Integer DEFAULT_NUM_HOUSE = 1;
    private static final Integer UPDATED_NUM_HOUSE = 2;

    private static final String ENTITY_API_URL = "/api/guest-blocks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GuestBlockRepository guestBlockRepository;

    @Autowired
    private GuestBlockMapper guestBlockMapper;

    @Autowired
    private MockMvc restGuestBlockMockMvc;

    private GuestBlock guestBlock;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuestBlock createEntity() {
        GuestBlock guestBlock = new GuestBlock()
            .name(DEFAULT_NAME)
            .numEntrance(DEFAULT_NUM_ENTRANCE)
            .numFloor(DEFAULT_NUM_FLOOR)
            .numHouse(DEFAULT_NUM_HOUSE);
        return guestBlock;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuestBlock createUpdatedEntity() {
        GuestBlock guestBlock = new GuestBlock()
            .name(UPDATED_NAME)
            .numEntrance(UPDATED_NUM_ENTRANCE)
            .numFloor(UPDATED_NUM_FLOOR)
            .numHouse(UPDATED_NUM_HOUSE);
        return guestBlock;
    }

    @BeforeEach
    public void initTest() {
        guestBlockRepository.deleteAll();
        guestBlock = createEntity();
    }

    @Test
    void createGuestBlock() throws Exception {
        int databaseSizeBeforeCreate = guestBlockRepository.findAll().size();
        // Create the GuestBlock
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(guestBlock);
        restGuestBlockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestBlockDTO)))
            .andExpect(status().isCreated());

        // Validate the GuestBlock in the database
        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeCreate + 1);
        GuestBlock testGuestBlock = guestBlockList.get(guestBlockList.size() - 1);
        assertThat(testGuestBlock.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGuestBlock.getNumEntrance()).isEqualTo(DEFAULT_NUM_ENTRANCE);
        assertThat(testGuestBlock.getNumFloor()).isEqualTo(DEFAULT_NUM_FLOOR);
        assertThat(testGuestBlock.getNumHouse()).isEqualTo(DEFAULT_NUM_HOUSE);
    }

    @Test
    void createGuestBlockWithExistingId() throws Exception {
        // Create the GuestBlock with an existing ID
        guestBlock.setId("existing_id");
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(guestBlock);

        int databaseSizeBeforeCreate = guestBlockRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuestBlockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestBlockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GuestBlock in the database
        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = guestBlockRepository.findAll().size();
        // set the field null
        guestBlock.setName(null);

        // Create the GuestBlock, which fails.
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(guestBlock);

        restGuestBlockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestBlockDTO)))
            .andExpect(status().isBadRequest());

        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNumEntranceIsRequired() throws Exception {
        int databaseSizeBeforeTest = guestBlockRepository.findAll().size();
        // set the field null
        guestBlock.setNumEntrance(null);

        // Create the GuestBlock, which fails.
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(guestBlock);

        restGuestBlockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestBlockDTO)))
            .andExpect(status().isBadRequest());

        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNumFloorIsRequired() throws Exception {
        int databaseSizeBeforeTest = guestBlockRepository.findAll().size();
        // set the field null
        guestBlock.setNumFloor(null);

        // Create the GuestBlock, which fails.
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(guestBlock);

        restGuestBlockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestBlockDTO)))
            .andExpect(status().isBadRequest());

        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkNumHouseIsRequired() throws Exception {
        int databaseSizeBeforeTest = guestBlockRepository.findAll().size();
        // set the field null
        guestBlock.setNumHouse(null);

        // Create the GuestBlock, which fails.
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(guestBlock);

        restGuestBlockMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestBlockDTO)))
            .andExpect(status().isBadRequest());

        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllGuestBlocks() throws Exception {
        // Initialize the database
        guestBlockRepository.save(guestBlock);

        // Get all the guestBlockList
        restGuestBlockMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guestBlock.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].numEntrance").value(hasItem(DEFAULT_NUM_ENTRANCE)))
            .andExpect(jsonPath("$.[*].numFloor").value(hasItem(DEFAULT_NUM_FLOOR)))
            .andExpect(jsonPath("$.[*].numHouse").value(hasItem(DEFAULT_NUM_HOUSE)));
    }

    @Test
    void getGuestBlock() throws Exception {
        // Initialize the database
        guestBlockRepository.save(guestBlock);

        // Get the guestBlock
        restGuestBlockMockMvc
            .perform(get(ENTITY_API_URL_ID, guestBlock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guestBlock.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.numEntrance").value(DEFAULT_NUM_ENTRANCE))
            .andExpect(jsonPath("$.numFloor").value(DEFAULT_NUM_FLOOR))
            .andExpect(jsonPath("$.numHouse").value(DEFAULT_NUM_HOUSE));
    }

    @Test
    void getNonExistingGuestBlock() throws Exception {
        // Get the guestBlock
        restGuestBlockMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingGuestBlock() throws Exception {
        // Initialize the database
        guestBlockRepository.save(guestBlock);

        int databaseSizeBeforeUpdate = guestBlockRepository.findAll().size();

        // Update the guestBlock
        GuestBlock updatedGuestBlock = guestBlockRepository.findById(guestBlock.getId()).orElseThrow();
        updatedGuestBlock.name(UPDATED_NAME).numEntrance(UPDATED_NUM_ENTRANCE).numFloor(UPDATED_NUM_FLOOR).numHouse(UPDATED_NUM_HOUSE);
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(updatedGuestBlock);

        restGuestBlockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestBlockDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestBlockDTO))
            )
            .andExpect(status().isOk());

        // Validate the GuestBlock in the database
        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeUpdate);
        GuestBlock testGuestBlock = guestBlockList.get(guestBlockList.size() - 1);
        assertThat(testGuestBlock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuestBlock.getNumEntrance()).isEqualTo(UPDATED_NUM_ENTRANCE);
        assertThat(testGuestBlock.getNumFloor()).isEqualTo(UPDATED_NUM_FLOOR);
        assertThat(testGuestBlock.getNumHouse()).isEqualTo(UPDATED_NUM_HOUSE);
    }

    @Test
    void putNonExistingGuestBlock() throws Exception {
        int databaseSizeBeforeUpdate = guestBlockRepository.findAll().size();
        guestBlock.setId(UUID.randomUUID().toString());

        // Create the GuestBlock
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(guestBlock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestBlockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestBlockDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestBlockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestBlock in the database
        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGuestBlock() throws Exception {
        int databaseSizeBeforeUpdate = guestBlockRepository.findAll().size();
        guestBlock.setId(UUID.randomUUID().toString());

        // Create the GuestBlock
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(guestBlock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestBlockMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestBlockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestBlock in the database
        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGuestBlock() throws Exception {
        int databaseSizeBeforeUpdate = guestBlockRepository.findAll().size();
        guestBlock.setId(UUID.randomUUID().toString());

        // Create the GuestBlock
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(guestBlock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestBlockMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestBlockDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuestBlock in the database
        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGuestBlockWithPatch() throws Exception {
        // Initialize the database
        guestBlockRepository.save(guestBlock);

        int databaseSizeBeforeUpdate = guestBlockRepository.findAll().size();

        // Update the guestBlock using partial update
        GuestBlock partialUpdatedGuestBlock = new GuestBlock();
        partialUpdatedGuestBlock.setId(guestBlock.getId());

        partialUpdatedGuestBlock.name(UPDATED_NAME).numEntrance(UPDATED_NUM_ENTRANCE).numFloor(UPDATED_NUM_FLOOR);

        restGuestBlockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuestBlock.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuestBlock))
            )
            .andExpect(status().isOk());

        // Validate the GuestBlock in the database
        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeUpdate);
        GuestBlock testGuestBlock = guestBlockList.get(guestBlockList.size() - 1);
        assertThat(testGuestBlock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuestBlock.getNumEntrance()).isEqualTo(UPDATED_NUM_ENTRANCE);
        assertThat(testGuestBlock.getNumFloor()).isEqualTo(UPDATED_NUM_FLOOR);
        assertThat(testGuestBlock.getNumHouse()).isEqualTo(DEFAULT_NUM_HOUSE);
    }

    @Test
    void fullUpdateGuestBlockWithPatch() throws Exception {
        // Initialize the database
        guestBlockRepository.save(guestBlock);

        int databaseSizeBeforeUpdate = guestBlockRepository.findAll().size();

        // Update the guestBlock using partial update
        GuestBlock partialUpdatedGuestBlock = new GuestBlock();
        partialUpdatedGuestBlock.setId(guestBlock.getId());

        partialUpdatedGuestBlock
            .name(UPDATED_NAME)
            .numEntrance(UPDATED_NUM_ENTRANCE)
            .numFloor(UPDATED_NUM_FLOOR)
            .numHouse(UPDATED_NUM_HOUSE);

        restGuestBlockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuestBlock.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuestBlock))
            )
            .andExpect(status().isOk());

        // Validate the GuestBlock in the database
        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeUpdate);
        GuestBlock testGuestBlock = guestBlockList.get(guestBlockList.size() - 1);
        assertThat(testGuestBlock.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuestBlock.getNumEntrance()).isEqualTo(UPDATED_NUM_ENTRANCE);
        assertThat(testGuestBlock.getNumFloor()).isEqualTo(UPDATED_NUM_FLOOR);
        assertThat(testGuestBlock.getNumHouse()).isEqualTo(UPDATED_NUM_HOUSE);
    }

    @Test
    void patchNonExistingGuestBlock() throws Exception {
        int databaseSizeBeforeUpdate = guestBlockRepository.findAll().size();
        guestBlock.setId(UUID.randomUUID().toString());

        // Create the GuestBlock
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(guestBlock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestBlockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, guestBlockDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestBlockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestBlock in the database
        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGuestBlock() throws Exception {
        int databaseSizeBeforeUpdate = guestBlockRepository.findAll().size();
        guestBlock.setId(UUID.randomUUID().toString());

        // Create the GuestBlock
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(guestBlock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestBlockMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestBlockDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestBlock in the database
        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGuestBlock() throws Exception {
        int databaseSizeBeforeUpdate = guestBlockRepository.findAll().size();
        guestBlock.setId(UUID.randomUUID().toString());

        // Create the GuestBlock
        GuestBlockDTO guestBlockDTO = guestBlockMapper.toDto(guestBlock);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestBlockMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(guestBlockDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuestBlock in the database
        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGuestBlock() throws Exception {
        // Initialize the database
        guestBlockRepository.save(guestBlock);

        int databaseSizeBeforeDelete = guestBlockRepository.findAll().size();

        // Delete the guestBlock
        restGuestBlockMockMvc
            .perform(delete(ENTITY_API_URL_ID, guestBlock.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GuestBlock> guestBlockList = guestBlockRepository.findAll();
        assertThat(guestBlockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
