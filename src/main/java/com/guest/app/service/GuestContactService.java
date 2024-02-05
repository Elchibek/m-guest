package com.guest.app.service;

import com.guest.app.domain.GuestContact;
import com.guest.app.repository.GuestContactRepository;
import com.guest.app.service.dto.GuestContactDTO;
import com.guest.app.service.mapper.GuestContactMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.guest.app.domain.GuestContact}.
 */
@Service
public class GuestContactService {

    private final Logger log = LoggerFactory.getLogger(GuestContactService.class);

    private final GuestContactRepository guestContactRepository;

    private final GuestContactMapper guestContactMapper;

    public GuestContactService(GuestContactRepository guestContactRepository, GuestContactMapper guestContactMapper) {
        this.guestContactRepository = guestContactRepository;
        this.guestContactMapper = guestContactMapper;
    }

    /**
     * Save a guestContact.
     *
     * @param guestContactDTO the entity to save.
     * @return the persisted entity.
     */
    public GuestContactDTO save(GuestContactDTO guestContactDTO) {
        log.debug("Request to save GuestContact : {}", guestContactDTO);
        GuestContact guestContact = guestContactMapper.toEntity(guestContactDTO);
        guestContact = guestContactRepository.save(guestContact);
        return guestContactMapper.toDto(guestContact);
    }

    /**
     * Update a guestContact.
     *
     * @param guestContactDTO the entity to save.
     * @return the persisted entity.
     */
    public GuestContactDTO update(GuestContactDTO guestContactDTO) {
        log.debug("Request to update GuestContact : {}", guestContactDTO);
        GuestContact guestContact = guestContactMapper.toEntity(guestContactDTO);
        guestContact = guestContactRepository.save(guestContact);
        return guestContactMapper.toDto(guestContact);
    }

    /**
     * Partially update a guestContact.
     *
     * @param guestContactDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GuestContactDTO> partialUpdate(GuestContactDTO guestContactDTO) {
        log.debug("Request to partially update GuestContact : {}", guestContactDTO);

        return guestContactRepository
            .findById(guestContactDTO.getId())
            .map(existingGuestContact -> {
                guestContactMapper.partialUpdate(existingGuestContact, guestContactDTO);

                return existingGuestContact;
            })
            .map(guestContactRepository::save)
            .map(guestContactMapper::toDto);
    }

    /**
     * Get all the guestContacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<GuestContactDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GuestContacts");
        return guestContactRepository.findAll(pageable).map(guestContactMapper::toDto);
    }

    public Page<GuestContactDTO> findAllByGuestId(String guestId) {
        return guestContactRepository.findAllByGuestId(guestId, Pageable.unpaged()).map(guestContactMapper::toDto);
    }

    /**
     * Get one guestContact by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<GuestContactDTO> findOne(String id) {
        log.debug("Request to get GuestContact : {}", id);
        return guestContactRepository.findById(id).map(guestContactMapper::toDto);
    }

    /**
     * Delete the guestContact by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete GuestContact : {}", id);
        guestContactRepository.deleteById(id);
    }

    public void deleteByGuestId(String guestId) {
        log.debug("Request to delete GuestContact : {}", guestId);
        guestContactRepository.deleteByGuestId(guestId);
    }
}
