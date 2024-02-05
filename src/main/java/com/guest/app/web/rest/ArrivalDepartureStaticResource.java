package com.guest.app.web.rest;

import com.guest.app.repository.ArrivalDepartureStaticRepository;
import com.guest.app.service.ArrivalDepartureStaticService;
import com.guest.app.service.dto.ArrivalDepartureStaticDTO;
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
 * REST controller for managing {@link com.guest.app.domain.ArrivalDepartureStatic}.
 */
@RestController
@RequestMapping("/api/arrival-departure-statics")
public class ArrivalDepartureStaticResource {

    private final Logger log = LoggerFactory.getLogger(ArrivalDepartureStaticResource.class);

    private static final String ENTITY_NAME = "arrivalDepartureStatic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArrivalDepartureStaticService arrivalDepartureStaticService;

    private final ArrivalDepartureStaticRepository arrivalDepartureStaticRepository;

    public ArrivalDepartureStaticResource(
        ArrivalDepartureStaticService arrivalDepartureStaticService,
        ArrivalDepartureStaticRepository arrivalDepartureStaticRepository
    ) {
        this.arrivalDepartureStaticService = arrivalDepartureStaticService;
        this.arrivalDepartureStaticRepository = arrivalDepartureStaticRepository;
    }

    /**
     * {@code POST  /arrival-departure-statics} : Create a new arrivalDepartureStatic.
     *
     * @param arrivalDepartureStaticDTO the arrivalDepartureStaticDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arrivalDepartureStaticDTO, or with status {@code 400 (Bad Request)} if the arrivalDepartureStatic has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ArrivalDepartureStaticDTO> createArrivalDepartureStatic(
        @Valid @RequestBody ArrivalDepartureStaticDTO arrivalDepartureStaticDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ArrivalDepartureStatic : {}", arrivalDepartureStaticDTO);
        if (arrivalDepartureStaticDTO.getId() != null) {
            throw new BadRequestAlertException("A new arrivalDepartureStatic cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArrivalDepartureStaticDTO result = arrivalDepartureStaticService.save(arrivalDepartureStaticDTO);
        return ResponseEntity
            .created(new URI("/api/arrival-departure-statics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /arrival-departure-statics/:id} : Updates an existing arrivalDepartureStatic.
     *
     * @param id the id of the arrivalDepartureStaticDTO to save.
     * @param arrivalDepartureStaticDTO the arrivalDepartureStaticDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arrivalDepartureStaticDTO,
     * or with status {@code 400 (Bad Request)} if the arrivalDepartureStaticDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arrivalDepartureStaticDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArrivalDepartureStaticDTO> updateArrivalDepartureStatic(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody ArrivalDepartureStaticDTO arrivalDepartureStaticDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ArrivalDepartureStatic : {}, {}", id, arrivalDepartureStaticDTO);
        if (arrivalDepartureStaticDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arrivalDepartureStaticDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arrivalDepartureStaticRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArrivalDepartureStaticDTO result = arrivalDepartureStaticService.update(arrivalDepartureStaticDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arrivalDepartureStaticDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /arrival-departure-statics/:id} : Partial updates given fields of an existing arrivalDepartureStatic, field will ignore if it is null
     *
     * @param id the id of the arrivalDepartureStaticDTO to save.
     * @param arrivalDepartureStaticDTO the arrivalDepartureStaticDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arrivalDepartureStaticDTO,
     * or with status {@code 400 (Bad Request)} if the arrivalDepartureStaticDTO is not valid,
     * or with status {@code 404 (Not Found)} if the arrivalDepartureStaticDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the arrivalDepartureStaticDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ArrivalDepartureStaticDTO> partialUpdateArrivalDepartureStatic(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody ArrivalDepartureStaticDTO arrivalDepartureStaticDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ArrivalDepartureStatic partially : {}, {}", id, arrivalDepartureStaticDTO);
        if (arrivalDepartureStaticDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, arrivalDepartureStaticDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!arrivalDepartureStaticRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArrivalDepartureStaticDTO> result = arrivalDepartureStaticService.partialUpdate(arrivalDepartureStaticDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arrivalDepartureStaticDTO.getId())
        );
    }

    /**
     * {@code GET  /arrival-departure-statics} : get all the arrivalDepartureStatics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arrivalDepartureStatics in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ArrivalDepartureStaticDTO>> getAllArrivalDepartureStatics(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ArrivalDepartureStatics");
        Page<ArrivalDepartureStaticDTO> page = arrivalDepartureStaticService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arrival-departure-statics/:id} : get the "id" arrivalDepartureStatic.
     *
     * @param id the id of the arrivalDepartureStaticDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arrivalDepartureStaticDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArrivalDepartureStaticDTO> getArrivalDepartureStatic(@PathVariable String id) {
        log.debug("REST request to get ArrivalDepartureStatic : {}", id);
        Optional<ArrivalDepartureStaticDTO> arrivalDepartureStaticDTO = arrivalDepartureStaticService.findOne(id);
        return ResponseUtil.wrapOrNotFound(arrivalDepartureStaticDTO);
    }

    /**
     * {@code DELETE  /arrival-departure-statics/:id} : delete the "id" arrivalDepartureStatic.
     *
     * @param id the id of the arrivalDepartureStaticDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArrivalDepartureStatic(@PathVariable String id) {
        log.debug("REST request to delete ArrivalDepartureStatic : {}", id);
        arrivalDepartureStaticService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
