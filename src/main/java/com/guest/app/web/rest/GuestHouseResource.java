package com.guest.app.web.rest;

import com.guest.app.repository.GuestHouseRepository;
import com.guest.app.service.GuestHouseService;
import com.guest.app.service.dto.GuestHouseDTO;
import com.guest.app.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.guest.app.domain.GuestHouse}.
 */
@RestController
@RequestMapping("/api/guest-houses")
public class GuestHouseResource {

    private final Logger log = LoggerFactory.getLogger(GuestHouseResource.class);

    private static final String ENTITY_NAME = "guestHouse";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuestHouseService guestHouseService;

    private final GuestHouseRepository guestHouseRepository;

    public GuestHouseResource(GuestHouseService guestHouseService, GuestHouseRepository guestHouseRepository) {
        this.guestHouseService = guestHouseService;
        this.guestHouseRepository = guestHouseRepository;
    }

    /**
     * {@code POST  /guest-houses} : Create a new guestHouse.
     *
     * @param guestHouseDTO the guestHouseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guestHouseDTO, or with status {@code 400 (Bad Request)} if the guestHouse has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GuestHouseDTO> createGuestHouse(@Valid @RequestBody GuestHouseDTO guestHouseDTO) throws URISyntaxException {
        log.debug("REST request to save GuestHouse : {}", guestHouseDTO);
        if (guestHouseDTO.getId() != null) {
            throw new BadRequestAlertException("A new guestHouse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuestHouseDTO result = guestHouseService.save(guestHouseDTO);
        return ResponseEntity
            .created(new URI("/api/guest-houses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /guest-houses/:id} : Updates an existing guestHouse.
     *
     * @param id the id of the guestHouseDTO to save.
     * @param guestHouseDTO the guestHouseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guestHouseDTO,
     * or with status {@code 400 (Bad Request)} if the guestHouseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guestHouseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GuestHouseDTO> updateGuestHouse(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody GuestHouseDTO guestHouseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GuestHouse : {}, {}", id, guestHouseDTO);
        if (guestHouseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestHouseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestHouseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GuestHouseDTO result = guestHouseService.update(guestHouseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestHouseDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /guest-houses/:id} : Partial updates given fields of an existing guestHouse, field will ignore if it is null
     *
     * @param id the id of the guestHouseDTO to save.
     * @param guestHouseDTO the guestHouseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guestHouseDTO,
     * or with status {@code 400 (Bad Request)} if the guestHouseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the guestHouseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the guestHouseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GuestHouseDTO> partialUpdateGuestHouse(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody GuestHouseDTO guestHouseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GuestHouse partially : {}, {}", id, guestHouseDTO);
        if (guestHouseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestHouseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestHouseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GuestHouseDTO> result = guestHouseService.partialUpdate(guestHouseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestHouseDTO.getId())
        );
    }

    /**
     * {@code GET  /guest-houses} : get all the guestHouses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guestHouses in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GuestHouseDTO>> getAllGuestHouses(
        @RequestParam(required = false) String floorId,
        @RequestParam(required = false) String guestBlockId
    ) {
        log.debug("REST request to get a page of GuestHouses");
        Page<GuestHouseDTO> page = guestHouseService.findAll(floorId, guestBlockId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /guest-houses/:id} : get the "id" guestHouse.
     *
     * @param id the id of the guestHouseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guestHouseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GuestHouseDTO> getGuestHouse(@PathVariable String id) {
        log.debug("REST request to get GuestHouse : {}", id);
        Optional<GuestHouseDTO> guestHouseDTO = guestHouseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guestHouseDTO);
    }

    /**
     * {@code DELETE  /guest-houses/:id} : delete the "id" guestHouse.
     *
     * @param id the id of the guestHouseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuestHouse(@PathVariable String id) {
        log.debug("REST request to delete GuestHouse : {}", id);
        guestHouseService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
