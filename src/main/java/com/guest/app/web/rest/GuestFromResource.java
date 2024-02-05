package com.guest.app.web.rest;

import com.guest.app.repository.GuestFromRepository;
import com.guest.app.service.GuestFromService;
import com.guest.app.service.dto.GuestFromDTO;
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
 * REST controller for managing {@link com.guest.app.domain.GuestFrom}.
 */
@RestController
@RequestMapping("/api/guest-froms")
public class GuestFromResource {

    private final Logger log = LoggerFactory.getLogger(GuestFromResource.class);

    private static final String ENTITY_NAME = "guestFrom";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuestFromService guestFromService;

    private final GuestFromRepository guestFromRepository;

    public GuestFromResource(GuestFromService guestFromService, GuestFromRepository guestFromRepository) {
        this.guestFromService = guestFromService;
        this.guestFromRepository = guestFromRepository;
    }

    /**
     * {@code POST  /guest-froms} : Create a new guestFrom.
     *
     * @param guestFromDTO the guestFromDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guestFromDTO, or with status {@code 400 (Bad Request)} if the guestFrom has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GuestFromDTO> createGuestFrom(@Valid @RequestBody GuestFromDTO guestFromDTO) throws URISyntaxException {
        log.debug("REST request to save GuestFrom : {}", guestFromDTO);
        if (guestFromDTO.getId() != null) {
            throw new BadRequestAlertException("A new guestFrom cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuestFromDTO result = guestFromService.save(guestFromDTO);
        return ResponseEntity
            .created(new URI("/api/guest-froms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /guest-froms/:id} : Updates an existing guestFrom.
     *
     * @param id the id of the guestFromDTO to save.
     * @param guestFromDTO the guestFromDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guestFromDTO,
     * or with status {@code 400 (Bad Request)} if the guestFromDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guestFromDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GuestFromDTO> updateGuestFrom(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody GuestFromDTO guestFromDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GuestFrom : {}, {}", id, guestFromDTO);
        if (guestFromDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestFromDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestFromRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GuestFromDTO result = guestFromService.update(guestFromDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestFromDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /guest-froms/:id} : Partial updates given fields of an existing guestFrom, field will ignore if it is null
     *
     * @param id the id of the guestFromDTO to save.
     * @param guestFromDTO the guestFromDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guestFromDTO,
     * or with status {@code 400 (Bad Request)} if the guestFromDTO is not valid,
     * or with status {@code 404 (Not Found)} if the guestFromDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the guestFromDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GuestFromDTO> partialUpdateGuestFrom(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody GuestFromDTO guestFromDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GuestFrom partially : {}, {}", id, guestFromDTO);
        if (guestFromDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestFromDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestFromRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GuestFromDTO> result = guestFromService.partialUpdate(guestFromDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestFromDTO.getId())
        );
    }

    /**
     * {@code GET  /guest-froms} : get all the guestFroms.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guestFroms in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GuestFromDTO>> getAllGuestFroms(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of GuestFroms");
        Page<GuestFromDTO> page = guestFromService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /guest-froms/:id} : get the "id" guestFrom.
     *
     * @param id the id of the guestFromDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guestFromDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GuestFromDTO> getGuestFrom(@PathVariable String id) {
        log.debug("REST request to get GuestFrom : {}", id);
        Optional<GuestFromDTO> guestFromDTO = guestFromService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guestFromDTO);
    }

    /**
     * {@code DELETE  /guest-froms/:id} : delete the "id" guestFrom.
     *
     * @param id the id of the guestFromDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuestFrom(@PathVariable String id) {
        log.debug("REST request to delete GuestFrom : {}", id);
        guestFromService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
