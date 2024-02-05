package com.guest.app.service;

import com.guest.app.domain.GuestFrom;
import com.guest.app.repository.GuestFromRepository;
import com.guest.app.service.dto.GuestFromDTO;
import com.guest.app.service.mapper.GuestFromMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.guest.app.domain.GuestFrom}.
 */
@Service
public class GuestFromService {

    private final Logger log = LoggerFactory.getLogger(GuestFromService.class);

    private final GuestFromRepository guestFromRepository;

    private final GuestFromMapper guestFromMapper;

    public GuestFromService(GuestFromRepository guestFromRepository, GuestFromMapper guestFromMapper) {
        this.guestFromRepository = guestFromRepository;
        this.guestFromMapper = guestFromMapper;
    }

    /**
     * Save a guestFrom.
     *
     * @param guestFromDTO the entity to save.
     * @return the persisted entity.
     */
    public GuestFromDTO save(GuestFromDTO guestFromDTO) {
        log.debug("Request to save GuestFrom : {}", guestFromDTO);
        GuestFrom guestFrom = guestFromMapper.toEntity(guestFromDTO);
        guestFrom = guestFromRepository.save(guestFrom);
        return guestFromMapper.toDto(guestFrom);
    }

    /**
     * Update a guestFrom.
     *
     * @param guestFromDTO the entity to save.
     * @return the persisted entity.
     */
    public GuestFromDTO update(GuestFromDTO guestFromDTO) {
        log.debug("Request to update GuestFrom : {}", guestFromDTO);
        GuestFrom guestFrom = guestFromMapper.toEntity(guestFromDTO);
        guestFrom = guestFromRepository.save(guestFrom);
        return guestFromMapper.toDto(guestFrom);
    }

    /**
     * Partially update a guestFrom.
     *
     * @param guestFromDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GuestFromDTO> partialUpdate(GuestFromDTO guestFromDTO) {
        log.debug("Request to partially update GuestFrom : {}", guestFromDTO);

        return guestFromRepository
            .findById(guestFromDTO.getId())
            .map(existingGuestFrom -> {
                guestFromMapper.partialUpdate(existingGuestFrom, guestFromDTO);

                return existingGuestFrom;
            })
            .map(guestFromRepository::save)
            .map(guestFromMapper::toDto);
    }

    /**
     * Get all the guestFroms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<GuestFromDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GuestFroms");
        return guestFromRepository.findAll(pageable).map(guestFromMapper::toDto);
    }

    /**
     * Get one guestFrom by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<GuestFromDTO> findOne(String id) {
        log.debug("Request to get GuestFrom : {}", id);
        return guestFromRepository.findById(id).map(guestFromMapper::toDto);
    }

    /**
     * Delete the guestFrom by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete GuestFrom : {}", id);
        guestFromRepository.deleteById(id);
    }
}
