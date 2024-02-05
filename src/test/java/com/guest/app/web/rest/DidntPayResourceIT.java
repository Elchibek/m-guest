package com.guest.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.guest.app.IntegrationTest;
import com.guest.app.domain.DidntPay;
import com.guest.app.repository.DidntPayRepository;
import com.guest.app.service.dto.DidntPayDTO;
import com.guest.app.service.mapper.DidntPayMapper;
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
 * Integration tests for the {@link DidntPayResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DidntPayResourceIT {

    private static final Integer DEFAULT_COUNT_PERSON = 1;
    private static final Integer UPDATED_COUNT_PERSON = 2;

    private static final Integer DEFAULT_PAID = 1;
    private static final Integer UPDATED_PAID = 2;

    private static final String DEFAULT_EXPLANATION = "AAAAAAAAAA";
    private static final String UPDATED_EXPLANATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/didnt-pays";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private DidntPayRepository didntPayRepository;

    @Autowired
    private DidntPayMapper didntPayMapper;

    @Autowired
    private MockMvc restDidntPayMockMvc;

    private DidntPay didntPay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DidntPay createEntity() {
        DidntPay didntPay = new DidntPay().countPerson(DEFAULT_COUNT_PERSON).paid(DEFAULT_PAID).explanation(DEFAULT_EXPLANATION);
        return didntPay;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DidntPay createUpdatedEntity() {
        DidntPay didntPay = new DidntPay().countPerson(UPDATED_COUNT_PERSON).paid(UPDATED_PAID).explanation(UPDATED_EXPLANATION);
        return didntPay;
    }

    @BeforeEach
    public void initTest() {
        didntPayRepository.deleteAll();
        didntPay = createEntity();
    }

    @Test
    void createDidntPay() throws Exception {
        int databaseSizeBeforeCreate = didntPayRepository.findAll().size();
        // Create the DidntPay
        DidntPayDTO didntPayDTO = didntPayMapper.toDto(didntPay);
        restDidntPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(didntPayDTO)))
            .andExpect(status().isCreated());

        // Validate the DidntPay in the database
        List<DidntPay> didntPayList = didntPayRepository.findAll();
        assertThat(didntPayList).hasSize(databaseSizeBeforeCreate + 1);
        DidntPay testDidntPay = didntPayList.get(didntPayList.size() - 1);
        assertThat(testDidntPay.getCountPerson()).isEqualTo(DEFAULT_COUNT_PERSON);
        assertThat(testDidntPay.getPaid()).isEqualTo(DEFAULT_PAID);
        assertThat(testDidntPay.getExplanation()).isEqualTo(DEFAULT_EXPLANATION);
    }

    @Test
    void createDidntPayWithExistingId() throws Exception {
        // Create the DidntPay with an existing ID
        didntPay.setId("existing_id");
        DidntPayDTO didntPayDTO = didntPayMapper.toDto(didntPay);

        int databaseSizeBeforeCreate = didntPayRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDidntPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(didntPayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DidntPay in the database
        List<DidntPay> didntPayList = didntPayRepository.findAll();
        assertThat(didntPayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllDidntPays() throws Exception {
        // Initialize the database
        didntPayRepository.save(didntPay);

        // Get all the didntPayList
        restDidntPayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(didntPay.getId())))
            .andExpect(jsonPath("$.[*].countPerson").value(hasItem(DEFAULT_COUNT_PERSON)))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID)))
            .andExpect(jsonPath("$.[*].explanation").value(hasItem(DEFAULT_EXPLANATION)));
    }

    @Test
    void getDidntPay() throws Exception {
        // Initialize the database
        didntPayRepository.save(didntPay);

        // Get the didntPay
        restDidntPayMockMvc
            .perform(get(ENTITY_API_URL_ID, didntPay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(didntPay.getId()))
            .andExpect(jsonPath("$.countPerson").value(DEFAULT_COUNT_PERSON))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID))
            .andExpect(jsonPath("$.explanation").value(DEFAULT_EXPLANATION));
    }

    @Test
    void getNonExistingDidntPay() throws Exception {
        // Get the didntPay
        restDidntPayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingDidntPay() throws Exception {
        // Initialize the database
        didntPayRepository.save(didntPay);

        int databaseSizeBeforeUpdate = didntPayRepository.findAll().size();

        // Update the didntPay
        DidntPay updatedDidntPay = didntPayRepository.findById(didntPay.getId()).orElseThrow();
        updatedDidntPay.countPerson(UPDATED_COUNT_PERSON).paid(UPDATED_PAID).explanation(UPDATED_EXPLANATION);
        DidntPayDTO didntPayDTO = didntPayMapper.toDto(updatedDidntPay);

        restDidntPayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, didntPayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(didntPayDTO))
            )
            .andExpect(status().isOk());

        // Validate the DidntPay in the database
        List<DidntPay> didntPayList = didntPayRepository.findAll();
        assertThat(didntPayList).hasSize(databaseSizeBeforeUpdate);
        DidntPay testDidntPay = didntPayList.get(didntPayList.size() - 1);
        assertThat(testDidntPay.getCountPerson()).isEqualTo(UPDATED_COUNT_PERSON);
        assertThat(testDidntPay.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testDidntPay.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
    }

    @Test
    void putNonExistingDidntPay() throws Exception {
        int databaseSizeBeforeUpdate = didntPayRepository.findAll().size();
        didntPay.setId(UUID.randomUUID().toString());

        // Create the DidntPay
        DidntPayDTO didntPayDTO = didntPayMapper.toDto(didntPay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDidntPayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, didntPayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(didntPayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DidntPay in the database
        List<DidntPay> didntPayList = didntPayRepository.findAll();
        assertThat(didntPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchDidntPay() throws Exception {
        int databaseSizeBeforeUpdate = didntPayRepository.findAll().size();
        didntPay.setId(UUID.randomUUID().toString());

        // Create the DidntPay
        DidntPayDTO didntPayDTO = didntPayMapper.toDto(didntPay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDidntPayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(didntPayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DidntPay in the database
        List<DidntPay> didntPayList = didntPayRepository.findAll();
        assertThat(didntPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamDidntPay() throws Exception {
        int databaseSizeBeforeUpdate = didntPayRepository.findAll().size();
        didntPay.setId(UUID.randomUUID().toString());

        // Create the DidntPay
        DidntPayDTO didntPayDTO = didntPayMapper.toDto(didntPay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDidntPayMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(didntPayDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DidntPay in the database
        List<DidntPay> didntPayList = didntPayRepository.findAll();
        assertThat(didntPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateDidntPayWithPatch() throws Exception {
        // Initialize the database
        didntPayRepository.save(didntPay);

        int databaseSizeBeforeUpdate = didntPayRepository.findAll().size();

        // Update the didntPay using partial update
        DidntPay partialUpdatedDidntPay = new DidntPay();
        partialUpdatedDidntPay.setId(didntPay.getId());

        partialUpdatedDidntPay.paid(UPDATED_PAID);

        restDidntPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDidntPay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDidntPay))
            )
            .andExpect(status().isOk());

        // Validate the DidntPay in the database
        List<DidntPay> didntPayList = didntPayRepository.findAll();
        assertThat(didntPayList).hasSize(databaseSizeBeforeUpdate);
        DidntPay testDidntPay = didntPayList.get(didntPayList.size() - 1);
        assertThat(testDidntPay.getCountPerson()).isEqualTo(DEFAULT_COUNT_PERSON);
        assertThat(testDidntPay.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testDidntPay.getExplanation()).isEqualTo(DEFAULT_EXPLANATION);
    }

    @Test
    void fullUpdateDidntPayWithPatch() throws Exception {
        // Initialize the database
        didntPayRepository.save(didntPay);

        int databaseSizeBeforeUpdate = didntPayRepository.findAll().size();

        // Update the didntPay using partial update
        DidntPay partialUpdatedDidntPay = new DidntPay();
        partialUpdatedDidntPay.setId(didntPay.getId());

        partialUpdatedDidntPay.countPerson(UPDATED_COUNT_PERSON).paid(UPDATED_PAID).explanation(UPDATED_EXPLANATION);

        restDidntPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDidntPay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDidntPay))
            )
            .andExpect(status().isOk());

        // Validate the DidntPay in the database
        List<DidntPay> didntPayList = didntPayRepository.findAll();
        assertThat(didntPayList).hasSize(databaseSizeBeforeUpdate);
        DidntPay testDidntPay = didntPayList.get(didntPayList.size() - 1);
        assertThat(testDidntPay.getCountPerson()).isEqualTo(UPDATED_COUNT_PERSON);
        assertThat(testDidntPay.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testDidntPay.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
    }

    @Test
    void patchNonExistingDidntPay() throws Exception {
        int databaseSizeBeforeUpdate = didntPayRepository.findAll().size();
        didntPay.setId(UUID.randomUUID().toString());

        // Create the DidntPay
        DidntPayDTO didntPayDTO = didntPayMapper.toDto(didntPay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDidntPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, didntPayDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(didntPayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DidntPay in the database
        List<DidntPay> didntPayList = didntPayRepository.findAll();
        assertThat(didntPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchDidntPay() throws Exception {
        int databaseSizeBeforeUpdate = didntPayRepository.findAll().size();
        didntPay.setId(UUID.randomUUID().toString());

        // Create the DidntPay
        DidntPayDTO didntPayDTO = didntPayMapper.toDto(didntPay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDidntPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(didntPayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DidntPay in the database
        List<DidntPay> didntPayList = didntPayRepository.findAll();
        assertThat(didntPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamDidntPay() throws Exception {
        int databaseSizeBeforeUpdate = didntPayRepository.findAll().size();
        didntPay.setId(UUID.randomUUID().toString());

        // Create the DidntPay
        DidntPayDTO didntPayDTO = didntPayMapper.toDto(didntPay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDidntPayMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(didntPayDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DidntPay in the database
        List<DidntPay> didntPayList = didntPayRepository.findAll();
        assertThat(didntPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteDidntPay() throws Exception {
        // Initialize the database
        didntPayRepository.save(didntPay);

        int databaseSizeBeforeDelete = didntPayRepository.findAll().size();

        // Delete the didntPay
        restDidntPayMockMvc
            .perform(delete(ENTITY_API_URL_ID, didntPay.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DidntPay> didntPayList = didntPayRepository.findAll();
        assertThat(didntPayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
