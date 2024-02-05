package com.guest.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.guest.app.IntegrationTest;
import com.guest.app.domain.GuestStatic;
import com.guest.app.repository.GuestStaticRepository;
import com.guest.app.service.dto.GuestStaticDTO;
import com.guest.app.service.mapper.GuestStaticMapper;
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
 * Integration tests for the {@link GuestStaticResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GuestStaticResourceIT {

    private static final Boolean DEFAULT_IS_ARCHIVE = false;
    private static final Boolean UPDATED_IS_ARCHIVE = true;

    private static final Integer DEFAULT_COUNT_PERSON = 1;
    private static final Integer UPDATED_COUNT_PERSON = 2;

    private static final Integer DEFAULT_TO_DAY_DEPARTURE = 1;
    private static final Integer UPDATED_TO_DAY_DEPARTURE = 2;

    private static final Integer DEFAULT_TO_MORROW_DEPARTURE = 1;
    private static final Integer UPDATED_TO_MORROW_DEPARTURE = 2;

    private static final Integer DEFAULT_TOTAL_PRICE = 1;
    private static final Integer UPDATED_TOTAL_PRICE = 2;

    private static final Integer DEFAULT_TOTAL_DIDNT_PAY = 1;
    private static final Integer UPDATED_TOTAL_DIDNT_PAY = 2;

    private static final Integer DEFAULT_NUM_OF_APARTMENTS = 1;
    private static final Integer UPDATED_NUM_OF_APARTMENTS = 2;

    private static final Integer DEFAULT_AFFORDABLE_APARTMENTS = 1;
    private static final Integer UPDATED_AFFORDABLE_APARTMENTS = 2;

    private static final String ENTITY_API_URL = "/api/guest-statics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GuestStaticRepository guestStaticRepository;

    @Autowired
    private GuestStaticMapper guestStaticMapper;

    @Autowired
    private MockMvc restGuestStaticMockMvc;

    private GuestStatic guestStatic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuestStatic createEntity() {
        GuestStatic guestStatic = new GuestStatic()
            .isArchive(DEFAULT_IS_ARCHIVE)
            .countPerson(DEFAULT_COUNT_PERSON)
            .toDayDeparture(DEFAULT_TO_DAY_DEPARTURE)
            .toMorrowDeparture(DEFAULT_TO_MORROW_DEPARTURE)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .totalDidntPay(DEFAULT_TOTAL_DIDNT_PAY)
            .numOfApartments(DEFAULT_NUM_OF_APARTMENTS)
            .affordableApartments(DEFAULT_AFFORDABLE_APARTMENTS);
        return guestStatic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GuestStatic createUpdatedEntity() {
        GuestStatic guestStatic = new GuestStatic()
            .isArchive(UPDATED_IS_ARCHIVE)
            .countPerson(UPDATED_COUNT_PERSON)
            .toDayDeparture(UPDATED_TO_DAY_DEPARTURE)
            .toMorrowDeparture(UPDATED_TO_MORROW_DEPARTURE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .totalDidntPay(UPDATED_TOTAL_DIDNT_PAY)
            .numOfApartments(UPDATED_NUM_OF_APARTMENTS)
            .affordableApartments(UPDATED_AFFORDABLE_APARTMENTS);
        return guestStatic;
    }

    @BeforeEach
    public void initTest() {
        guestStaticRepository.deleteAll();
        guestStatic = createEntity();
    }

    @Test
    void createGuestStatic() throws Exception {
        int databaseSizeBeforeCreate = guestStaticRepository.findAll().size();
        // Create the GuestStatic
        GuestStaticDTO guestStaticDTO = guestStaticMapper.toDto(guestStatic);
        restGuestStaticMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestStaticDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GuestStatic in the database
        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeCreate + 1);
        GuestStatic testGuestStatic = guestStaticList.get(guestStaticList.size() - 1);
        assertThat(testGuestStatic.getIsArchive()).isEqualTo(DEFAULT_IS_ARCHIVE);
        assertThat(testGuestStatic.getCountPerson()).isEqualTo(DEFAULT_COUNT_PERSON);
        assertThat(testGuestStatic.getToDayDeparture()).isEqualTo(DEFAULT_TO_DAY_DEPARTURE);
        assertThat(testGuestStatic.getToMorrowDeparture()).isEqualTo(DEFAULT_TO_MORROW_DEPARTURE);
        assertThat(testGuestStatic.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testGuestStatic.getTotalDidntPay()).isEqualTo(DEFAULT_TOTAL_DIDNT_PAY);
        assertThat(testGuestStatic.getNumOfApartments()).isEqualTo(DEFAULT_NUM_OF_APARTMENTS);
        assertThat(testGuestStatic.getAffordableApartments()).isEqualTo(DEFAULT_AFFORDABLE_APARTMENTS);
    }

    @Test
    void createGuestStaticWithExistingId() throws Exception {
        // Create the GuestStatic with an existing ID
        guestStatic.setId("existing_id");
        GuestStaticDTO guestStaticDTO = guestStaticMapper.toDto(guestStatic);

        int databaseSizeBeforeCreate = guestStaticRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuestStaticMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestStaticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestStatic in the database
        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkCountPersonIsRequired() throws Exception {
        int databaseSizeBeforeTest = guestStaticRepository.findAll().size();
        // set the field null
        guestStatic.setCountPerson(null);

        // Create the GuestStatic, which fails.
        GuestStaticDTO guestStaticDTO = guestStaticMapper.toDto(guestStatic);

        restGuestStaticMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestStaticDTO))
            )
            .andExpect(status().isBadRequest());

        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllGuestStatics() throws Exception {
        // Initialize the database
        guestStaticRepository.save(guestStatic);

        // Get all the guestStaticList
        restGuestStaticMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guestStatic.getId())))
            .andExpect(jsonPath("$.[*].isArchive").value(hasItem(DEFAULT_IS_ARCHIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].countPerson").value(hasItem(DEFAULT_COUNT_PERSON)))
            .andExpect(jsonPath("$.[*].toDayDeparture").value(hasItem(DEFAULT_TO_DAY_DEPARTURE)))
            .andExpect(jsonPath("$.[*].toMorrowDeparture").value(hasItem(DEFAULT_TO_MORROW_DEPARTURE)))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE)))
            .andExpect(jsonPath("$.[*].totalDidntPay").value(hasItem(DEFAULT_TOTAL_DIDNT_PAY)))
            .andExpect(jsonPath("$.[*].numOfApartments").value(hasItem(DEFAULT_NUM_OF_APARTMENTS)))
            .andExpect(jsonPath("$.[*].affordableApartments").value(hasItem(DEFAULT_AFFORDABLE_APARTMENTS)));
    }

    @Test
    void getGuestStatic() throws Exception {
        // Initialize the database
        guestStaticRepository.save(guestStatic);

        // Get the guestStatic
        restGuestStaticMockMvc
            .perform(get(ENTITY_API_URL_ID, guestStatic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guestStatic.getId()))
            .andExpect(jsonPath("$.isArchive").value(DEFAULT_IS_ARCHIVE.booleanValue()))
            .andExpect(jsonPath("$.countPerson").value(DEFAULT_COUNT_PERSON))
            .andExpect(jsonPath("$.toDayDeparture").value(DEFAULT_TO_DAY_DEPARTURE))
            .andExpect(jsonPath("$.toMorrowDeparture").value(DEFAULT_TO_MORROW_DEPARTURE))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE))
            .andExpect(jsonPath("$.totalDidntPay").value(DEFAULT_TOTAL_DIDNT_PAY))
            .andExpect(jsonPath("$.numOfApartments").value(DEFAULT_NUM_OF_APARTMENTS))
            .andExpect(jsonPath("$.affordableApartments").value(DEFAULT_AFFORDABLE_APARTMENTS));
    }

    @Test
    void getNonExistingGuestStatic() throws Exception {
        // Get the guestStatic
        restGuestStaticMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingGuestStatic() throws Exception {
        // Initialize the database
        guestStaticRepository.save(guestStatic);

        int databaseSizeBeforeUpdate = guestStaticRepository.findAll().size();

        // Update the guestStatic
        GuestStatic updatedGuestStatic = guestStaticRepository.findById(guestStatic.getId()).orElseThrow();
        updatedGuestStatic
            .isArchive(UPDATED_IS_ARCHIVE)
            .countPerson(UPDATED_COUNT_PERSON)
            .toDayDeparture(UPDATED_TO_DAY_DEPARTURE)
            .toMorrowDeparture(UPDATED_TO_MORROW_DEPARTURE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .totalDidntPay(UPDATED_TOTAL_DIDNT_PAY)
            .numOfApartments(UPDATED_NUM_OF_APARTMENTS)
            .affordableApartments(UPDATED_AFFORDABLE_APARTMENTS);
        GuestStaticDTO guestStaticDTO = guestStaticMapper.toDto(updatedGuestStatic);

        restGuestStaticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestStaticDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestStaticDTO))
            )
            .andExpect(status().isOk());

        // Validate the GuestStatic in the database
        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeUpdate);
        GuestStatic testGuestStatic = guestStaticList.get(guestStaticList.size() - 1);
        assertThat(testGuestStatic.getIsArchive()).isEqualTo(UPDATED_IS_ARCHIVE);
        assertThat(testGuestStatic.getCountPerson()).isEqualTo(UPDATED_COUNT_PERSON);
        assertThat(testGuestStatic.getToDayDeparture()).isEqualTo(UPDATED_TO_DAY_DEPARTURE);
        assertThat(testGuestStatic.getToMorrowDeparture()).isEqualTo(UPDATED_TO_MORROW_DEPARTURE);
        assertThat(testGuestStatic.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testGuestStatic.getTotalDidntPay()).isEqualTo(UPDATED_TOTAL_DIDNT_PAY);
        assertThat(testGuestStatic.getNumOfApartments()).isEqualTo(UPDATED_NUM_OF_APARTMENTS);
        assertThat(testGuestStatic.getAffordableApartments()).isEqualTo(UPDATED_AFFORDABLE_APARTMENTS);
    }

    @Test
    void putNonExistingGuestStatic() throws Exception {
        int databaseSizeBeforeUpdate = guestStaticRepository.findAll().size();
        guestStatic.setId(UUID.randomUUID().toString());

        // Create the GuestStatic
        GuestStaticDTO guestStaticDTO = guestStaticMapper.toDto(guestStatic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestStaticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestStaticDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestStaticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestStatic in the database
        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGuestStatic() throws Exception {
        int databaseSizeBeforeUpdate = guestStaticRepository.findAll().size();
        guestStatic.setId(UUID.randomUUID().toString());

        // Create the GuestStatic
        GuestStaticDTO guestStaticDTO = guestStaticMapper.toDto(guestStatic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestStaticMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestStaticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestStatic in the database
        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGuestStatic() throws Exception {
        int databaseSizeBeforeUpdate = guestStaticRepository.findAll().size();
        guestStatic.setId(UUID.randomUUID().toString());

        // Create the GuestStatic
        GuestStaticDTO guestStaticDTO = guestStaticMapper.toDto(guestStatic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestStaticMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestStaticDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuestStatic in the database
        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGuestStaticWithPatch() throws Exception {
        // Initialize the database
        guestStaticRepository.save(guestStatic);

        int databaseSizeBeforeUpdate = guestStaticRepository.findAll().size();

        // Update the guestStatic using partial update
        GuestStatic partialUpdatedGuestStatic = new GuestStatic();
        partialUpdatedGuestStatic.setId(guestStatic.getId());

        partialUpdatedGuestStatic
            .totalDidntPay(UPDATED_TOTAL_DIDNT_PAY)
            .numOfApartments(UPDATED_NUM_OF_APARTMENTS)
            .affordableApartments(UPDATED_AFFORDABLE_APARTMENTS);

        restGuestStaticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuestStatic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuestStatic))
            )
            .andExpect(status().isOk());

        // Validate the GuestStatic in the database
        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeUpdate);
        GuestStatic testGuestStatic = guestStaticList.get(guestStaticList.size() - 1);
        assertThat(testGuestStatic.getIsArchive()).isEqualTo(DEFAULT_IS_ARCHIVE);
        assertThat(testGuestStatic.getCountPerson()).isEqualTo(DEFAULT_COUNT_PERSON);
        assertThat(testGuestStatic.getToDayDeparture()).isEqualTo(DEFAULT_TO_DAY_DEPARTURE);
        assertThat(testGuestStatic.getToMorrowDeparture()).isEqualTo(DEFAULT_TO_MORROW_DEPARTURE);
        assertThat(testGuestStatic.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testGuestStatic.getTotalDidntPay()).isEqualTo(UPDATED_TOTAL_DIDNT_PAY);
        assertThat(testGuestStatic.getNumOfApartments()).isEqualTo(UPDATED_NUM_OF_APARTMENTS);
        assertThat(testGuestStatic.getAffordableApartments()).isEqualTo(UPDATED_AFFORDABLE_APARTMENTS);
    }

    @Test
    void fullUpdateGuestStaticWithPatch() throws Exception {
        // Initialize the database
        guestStaticRepository.save(guestStatic);

        int databaseSizeBeforeUpdate = guestStaticRepository.findAll().size();

        // Update the guestStatic using partial update
        GuestStatic partialUpdatedGuestStatic = new GuestStatic();
        partialUpdatedGuestStatic.setId(guestStatic.getId());

        partialUpdatedGuestStatic
            .isArchive(UPDATED_IS_ARCHIVE)
            .countPerson(UPDATED_COUNT_PERSON)
            .toDayDeparture(UPDATED_TO_DAY_DEPARTURE)
            .toMorrowDeparture(UPDATED_TO_MORROW_DEPARTURE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .totalDidntPay(UPDATED_TOTAL_DIDNT_PAY)
            .numOfApartments(UPDATED_NUM_OF_APARTMENTS)
            .affordableApartments(UPDATED_AFFORDABLE_APARTMENTS);

        restGuestStaticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuestStatic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuestStatic))
            )
            .andExpect(status().isOk());

        // Validate the GuestStatic in the database
        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeUpdate);
        GuestStatic testGuestStatic = guestStaticList.get(guestStaticList.size() - 1);
        assertThat(testGuestStatic.getIsArchive()).isEqualTo(UPDATED_IS_ARCHIVE);
        assertThat(testGuestStatic.getCountPerson()).isEqualTo(UPDATED_COUNT_PERSON);
        assertThat(testGuestStatic.getToDayDeparture()).isEqualTo(UPDATED_TO_DAY_DEPARTURE);
        assertThat(testGuestStatic.getToMorrowDeparture()).isEqualTo(UPDATED_TO_MORROW_DEPARTURE);
        assertThat(testGuestStatic.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testGuestStatic.getTotalDidntPay()).isEqualTo(UPDATED_TOTAL_DIDNT_PAY);
        assertThat(testGuestStatic.getNumOfApartments()).isEqualTo(UPDATED_NUM_OF_APARTMENTS);
        assertThat(testGuestStatic.getAffordableApartments()).isEqualTo(UPDATED_AFFORDABLE_APARTMENTS);
    }

    @Test
    void patchNonExistingGuestStatic() throws Exception {
        int databaseSizeBeforeUpdate = guestStaticRepository.findAll().size();
        guestStatic.setId(UUID.randomUUID().toString());

        // Create the GuestStatic
        GuestStaticDTO guestStaticDTO = guestStaticMapper.toDto(guestStatic);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestStaticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, guestStaticDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestStaticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestStatic in the database
        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGuestStatic() throws Exception {
        int databaseSizeBeforeUpdate = guestStaticRepository.findAll().size();
        guestStatic.setId(UUID.randomUUID().toString());

        // Create the GuestStatic
        GuestStaticDTO guestStaticDTO = guestStaticMapper.toDto(guestStatic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestStaticMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestStaticDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GuestStatic in the database
        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGuestStatic() throws Exception {
        int databaseSizeBeforeUpdate = guestStaticRepository.findAll().size();
        guestStatic.setId(UUID.randomUUID().toString());

        // Create the GuestStatic
        GuestStaticDTO guestStaticDTO = guestStaticMapper.toDto(guestStatic);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestStaticMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(guestStaticDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GuestStatic in the database
        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGuestStatic() throws Exception {
        // Initialize the database
        guestStaticRepository.save(guestStatic);

        int databaseSizeBeforeDelete = guestStaticRepository.findAll().size();

        // Delete the guestStatic
        restGuestStaticMockMvc
            .perform(delete(ENTITY_API_URL_ID, guestStatic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GuestStatic> guestStaticList = guestStaticRepository.findAll();
        assertThat(guestStaticList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
