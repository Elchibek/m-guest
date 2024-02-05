package com.guest.app.web.rest;

import com.guest.app.repository.GuestBlockRepository;
import com.guest.app.security.AuthoritiesConstants;
import com.guest.app.service.GuestBlockService;
import com.guest.app.service.dto.GuestBlockDTO;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.guest.app.domain.GuestBlock}.
 */
@RestController
@RequestMapping("/api/guest-blocks")
public class GuestBlockResource {

    private final Logger log = LoggerFactory.getLogger(GuestBlockResource.class);

    private static final String ENTITY_NAME = "guestBlock";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuestBlockService guestBlockService;

    private final GuestBlockRepository guestBlockRepository;

    public GuestBlockResource(GuestBlockService guestBlockService, GuestBlockRepository guestBlockRepository) {
        this.guestBlockService = guestBlockService;
        this.guestBlockRepository = guestBlockRepository;
    }

    /**
     * {@code POST  /guest-blocks} : Create a new guestBlock.
     *
     * @param guestBlockDTO the guestBlockDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new guestBlockDTO, or with status {@code 400 (Bad Request)} if the guestBlock has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<GuestBlockDTO> createGuestBlock(@Valid @RequestBody GuestBlockDTO guestBlockDTO) throws URISyntaxException {
        log.debug("REST request to save GuestBlock : {}", guestBlockDTO);
        if (guestBlockDTO.getId() != null) {
            throw new BadRequestAlertException("A new guestBlock cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuestBlockDTO result = guestBlockService.save(guestBlockDTO);
        return ResponseEntity
            .created(new URI("/api/guest-blocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /guest-blocks/:id} : Updates an existing guestBlock.
     *
     * @param id the id of the guestBlockDTO to save.
     * @param guestBlockDTO the guestBlockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guestBlockDTO,
     * or with status {@code 400 (Bad Request)} if the guestBlockDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the guestBlockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<GuestBlockDTO> updateGuestBlock(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody GuestBlockDTO guestBlockDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GuestBlock : {}, {}", id, guestBlockDTO);
        if (guestBlockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestBlockDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestBlockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GuestBlockDTO result = guestBlockService.update(guestBlockDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestBlockDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /guest-blocks/:id} : Partial updates given fields of an existing guestBlock, field will ignore if it is null
     *
     * @param id the id of the guestBlockDTO to save.
     * @param guestBlockDTO the guestBlockDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated guestBlockDTO,
     * or with status {@code 400 (Bad Request)} if the guestBlockDTO is not valid,
     * or with status {@code 404 (Not Found)} if the guestBlockDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the guestBlockDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<GuestBlockDTO> partialUpdateGuestBlock(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody GuestBlockDTO guestBlockDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GuestBlock partially : {}, {}", id, guestBlockDTO);
        if (guestBlockDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestBlockDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestBlockRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GuestBlockDTO> result = guestBlockService.partialUpdate(guestBlockDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestBlockDTO.getId())
        );
    }

    /**
     * {@code GET  /guest-blocks} : get all the guestBlocks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guestBlocks in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GuestBlockDTO>> getAllGuestBlocks(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of GuestBlocks");
        Page<GuestBlockDTO> page = guestBlockService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /guest-blocks/:id} : get the "id" guestBlock.
     *
     * @param id the id of the guestBlockDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guestBlockDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<GuestBlockDTO> getGuestBlock(@PathVariable String id) {
        log.debug("REST request to get GuestBlock : {}", id);
        Optional<GuestBlockDTO> guestBlockDTO = guestBlockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guestBlockDTO);
    }

    /**
     * {@code DELETE  /guest-blocks/:id} : delete the "id" guestBlock.
     *
     * @param id the id of the guestBlockDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<Void> deleteGuestBlock(@PathVariable String id) {
        log.debug("REST request to delete GuestBlock : {}", id);
        guestBlockService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
