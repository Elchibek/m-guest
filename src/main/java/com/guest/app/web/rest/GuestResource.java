package com.guest.app.web.rest;

import com.guest.app.domain.Guest;
import com.guest.app.repository.GuestRepository;
import com.guest.app.service.GuestContactService;
import com.guest.app.service.GuestService;
import com.guest.app.service.dto.GuestContactDTO;
import com.guest.app.service.dto.GuestDTO;
import com.guest.app.service.dto.GuestFilterDTO;
import com.guest.app.service.dto.docs.Doc;
import com.guest.app.web.rest.errors.BadRequestAlertException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.guest.app.domain.Guest}.
 */
@RestController
@RequestMapping("/api/guests")
public class GuestResource {

    private final Logger log = LoggerFactory.getLogger(GuestResource.class);

    private static final String ENTITY_NAME = "guest";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GuestService guestService;

    private final GuestContactService guestContactService;

    private final GuestRepository guestRepository;

    public GuestResource(GuestService guestService, GuestContactService guestContactService, GuestRepository guestRepository) {
        this.guestService = guestService;
        this.guestContactService = guestContactService;
        this.guestRepository = guestRepository;
    }

    @PostMapping("")
    public ResponseEntity<GuestDTO> createGuest(@Valid @RequestBody GuestDTO guestDTO) throws URISyntaxException {
        log.debug("REST request to save Guest : {}", guestDTO);
        if (guestDTO.getId() != null) {
            throw new BadRequestAlertException("A new guest cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GuestDTO result = guestService.save(guestDTO);
        return ResponseEntity
            .created(new URI("/api/guests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<?> downloadFile(@RequestParam(required = false) String type) {
        log.debug("REST request to get a page of GuestContacts");

        Resource resource = null;
        try {
            resource = guestService.downloadDoc("." + type);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/" + applicationName;
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity
            .ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
            .body(resource);
    }

    @PostMapping("/saveToDoc")
    public ResponseEntity<?> saveDoc(@Valid @RequestBody Doc doc) throws URISyntaxException {
        log.debug("REST request to save to Doc : {}", doc);
        guestService.saveDoc(doc);
        return ResponseEntity
            .created(new URI("/api/guests/downloadFile?type=" + doc.getDocType()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, "k"))
            .body("Save");
    }

    @PutMapping("/{id}")
    public ResponseEntity<GuestDTO> updateGuest(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody GuestDTO guestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Guest : {}, {}", id, guestDTO);
        if (guestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GuestDTO result = guestService.update(guestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestDTO.getId()))
            .body(result);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GuestDTO> partialUpdateGuest(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody GuestDTO guestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Guest partially : {}, {}", id, guestDTO);
        if (guestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, guestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!guestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GuestDTO> result = guestService.partialUpdate(guestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, guestDTO.getId())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<Guest>> getAllGuests(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        // ---------------------------------------------------------
        @RequestParam(required = false) String userId,
        // ---------------------------------------------------------
        @RequestParam(required = false) String searchText,
        @RequestParam(required = false) String searchType,
        // ---------------------------------------------------------
        @RequestParam(required = false) String guestFromId,
        @RequestParam(required = false) String guestBlockId,
        @RequestParam(required = false) String entranceId,
        @RequestParam(required = false) String floorId,
        @RequestParam(required = false) String houseId,
        // ---------------------------------------------------------
        @RequestParam(required = false) String typeBetween,
        @RequestParam(required = false) Integer from,
        @RequestParam(required = false) Integer before,
        // ---------------------------------------------------------
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
        @RequestParam(required = false) String typeDate,
        // ---------------------------------------------------------
        @RequestParam(required = false) Boolean isPaid,
        @RequestParam(required = false) Boolean isDeparture,
        // ---------------------------------------------------------
        @RequestParam(required = false) Boolean isUpdate,
        @RequestParam(required = false) String parentId,
        @RequestParam(required = false, defaultValue = "false") Boolean isChange,
        @RequestParam(required = false, defaultValue = "false") Boolean isArchive,
        @RequestParam(required = false, defaultValue = "false") Boolean isDeleted
    ) {
        log.debug("REST request to get a page of Guests");

        GuestFilterDTO gFilterDTO = new GuestFilterDTO();
        gFilterDTO.setUserId(userId);
        gFilterDTO.setSearchText(searchText);
        gFilterDTO.setSearchType(searchType);
        gFilterDTO.setGuestFromId(guestFromId);
        gFilterDTO.setGuestBlockId(guestBlockId);
        gFilterDTO.setEntranceId(entranceId);
        gFilterDTO.setFloorId(floorId);
        gFilterDTO.setHouseId(houseId);
        gFilterDTO.setTypeBetween(typeBetween);
        gFilterDTO.setFrom(from);
        gFilterDTO.setBefore(before);
        gFilterDTO.setStartDate(startDate);
        gFilterDTO.setEndDate(endDate);
        gFilterDTO.setTypeDate(typeDate);
        gFilterDTO.setIsPaid(isPaid);
        gFilterDTO.setIsDeparture(isDeparture);
        gFilterDTO.setIsUpdate(isUpdate);
        gFilterDTO.setIsDeleted(isDeleted);
        gFilterDTO.setIsChange(isChange);
        gFilterDTO.setIsArchive(isArchive);
        gFilterDTO.setParentId(parentId);
        Page<Guest> page = guestService.findAll(gFilterDTO, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<GuestContactDTO>> getAllGuestContacts(@RequestParam(required = true) String guestId) {
        log.debug("REST request to get a page of GuestContacts");
        Page<GuestContactDTO> page = guestContactService.findAllByGuestId(guestId);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestDTO> getGuest(@PathVariable String id) {
        log.debug("REST request to get Guest : {}", id);
        Optional<GuestDTO> guestDTO = guestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(guestDTO);
    }

    @GetMapping("/archive/{id}")
    public ResponseEntity<GuestDTO> archiveGuest(@PathVariable String id) {
        log.debug("REST request to archive Guest : {}", id);
        guestService.archive(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(
        @PathVariable String id,
        @RequestParam(required = false, defaultValue = "false") Boolean isRestore
    ) {
        log.debug("REST request to delete Guest : {}", id);
        guestService.delete(id, isRestore);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    @GetMapping("/_search")
    public ResponseEntity<List<Guest>> searchGuests(
        @RequestParam String query,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search for a page of Guests for query {}", query);
        Page<Guest> page = guestService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/static")
    public ResponseEntity<List<Guest>> getStatic(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        List<Guest> page = guestService.findStatic(startDate, endDate);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), Page.empty());
        return ResponseEntity.ok().headers(headers).body(page);
    }
}
