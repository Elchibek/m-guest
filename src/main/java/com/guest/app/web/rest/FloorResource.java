package com.guest.app.web.rest;

import com.guest.app.service.FloorService;
import com.guest.app.service.dto.FloorDTO;
import java.util.List;
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
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.guest.app.domain.Floor}.
 */
@RestController
@RequestMapping("/api/floors")
public class FloorResource {

    private final Logger log = LoggerFactory.getLogger(FloorResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FloorService floorService;

    public FloorResource(FloorService floorService) {
        this.floorService = floorService;
    }

    @GetMapping("")
    public ResponseEntity<List<FloorDTO>> getAllFloors(
        @RequestParam(required = false) String entranceId,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Floors");
        Page<FloorDTO> page = floorService.findAll(entranceId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FloorDTO> getFloor(@PathVariable String id) {
        log.debug("REST request to get Floor : {}", id);
        Optional<FloorDTO> floorDTO = floorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(floorDTO);
    }
}
