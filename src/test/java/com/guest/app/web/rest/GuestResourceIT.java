package com.guest.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.guest.app.IntegrationTest;
import com.guest.app.domain.Entrance;
import com.guest.app.domain.Floor;
import com.guest.app.domain.Guest;
import com.guest.app.domain.GuestBlock;
import com.guest.app.domain.GuestFrom;
import com.guest.app.domain.GuestHouse;
import com.guest.app.repository.GuestRepository;
import com.guest.app.service.dto.GuestDTO;
import com.guest.app.service.mapper.GuestMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link GuestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GuestResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GUEST_INSTITUTION = "AAAAAAAAAA";
    private static final String UPDATED_GUEST_INSTITUTION = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSIBLE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSIBLE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ARCHIVE = false;
    private static final Boolean UPDATED_IS_ARCHIVE = true;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_COUNT_DIDNT_PAY = 1;
    private static final Integer UPDATED_COUNT_DIDNT_PAY = 2;

    private static final Boolean DEFAULT_PAID = false;
    private static final Boolean UPDATED_PAID = true;

    private static final Integer DEFAULT_COUNT_PERSON = 1;
    private static final Integer UPDATED_COUNT_PERSON = 2;

    private static final String DEFAULT_EXPLANATION = "AAAAAAAAAA";
    private static final String UPDATED_EXPLANATION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final Integer DEFAULT_TOTAL_PRICE = 1;
    private static final Integer UPDATED_TOTAL_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/guests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private GuestMapper guestMapper;

    @Autowired
    private MockMvc restGuestMockMvc;

    private Guest guest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guest createEntity() {
        Guest guest = new Guest()
            .name(DEFAULT_NAME)
            .guestInstitution(DEFAULT_GUEST_INSTITUTION)
            .responsible(DEFAULT_RESPONSIBLE)
            .isArchive(DEFAULT_IS_ARCHIVE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .countDidntPay(DEFAULT_COUNT_DIDNT_PAY)
            .paid(DEFAULT_PAID)
            .countPerson(DEFAULT_COUNT_PERSON)
            .explanation(DEFAULT_EXPLANATION)
            .price(DEFAULT_PRICE)
            .totalPrice(DEFAULT_TOTAL_PRICE);
        // Add required entity
        GuestBlock guestBlock;
        guestBlock = GuestBlockResourceIT.createEntity();
        guestBlock.setId("fixed-id-for-tests");
        guest.setGuestBlock(guestBlock);
        // Add required entity
        Entrance entrance;
        entrance = EntranceResourceIT.createEntity();
        entrance.setId("fixed-id-for-tests");
        guest.setEntrance(entrance);
        // Add required entity
        Floor floor;
        floor = FloorResourceIT.createEntity();
        floor.setId("fixed-id-for-tests");
        guest.setFloor(floor);
        // Add required entity
        GuestHouse guestHouse;
        guestHouse = GuestHouseResourceIT.createEntity();
        guestHouse.setId("fixed-id-for-tests");
        guest.setGuestHouse(guestHouse);
        // Add required entity
        GuestFrom guestFrom;
        guestFrom = GuestFromResourceIT.createEntity();
        guestFrom.setId("fixed-id-for-tests");
        guest.setGuestFrom(guestFrom);
        return guest;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Guest createUpdatedEntity() {
        Guest guest = new Guest()
            .name(UPDATED_NAME)
            .guestInstitution(UPDATED_GUEST_INSTITUTION)
            .responsible(UPDATED_RESPONSIBLE)
            .isArchive(UPDATED_IS_ARCHIVE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .countDidntPay(UPDATED_COUNT_DIDNT_PAY)
            .paid(UPDATED_PAID)
            .countPerson(UPDATED_COUNT_PERSON)
            .explanation(UPDATED_EXPLANATION)
            .price(UPDATED_PRICE)
            .totalPrice(UPDATED_TOTAL_PRICE);
        // Add required entity
        GuestBlock guestBlock;
        guestBlock = GuestBlockResourceIT.createUpdatedEntity();
        guestBlock.setId("fixed-id-for-tests");
        guest.setGuestBlock(guestBlock);
        // Add required entity
        Entrance entrance;
        entrance = EntranceResourceIT.createUpdatedEntity();
        entrance.setId("fixed-id-for-tests");
        guest.setEntrance(entrance);
        // Add required entity
        Floor floor;
        floor = FloorResourceIT.createUpdatedEntity();
        floor.setId("fixed-id-for-tests");
        guest.setFloor(floor);
        // Add required entity
        GuestHouse guestHouse;
        guestHouse = GuestHouseResourceIT.createUpdatedEntity();
        guestHouse.setId("fixed-id-for-tests");
        guest.setGuestHouse(guestHouse);
        // Add required entity
        GuestFrom guestFrom;
        guestFrom = GuestFromResourceIT.createUpdatedEntity();
        guestFrom.setId("fixed-id-for-tests");
        guest.setGuestFrom(guestFrom);
        return guest;
    }

    @BeforeEach
    public void initTest() {
        guestRepository.deleteAll();
        guest = createEntity();
    }

    @Test
    void createGuest() throws Exception {
        int databaseSizeBeforeCreate = guestRepository.findAll().size();
        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);
        restGuestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isCreated());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeCreate + 1);
        Guest testGuest = guestList.get(guestList.size() - 1);
        assertThat(testGuest.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGuest.getGuestInstitution()).isEqualTo(DEFAULT_GUEST_INSTITUTION);
        assertThat(testGuest.getResponsible()).isEqualTo(DEFAULT_RESPONSIBLE);
        assertThat(testGuest.getIsArchive()).isEqualTo(DEFAULT_IS_ARCHIVE);
        assertThat(testGuest.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testGuest.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testGuest.getCountDidntPay()).isEqualTo(DEFAULT_COUNT_DIDNT_PAY);
        assertThat(testGuest.getPaid()).isEqualTo(DEFAULT_PAID);
        assertThat(testGuest.getCountPerson()).isEqualTo(DEFAULT_COUNT_PERSON);
        assertThat(testGuest.getExplanation()).isEqualTo(DEFAULT_EXPLANATION);
        assertThat(testGuest.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testGuest.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
    }

    @Test
    void createGuestWithExistingId() throws Exception {
        // Create the Guest with an existing ID
        guest.setId("existing_id");
        GuestDTO guestDTO = guestMapper.toDto(guest);

        int databaseSizeBeforeCreate = guestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGuestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = guestRepository.findAll().size();
        // set the field null
        guest.setName(null);

        // Create the Guest, which fails.
        GuestDTO guestDTO = guestMapper.toDto(guest);

        restGuestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isBadRequest());

        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = guestRepository.findAll().size();
        // set the field null
        guest.setStartDate(null);

        // Create the Guest, which fails.
        GuestDTO guestDTO = guestMapper.toDto(guest);

        restGuestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isBadRequest());

        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = guestRepository.findAll().size();
        // set the field null
        guest.setEndDate(null);

        // Create the Guest, which fails.
        GuestDTO guestDTO = guestMapper.toDto(guest);

        restGuestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isBadRequest());

        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkCountPersonIsRequired() throws Exception {
        int databaseSizeBeforeTest = guestRepository.findAll().size();
        // set the field null
        guest.setCountPerson(null);

        // Create the Guest, which fails.
        GuestDTO guestDTO = guestMapper.toDto(guest);

        restGuestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isBadRequest());

        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = guestRepository.findAll().size();
        // set the field null
        guest.setPrice(null);

        // Create the Guest, which fails.
        GuestDTO guestDTO = guestMapper.toDto(guest);

        restGuestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isBadRequest());

        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllGuests() throws Exception {
        // Initialize the database
        guestRepository.save(guest);

        // Get all the guestList
        restGuestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(guest.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].guestInstitution").value(hasItem(DEFAULT_GUEST_INSTITUTION)))
            .andExpect(jsonPath("$.[*].responsible").value(hasItem(DEFAULT_RESPONSIBLE)))
            .andExpect(jsonPath("$.[*].isArchive").value(hasItem(DEFAULT_IS_ARCHIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].countDidntPay").value(hasItem(DEFAULT_COUNT_DIDNT_PAY)))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].countPerson").value(hasItem(DEFAULT_COUNT_PERSON)))
            .andExpect(jsonPath("$.[*].explanation").value(hasItem(DEFAULT_EXPLANATION)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE)));
    }

    @Test
    void getGuest() throws Exception {
        // Initialize the database
        guestRepository.save(guest);

        // Get the guest
        restGuestMockMvc
            .perform(get(ENTITY_API_URL_ID, guest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(guest.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.guestInstitution").value(DEFAULT_GUEST_INSTITUTION))
            .andExpect(jsonPath("$.responsible").value(DEFAULT_RESPONSIBLE))
            .andExpect(jsonPath("$.isArchive").value(DEFAULT_IS_ARCHIVE.booleanValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.countDidntPay").value(DEFAULT_COUNT_DIDNT_PAY))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.booleanValue()))
            .andExpect(jsonPath("$.countPerson").value(DEFAULT_COUNT_PERSON))
            .andExpect(jsonPath("$.explanation").value(DEFAULT_EXPLANATION))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE));
    }

    @Test
    void getNonExistingGuest() throws Exception {
        // Get the guest
        restGuestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingGuest() throws Exception {
        // Initialize the database
        guestRepository.save(guest);

        int databaseSizeBeforeUpdate = guestRepository.findAll().size();

        // Update the guest
        Guest updatedGuest = guestRepository.findById(guest.getId()).orElseThrow();
        updatedGuest
            .name(UPDATED_NAME)
            .guestInstitution(UPDATED_GUEST_INSTITUTION)
            .responsible(UPDATED_RESPONSIBLE)
            .isArchive(UPDATED_IS_ARCHIVE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .countDidntPay(UPDATED_COUNT_DIDNT_PAY)
            .paid(UPDATED_PAID)
            .countPerson(UPDATED_COUNT_PERSON)
            .explanation(UPDATED_EXPLANATION)
            .price(UPDATED_PRICE)
            .totalPrice(UPDATED_TOTAL_PRICE);
        GuestDTO guestDTO = guestMapper.toDto(updatedGuest);

        restGuestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestDTO))
            )
            .andExpect(status().isOk());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
        Guest testGuest = guestList.get(guestList.size() - 1);
        assertThat(testGuest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuest.getGuestInstitution()).isEqualTo(UPDATED_GUEST_INSTITUTION);
        assertThat(testGuest.getResponsible()).isEqualTo(UPDATED_RESPONSIBLE);
        assertThat(testGuest.getIsArchive()).isEqualTo(UPDATED_IS_ARCHIVE);
        assertThat(testGuest.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testGuest.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testGuest.getCountDidntPay()).isEqualTo(UPDATED_COUNT_DIDNT_PAY);
        assertThat(testGuest.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testGuest.getCountPerson()).isEqualTo(UPDATED_COUNT_PERSON);
        assertThat(testGuest.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testGuest.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testGuest.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    void putNonExistingGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();
        guest.setId(UUID.randomUUID().toString());

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, guestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();
        guest.setId(UUID.randomUUID().toString());

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(guestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();
        guest.setId(UUID.randomUUID().toString());

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateGuestWithPatch() throws Exception {
        // Initialize the database
        guestRepository.save(guest);

        int databaseSizeBeforeUpdate = guestRepository.findAll().size();

        // Update the guest using partial update
        Guest partialUpdatedGuest = new Guest();
        partialUpdatedGuest.setId(guest.getId());

        partialUpdatedGuest
            .name(UPDATED_NAME)
            .guestInstitution(UPDATED_GUEST_INSTITUTION)
            .responsible(UPDATED_RESPONSIBLE)
            .paid(UPDATED_PAID)
            .countPerson(UPDATED_COUNT_PERSON)
            .explanation(UPDATED_EXPLANATION)
            .price(UPDATED_PRICE);

        restGuestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuest))
            )
            .andExpect(status().isOk());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
        Guest testGuest = guestList.get(guestList.size() - 1);
        assertThat(testGuest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuest.getGuestInstitution()).isEqualTo(UPDATED_GUEST_INSTITUTION);
        assertThat(testGuest.getResponsible()).isEqualTo(UPDATED_RESPONSIBLE);
        assertThat(testGuest.getIsArchive()).isEqualTo(DEFAULT_IS_ARCHIVE);
        assertThat(testGuest.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testGuest.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testGuest.getCountDidntPay()).isEqualTo(DEFAULT_COUNT_DIDNT_PAY);
        assertThat(testGuest.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testGuest.getCountPerson()).isEqualTo(UPDATED_COUNT_PERSON);
        assertThat(testGuest.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testGuest.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testGuest.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
    }

    @Test
    void fullUpdateGuestWithPatch() throws Exception {
        // Initialize the database
        guestRepository.save(guest);

        int databaseSizeBeforeUpdate = guestRepository.findAll().size();

        // Update the guest using partial update
        Guest partialUpdatedGuest = new Guest();
        partialUpdatedGuest.setId(guest.getId());

        partialUpdatedGuest
            .name(UPDATED_NAME)
            .guestInstitution(UPDATED_GUEST_INSTITUTION)
            .responsible(UPDATED_RESPONSIBLE)
            .isArchive(UPDATED_IS_ARCHIVE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .countDidntPay(UPDATED_COUNT_DIDNT_PAY)
            .paid(UPDATED_PAID)
            .countPerson(UPDATED_COUNT_PERSON)
            .explanation(UPDATED_EXPLANATION)
            .price(UPDATED_PRICE)
            .totalPrice(UPDATED_TOTAL_PRICE);

        restGuestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGuest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGuest))
            )
            .andExpect(status().isOk());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
        Guest testGuest = guestList.get(guestList.size() - 1);
        assertThat(testGuest.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGuest.getGuestInstitution()).isEqualTo(UPDATED_GUEST_INSTITUTION);
        assertThat(testGuest.getResponsible()).isEqualTo(UPDATED_RESPONSIBLE);
        assertThat(testGuest.getIsArchive()).isEqualTo(UPDATED_IS_ARCHIVE);
        assertThat(testGuest.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testGuest.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testGuest.getCountDidntPay()).isEqualTo(UPDATED_COUNT_DIDNT_PAY);
        assertThat(testGuest.getPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testGuest.getCountPerson()).isEqualTo(UPDATED_COUNT_PERSON);
        assertThat(testGuest.getExplanation()).isEqualTo(UPDATED_EXPLANATION);
        assertThat(testGuest.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testGuest.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    void patchNonExistingGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();
        guest.setId(UUID.randomUUID().toString());

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGuestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, guestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();
        guest.setId(UUID.randomUUID().toString());

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(guestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamGuest() throws Exception {
        int databaseSizeBeforeUpdate = guestRepository.findAll().size();
        guest.setId(UUID.randomUUID().toString());

        // Create the Guest
        GuestDTO guestDTO = guestMapper.toDto(guest);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGuestMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(guestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Guest in the database
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteGuest() throws Exception {
        // Initialize the database
        guestRepository.save(guest);

        int databaseSizeBeforeDelete = guestRepository.findAll().size();

        // Delete the guest
        restGuestMockMvc
            .perform(delete(ENTITY_API_URL_ID, guest.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Guest> guestList = guestRepository.findAll();
        assertThat(guestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
