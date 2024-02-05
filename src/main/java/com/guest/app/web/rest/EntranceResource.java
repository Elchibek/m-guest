package com.guest.app.web.rest;

import com.guest.app.repository.EntranceRepository;
import com.guest.app.service.EntranceService;
import com.guest.app.service.dto.EntranceDTO;
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
 * REST controller for managing {@link com.guest.app.domain.Entrance}.
 */
@RestController
@RequestMapping("/api/entrances")
public class EntranceResource {

    private final Logger log = LoggerFactory.getLogger(EntranceResource.class);

    private static final String ENTITY_NAME = "entrance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntranceService entranceService;

    private final EntranceRepository entranceRepository;

    public EntranceResource(EntranceService entranceService, EntranceRepository entranceRepository) {
        this.entranceService = entranceService;
        this.entranceRepository = entranceRepository;
    }

    /**
     * {@code POST  /entrances} : Create a new entrance.
     *
     * @param entranceDTO the entranceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entranceDTO, or with status {@code 400 (Bad Request)} if the entrance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<EntranceDTO> createEntrance(@Valid @RequestBody EntranceDTO entranceDTO) throws URISyntaxException {
        log.debug("REST request to save Entrance : {}", entranceDTO);
        if (entranceDTO.getId() != null) {
            throw new BadRequestAlertException("A new entrance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntranceDTO result = entranceService.save(entranceDTO);
        return ResponseEntity
            .created(new URI("/api/entrances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /entrances/:id} : Updates an existing entrance.
     *
     * @param id the id of the entranceDTO to save.
     * @param entranceDTO the entranceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entranceDTO,
     * or with status {@code 400 (Bad Request)} if the entranceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entranceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<EntranceDTO> updateEntrance(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody EntranceDTO entranceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Entrance : {}, {}", id, entranceDTO);
        if (entranceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entranceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entranceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntranceDTO result = entranceService.update(entranceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entranceDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /entrances/:id} : Partial updates given fields of an existing entrance, field will ignore if it is null
     *
     * @param id the id of the entranceDTO to save.
     * @param entranceDTO the entranceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entranceDTO,
     * or with status {@code 400 (Bad Request)} if the entranceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entranceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entranceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntranceDTO> partialUpdateEntrance(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody EntranceDTO entranceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entrance partially : {}, {}", id, entranceDTO);
        if (entranceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entranceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entranceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntranceDTO> result = entranceService.partialUpdate(entranceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entranceDTO.getId())
        );
    }

    /**
     * {@code GET  /entrances} : get all the entrances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entrances in body.
     */
    @GetMapping("")
    public ResponseEntity<List<EntranceDTO>> getAllEntrances(
        @RequestParam(required = false) String guestBlockId,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Entrances");
        Page<EntranceDTO> page = entranceService.findAll(guestBlockId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entrances/:id} : get the "id" entrance.
     *
     * @param id the id of the entranceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entranceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<EntranceDTO> getEntrance(@PathVariable String id) {
        log.debug("REST request to get Entrance : {}", id);
        Optional<EntranceDTO> entranceDTO = entranceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entranceDTO);
    }

    /**
     * {@code DELETE  /entrances/:id} : delete the "id" entrance.
     *
     * @param id the id of the entranceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntrance(@PathVariable String id) {
        log.debug("REST request to delete Entrance : {}", id);
        entranceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
