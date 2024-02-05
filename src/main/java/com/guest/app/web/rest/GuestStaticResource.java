package com.guest.app.web.rest;

import com.guest.app.repository.GuestStaticRepository;
import com.guest.app.service.GuestStaticService;
import com.guest.app.service.dto.GuestStaticDTO;
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
 * REST controller for managing {@link com.guest.app.domain.GuestStatic}.
 */
@RestController
@RequestMapping("/api/guest-statics")
public class GuestStaticResource {

    private final Logger log = LoggerFactory.getLogger(GuestStaticResource.class);

    private static final String ENTITY_NAME = "guestStatic";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuestStaticService guestStaticService;

    private final GuestStaticRepository guestStaticRepository;

    public GuestStaticResource(GuestStaticService guestStaticService, GuestStaticRepository guestStaticRepository) {
        this.guestStaticService = guestStaticService;
        this.guestStaticRepository = guestStaticRepository;
    }

    /**
     * {@code GET  /guest-statics} : get all the guestStatics.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of guestStatics in body.
     */
    @GetMapping("")
    public ResponseEntity<List<GuestStaticDTO>> getAllGuestStatics(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of GuestStatics");
        Page<GuestStaticDTO> page = guestStaticService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /guest-statics/:id} : get the "id" guestStatic.
     *
     * @param id the id of the guestStaticDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the guestStaticDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/get")
    public ResponseEntity<GuestStaticDTO> getGuestStatic(@RequestParam(required = false) Boolean isArhive) {
        log.debug("REST request to get GuestStatic : {}", isArhive);
        Optional<GuestStaticDTO> guestStaticDTO = guestStaticService.findOne(isArhive);
        return ResponseUtil.wrapOrNotFound(guestStaticDTO);
    }
}
