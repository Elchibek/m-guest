package com.guest.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.guest.app.IntegrationTest;
import com.guest.app.domain.GuestFrom;
import com.guest.app.repository.GuestFromRepository;
import com.guest.app.service.dto.GuestFromDTO;
import com.guest.app.service.mapper.GuestFromMapper;
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
 * Integration tests for the {@link GuestFromResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GuestFromResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/guest-froms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GuestFromRepository guestFromRepository;

    @Autowired
    private GuestFromMapper guestFromMapper;

    @Autowired
    private MockMvc restGuestFromMockMvc;

    private GuestFrom guestFrom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuestFrom createEntity() {
        GuestFrom guestFrom = new GuestFrom().name(DEFAULT_NAME);
        return guestFrom;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuestFrom createUpdatedEntity() {
        GuestFrom guestFrom = new GuestFrom().name(UPDATED_NAME);
        return guestFrom;
    }

    @BeforeEach
    public void initTest() {
        guestFromRepository.deleteAll();
        guestFrom = createEntity();
    }

    @Test
    void createGuestFrom() throws Exception {
        int databaseSizeBeforeCreate = guestFromRepository.findAll().size();
        // Create the GuestFrom
        GuestFromDTO guestFromDTO = guestFromMapper.toDto(guestFrom);
        restGuestFromMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestFromDTO)))
            .andExpect(status().isCreated());

        // Validate the GuestFrom in the database
        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeCreate + 1);
        GuestFrom testGuestFrom = guestFromList.get(guestFromList.size() - 1);
        assertThat(testGuestFrom.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void createGuestFromWithExistingId() throws Exception {
        // Create the GuestFrom with an existing ID
        guestFrom.setId("existing_id");
        GuestFromDTO guestFromDTO = guestFromMapper.toDto(guestFrom);

        int databaseSizeBeforeCreate = guestFromRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuestFromMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestFromDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GuestFrom in the database
        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = guestFromRepository.findAll().size();
        // set the field null
        guestFrom.setName(null);

        // Create the GuestFrom, which fails.
        GuestFromDTO guestFromDTO = guestFromMapper.toDto(guestFrom);

        restGuestFromMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestFromDTO)))
            .andExpect(status().isBadRequest());

        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllGuestFroms() throws Exception {
        // Initialize the database
        guestFromRepository.save(guestFrom);

        // Get all the guestFromList
        restGuestFromMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guestFrom.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    void getGuestFrom() throws Exception {
        // Initialize the database
        guestFromRepository.save(guestFrom);

        // Get the guestFrom
        restGuestFromMockMvc
            .perform(get(ENTITY_API_URL_ID, guestFrom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guestFrom.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    void getNonExistingGuestFrom() throws Exception {
        // Get the guestFrom
        restGuestFromMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingGuestFrom() throws Exception {
        // Initialize the database
        guestFromRepository.save(guestFrom);

        int databaseSizeBeforeUpdate = guestFromRepository.findAll().size();

        // Update the guestFrom
        GuestFrom updatedGuestFrom = guestFromRepository.findById(guestFrom.getId()).orElseThrow();
        updatedGuestFrom.name(UPDATED_NAME);
        GuestFromDTO guestFromDTO = guestFromMapper.toDto(updatedGuestFrom);

        restGuestFromMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestFromDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestFromDTO))
            )
            .andExpect(status().isOk());

        // Validate the GuestFrom in the database
        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeUpdate);
        GuestFrom testGuestFrom = guestFromList.get(guestFromList.size() - 1);
        assertThat(testGuestFrom.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void putNonExistingGuestFrom() throws Exception {
        int databaseSizeBeforeUpdate = guestFromRepository.findAll().size();
        guestFrom.setId(UUID.randomUUID().toString());

        // Create the GuestFrom
        GuestFromDTO guestFromDTO = guestFromMapper.toDto(guestFrom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestFromMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestFromDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestFromDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestFrom in the database
        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGuestFrom() throws Exception {
        int databaseSizeBeforeUpdate = guestFromRepository.findAll().size();
        guestFrom.setId(UUID.randomUUID().toString());

        // Create the GuestFrom
        GuestFromDTO guestFromDTO = guestFromMapper.toDto(guestFrom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestFromMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestFromDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestFrom in the database
        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGuestFrom() throws Exception {
        int databaseSizeBeforeUpdate = guestFromRepository.findAll().size();
        guestFrom.setId(UUID.randomUUID().toString());

        // Create the GuestFrom
        GuestFromDTO guestFromDTO = guestFromMapper.toDto(guestFrom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestFromMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestFromDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuestFrom in the database
        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGuestFromWithPatch() throws Exception {
        // Initialize the database
        guestFromRepository.save(guestFrom);

        int databaseSizeBeforeUpdate = guestFromRepository.findAll().size();

        // Update the guestFrom using partial update
        GuestFrom partialUpdatedGuestFrom = new GuestFrom();
        partialUpdatedGuestFrom.setId(guestFrom.getId());

        restGuestFromMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuestFrom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuestFrom))
            )
            .andExpect(status().isOk());

        // Validate the GuestFrom in the database
        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeUpdate);
        GuestFrom testGuestFrom = guestFromList.get(guestFromList.size() - 1);
        assertThat(testGuestFrom.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    void fullUpdateGuestFromWithPatch() throws Exception {
        // Initialize the database
        guestFromRepository.save(guestFrom);

        int databaseSizeBeforeUpdate = guestFromRepository.findAll().size();

        // Update the guestFrom using partial update
        GuestFrom partialUpdatedGuestFrom = new GuestFrom();
        partialUpdatedGuestFrom.setId(guestFrom.getId());

        partialUpdatedGuestFrom.name(UPDATED_NAME);

        restGuestFromMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuestFrom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuestFrom))
            )
            .andExpect(status().isOk());

        // Validate the GuestFrom in the database
        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeUpdate);
        GuestFrom testGuestFrom = guestFromList.get(guestFromList.size() - 1);
        assertThat(testGuestFrom.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    void patchNonExistingGuestFrom() throws Exception {
        int databaseSizeBeforeUpdate = guestFromRepository.findAll().size();
        guestFrom.setId(UUID.randomUUID().toString());

        // Create the GuestFrom
        GuestFromDTO guestFromDTO = guestFromMapper.toDto(guestFrom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestFromMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, guestFromDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestFromDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestFrom in the database
        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGuestFrom() throws Exception {
        int databaseSizeBeforeUpdate = guestFromRepository.findAll().size();
        guestFrom.setId(UUID.randomUUID().toString());

        // Create the GuestFrom
        GuestFromDTO guestFromDTO = guestFromMapper.toDto(guestFrom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestFromMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestFromDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestFrom in the database
        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGuestFrom() throws Exception {
        int databaseSizeBeforeUpdate = guestFromRepository.findAll().size();
        guestFrom.setId(UUID.randomUUID().toString());

        // Create the GuestFrom
        GuestFromDTO guestFromDTO = guestFromMapper.toDto(guestFrom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestFromMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(guestFromDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuestFrom in the database
        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGuestFrom() throws Exception {
        // Initialize the database
        guestFromRepository.save(guestFrom);

        int databaseSizeBeforeDelete = guestFromRepository.findAll().size();

        // Delete the guestFrom
        restGuestFromMockMvc
            .perform(delete(ENTITY_API_URL_ID, guestFrom.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GuestFrom> guestFromList = guestFromRepository.findAll();
        assertThat(guestFromList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
