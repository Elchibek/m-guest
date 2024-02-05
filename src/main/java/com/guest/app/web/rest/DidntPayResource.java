package com.guest.app.web.rest;

import com.guest.app.repository.DidntPayRepository;
import com.guest.app.service.DidntPayService;
import com.guest.app.service.dto.DidntPayDTO;
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
 * REST controller for managing {@link com.guest.app.domain.DidntPay}.
 */
@RestController
@RequestMapping("/api/didnt-pays")
public class DidntPayResource {

    private final Logger log = LoggerFactory.getLogger(DidntPayResource.class);

    private static final String ENTITY_NAME = "didntPay";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DidntPayService didntPayService;

    private final DidntPayRepository didntPayRepository;

    public DidntPayResource(DidntPayService didntPayService, DidntPayRepository didntPayRepository) {
        this.didntPayService = didntPayService;
        this.didntPayRepository = didntPayRepository;
    }

    /**
     * {@code POST  /didnt-pays} : Create a new didntPay.
     *
     * @param didntPayDTO the didntPayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new didntPayDTO, or with status {@code 400 (Bad Request)} if the didntPay has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DidntPayDTO> createDidntPay(@RequestBody DidntPayDTO didntPayDTO) throws URISyntaxException {
        log.debug("REST request to save DidntPay : {}", didntPayDTO);
        if (didntPayDTO.getId() != null) {
            throw new BadRequestAlertException("A new didntPay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DidntPayDTO result = didntPayService.save(didntPayDTO);
        return ResponseEntity
            .created(new URI("/api/didnt-pays/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /didnt-pays/:id} : Updates an existing didntPay.
     *
     * @param id the id of the didntPayDTO to save.
     * @param didntPayDTO the didntPayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated didntPayDTO,
     * or with status {@code 400 (Bad Request)} if the didntPayDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the didntPayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DidntPayDTO> updateDidntPay(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody DidntPayDTO didntPayDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DidntPay : {}, {}", id, didntPayDTO);
        if (didntPayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, didntPayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!didntPayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DidntPayDTO result = didntPayService.update(didntPayDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, didntPayDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /didnt-pays/:id} : Partial updates given fields of an existing didntPay, field will ignore if it is null
     *
     * @param id the id of the didntPayDTO to save.
     * @param didntPayDTO the didntPayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated didntPayDTO,
     * or with status {@code 400 (Bad Request)} if the didntPayDTO is not valid,
     * or with status {@code 404 (Not Found)} if the didntPayDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the didntPayDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DidntPayDTO> partialUpdateDidntPay(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody DidntPayDTO didntPayDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DidntPay partially : {}, {}", id, didntPayDTO);
        if (didntPayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, didntPayDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!didntPayRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DidntPayDTO> result = didntPayService.partialUpdate(didntPayDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, didntPayDTO.getId())
        );
    }

    /**
     * {@code GET  /didnt-pays} : get all the didntPays.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of didntPays in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DidntPayDTO>> getAllDidntPays(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of DidntPays");
        Page<DidntPayDTO> page = didntPayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /didnt-pays/:id} : get the "id" didntPay.
     *
     * @param id the id of the didntPayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the didntPayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DidntPayDTO> getDidntPay(@PathVariable String id) {
        log.debug("REST request to get DidntPay : {}", id);
        Optional<DidntPayDTO> didntPayDTO = didntPayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(didntPayDTO);
    }

    /**
     * {@code DELETE  /didnt-pays/:id} : delete the "id" didntPay.
     *
     * @param id the id of the didntPayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDidntPay(@PathVariable String id) {
        log.debug("REST request to delete DidntPay : {}", id);
        didntPayService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
