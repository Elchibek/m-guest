package com.guest.app.web.rest;

import com.guest.app.repository.GuestContactRepository;
import com.guest.app.service.GuestContactService;
import com.guest.app.service.dto.GuestContactDTO;
import com.guest.app.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.guest.app.domain.GuestContact}.
 */
@RestController
@RequestMapping("/api/guest-contacts")
public class GuestContactResource {

    private final Logger log = LoggerFactory.getLogger(GuestContactResource.class);

    private static final String ENTITY_NAME = "guestContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuestContactService guestContactService;

    private final GuestContactRepository guestContactRepository;

    public GuestContactResource(GuestContactService guestContactService, GuestContactRepository guestContactRepository) {
        this.guestContactService = guestContactService;
        this.guestContactRepository = guestContactRepository;
    }

    /**
     * {@code POST  /guest-contacts} : Create a new guestContact.
     *
     * @param guestContactDTO the guestContactDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guestContactDTO, or with status {@code 400 (Bad Request)} if the guestContact has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<GuestContactDTO> createGuestContact(@RequestBody GuestContactDTO guestContactDTO) throws URISyntaxException {
        log.debug("REST request to save GuestContact : {}", guestContactDTO);
        if (guestContactDTO.getId() != null) {
            throw new BadRequestAlertException("A new guestContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuestContactDTO result = guestContactService.save(guestContactDTO);
        return ResponseEntity
            .created(new URI("/api/guest-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /guest-contacts/:id} : Updates an existing guestContact.
     *
     * @param id the id of the guestContactDTO to save.
     * @param guestContactDTO the guestContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guestContactDTO,
     * or with status {@code 400 (Bad Request)} if the guestContactDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guestContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<GuestContactDTO> updateGuestContact(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody GuestContactDTO guestContactDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GuestContact : {}, {}", id, guestContactDTO);
        if (guestContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestContactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GuestContactDTO result = guestContactService.update(guestContactDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestContactDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /guest-contacts/:id} : Partial updates given fields of an existing guestContact, field will ignore if it is null
     *
     * @param id the id of the guestContactDTO to save.
     * @param guestContactDTO the guestContactDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guestContactDTO,
     * or with status {@code 400 (Bad Request)} if the guestContactDTO is not valid,
     * or with status {@code 404 (Not Found)} if the guestContactDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the guestContactDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GuestContactDTO> partialUpdateGuestContact(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody GuestContactDTO guestContactDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GuestContact partially : {}, {}", id, guestContactDTO);
        if (guestContactDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestContactDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestContactRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GuestContactDTO> result = guestContactService.partialUpdate(guestContactDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestContactDTO.getId())
        );
    }

    /**
     * {@code GET  /guest-contacts} : get all the guestContacts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guestContacts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GuestContactDTO>> getAllGuestContacts(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of GuestContacts");
        Page<GuestContactDTO> page = guestContactService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /guest-contacts/:id} : get the "id" guestContact.
     *
     * @param id the id of the guestContactDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guestContactDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GuestContactDTO> getGuestContact(@PathVariable String id) {
        log.debug("REST request to get GuestContact : {}", id);
        Optional<GuestContactDTO> guestContactDTO = guestContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guestContactDTO);
    }

    /**
     * {@code DELETE  /guest-contacts/:id} : delete the "id" guestContact.
     *
     * @param id the id of the guestContactDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuestContact(@PathVariable String id) {
        log.debug("REST request to delete GuestContact : {}", id);
        guestContactService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
