package com.guest.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.guest.app.IntegrationTest;
import com.guest.app.domain.GuestContact;
import com.guest.app.repository.GuestContactRepository;
import com.guest.app.service.dto.GuestContactDTO;
import com.guest.app.service.mapper.GuestContactMapper;
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
 * Integration tests for the {@link GuestContactResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GuestContactResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EXPLANATION = "AAAAAAAAAA";
    private static final String UPDATED_EXPLANATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/guest-contacts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GuestContactRepository guestContactRepository;

    @Autowired
    private GuestContactMapper guestContactMapper;

    @Autowired
    private MockMvc restGuestContactMockMvc;

    private GuestContact guestContact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuestContact createEntity() {
        GuestContact guestContact = new GuestContact().name(DEFAULT_NAME).phone(DEFAULT_PHONE).explanation(DEFAULT_EXPLANATION);
        return guestContact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuestContact createUpdatedEntity() {
        GuestContact guestContact = new GuestContact().name(UPDATED_NAME).phone(UPDATED_PHONE).explanation(UPDATED_EXPLANATION);
        return guestContact;
    }

    @BeforeEach
    public void initTest() {
        guestContactRepository.deleteAll();
        guestContact = createEntity();
    }

    @Test
    void createGuestContact() throws Exception {
        int databaseSizeBeforeCreate = guestContactRepository.findAll().size();
        // Create the GuestContact
        GuestContactDTO guestContactDTO = guestContactMapper.toDto(guestContact);
        restGuestContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestContactDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GuestContact in the database
        List<GuestContact> guestContactList = guestContactRepository.findAll();
        assertThat(guestContactList).hasSize(databaseSizeBeforeCreate + 1);
        GuestContact testGuestContact = guestContactList.get(guestContactList.size() - 1);
        assertThat(testGuestContact.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGuestContact.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testGuestContact.getExplanation()).isEqualTo(DEFAULT_EXPLANATION);
    }

    @Test
    void createGuestContactWithExistingId() throws Exception {
        // Create the GuestContact with an existing ID
        guestContact.setId("existing_id");
        GuestContactDTO guestContactDTO = guestContactMapper.toDto(guestContact);

        int databaseSizeBeforeCreate = guestContactRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuestContactMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestContact in the database
        List<GuestContact> guestContactList = guestContactRepository.findAll();
        assertThat(guestContactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllGuestContacts() throws Exception {
        // Initialize the database
        guestContactRepository.save(guestContact);

        // Get all the guestContactList
        restGuestContactMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guestContact.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].explanation").value(hasItem(DEFAULT_EXPLANATION)));
    }

    @Test
    void getGuestContact() throws Exception {
        // Initialize the database
        guestContactRepository.save(guestContact);

        // Get the guestContact
        restGuestContactMockMvc
            .perform(get(ENTITY_API_URL_ID, guestContact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guestContact.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.explanation").value(DEFAULT_EXPLANATION));
    }

    @Test
    void getNonExistingGuestContact() throws Exception {
        // Get the guestContact
        restGuestContactMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingGuestContact() throws Exception {
        // Initialize the database
        guestContactRepository.save(guestContact);

        int databaseSizeBeforeUpdate = guestContactRepository.findAll().size();

        // Update the guestContact
        GuestContact updatedGuestContact = guestContactRepository.findById(guestContact.getId()).orElseThrow();
        updatedGuestContact.name(UPDATED_NAME).phone(UPDATED_PHONE).explanation(UPDATED_EXPLANATION);
        GuestContactDTO guestContactDTO = guestContactMapper.toDto(updatedGuestContact);

        restGuestContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestContactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestContactDTO))
            )
            .andExpect(status().isOk());

        // Validate the GuestContact in the database
        List<GuestContact> guestContactList = guestContactRepository.findAll();
        assertThat(guestContactList).hasSize(databaseSizeBeforeUpdate);
        GuestContact testGuestContact = guestContactList.get(guestContactList.size() - 1);
        assertThat(testGuestContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuestContact.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testGuestContact.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
    }

    @Test
    void putNonExistingGuestContact() throws Exception {
        int databaseSizeBeforeUpdate = guestContactRepository.findAll().size();
        guestContact.setId(UUID.randomUUID().toString());

        // Create the GuestContact
        GuestContactDTO guestContactDTO = guestContactMapper.toDto(guestContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestContactDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestContact in the database
        List<GuestContact> guestContactList = guestContactRepository.findAll();
        assertThat(guestContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGuestContact() throws Exception {
        int databaseSizeBeforeUpdate = guestContactRepository.findAll().size();
        guestContact.setId(UUID.randomUUID().toString());

        // Create the GuestContact
        GuestContactDTO guestContactDTO = guestContactMapper.toDto(guestContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestContactMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestContact in the database
        List<GuestContact> guestContactList = guestContactRepository.findAll();
        assertThat(guestContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGuestContact() throws Exception {
        int databaseSizeBeforeUpdate = guestContactRepository.findAll().size();
        guestContact.setId(UUID.randomUUID().toString());

        // Create the GuestContact
        GuestContactDTO guestContactDTO = guestContactMapper.toDto(guestContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestContactMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestContactDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuestContact in the database
        List<GuestContact> guestContactList = guestContactRepository.findAll();
        assertThat(guestContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGuestContactWithPatch() throws Exception {
        // Initialize the database
        guestContactRepository.save(guestContact);

        int databaseSizeBeforeUpdate = guestContactRepository.findAll().size();

        // Update the guestContact using partial update
        GuestContact partialUpdatedGuestContact = new GuestContact();
        partialUpdatedGuestContact.setId(guestContact.getId());

        partialUpdatedGuestContact.name(UPDATED_NAME).phone(UPDATED_PHONE);

        restGuestContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuestContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuestContact))
            )
            .andExpect(status().isOk());

        // Validate the GuestContact in the database
        List<GuestContact> guestContactList = guestContactRepository.findAll();
        assertThat(guestContactList).hasSize(databaseSizeBeforeUpdate);
        GuestContact testGuestContact = guestContactList.get(guestContactList.size() - 1);
        assertThat(testGuestContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuestContact.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testGuestContact.getExplanation()).isEqualTo(DEFAULT_EXPLANATION);
    }

    @Test
    void fullUpdateGuestContactWithPatch() throws Exception {
        // Initialize the database
        guestContactRepository.save(guestContact);

        int databaseSizeBeforeUpdate = guestContactRepository.findAll().size();

        // Update the guestContact using partial update
        GuestContact partialUpdatedGuestContact = new GuestContact();
        partialUpdatedGuestContact.setId(guestContact.getId());

        partialUpdatedGuestContact.name(UPDATED_NAME).phone(UPDATED_PHONE).explanation(UPDATED_EXPLANATION);

        restGuestContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuestContact.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuestContact))
            )
            .andExpect(status().isOk());

        // Validate the GuestContact in the database
        List<GuestContact> guestContactList = guestContactRepository.findAll();
        assertThat(guestContactList).hasSize(databaseSizeBeforeUpdate);
        GuestContact testGuestContact = guestContactList.get(guestContactList.size() - 1);
        assertThat(testGuestContact.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuestContact.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testGuestContact.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
    }

    @Test
    void patchNonExistingGuestContact() throws Exception {
        int databaseSizeBeforeUpdate = guestContactRepository.findAll().size();
        guestContact.setId(UUID.randomUUID().toString());

        // Create the GuestContact
        GuestContactDTO guestContactDTO = guestContactMapper.toDto(guestContact);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, guestContactDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestContact in the database
        List<GuestContact> guestContactList = guestContactRepository.findAll();
        assertThat(guestContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGuestContact() throws Exception {
        int databaseSizeBeforeUpdate = guestContactRepository.findAll().size();
        guestContact.setId(UUID.randomUUID().toString());

        // Create the GuestContact
        GuestContactDTO guestContactDTO = guestContactMapper.toDto(guestContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestContactMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestContactDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestContact in the database
        List<GuestContact> guestContactList = guestContactRepository.findAll();
        assertThat(guestContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGuestContact() throws Exception {
        int databaseSizeBeforeUpdate = guestContactRepository.findAll().size();
        guestContact.setId(UUID.randomUUID().toString());

        // Create the GuestContact
        GuestContactDTO guestContactDTO = guestContactMapper.toDto(guestContact);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestContactMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestContactDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuestContact in the database
        List<GuestContact> guestContactList = guestContactRepository.findAll();
        assertThat(guestContactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGuestContact() throws Exception {
        // Initialize the database
        guestContactRepository.save(guestContact);

        int databaseSizeBeforeDelete = guestContactRepository.findAll().size();

        // Delete the guestContact
        restGuestContactMockMvc
            .perform(delete(ENTITY_API_URL_ID, guestContact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GuestContact> guestContactList = guestContactRepository.findAll();
        assertThat(guestContactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
