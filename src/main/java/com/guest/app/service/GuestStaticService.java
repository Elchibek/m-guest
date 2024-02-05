package com.guest.app.service;

import com.guest.app.domain.GuestStatic;
import com.guest.app.repository.GuestHouseRepository;
import com.guest.app.repository.GuestStaticRepository;
import com.guest.app.service.dto.GuestStaticDTO;
import com.guest.app.service.mapper.GuestStaticMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.guest.app.domain.GuestStatic}.
 */
@Service
public class GuestStaticService {

    private final Logger log = LoggerFactory.getLogger(GuestStaticService.class);

    private final GuestStaticRepository guestStaticRepository;

    private final GuestStaticMapper guestStaticMapper;

    public GuestStaticService(
        GuestStaticRepository guestStaticRepository,
        GuestStaticMapper guestStaticMapper,
        GuestHouseRepository guestHouseRepository
    ) {
        this.guestStaticRepository = guestStaticRepository;
        this.guestStaticMapper = guestStaticMapper;
    }

    /**
     * Save a guestStatic.
     *
     * @param guestStaticDTO the entity to save.
     * @return the persisted entity.
     */
    public GuestStaticDTO save(GuestStaticDTO guestStaticDTO) {
        log.debug("Request to save GuestStatic : {}", guestStaticDTO);
        GuestStatic guestStatic = guestStaticMapper.toEntity(guestStaticDTO);
        guestStatic = guestStaticRepository.save(guestStatic);
        return guestStaticMapper.toDto(guestStatic);
    }

    /**
     * Update a guestStatic.
     *
     * @param guestStaticDTO the entity to save.
     * @return the persisted entity.
     */
    public GuestStaticDTO update(GuestStaticDTO guestStaticDTO) {
        log.debug("Request to update GuestStatic : {}", guestStaticDTO);
        GuestStatic guestStatic = guestStaticMapper.toEntity(guestStaticDTO);
        guestStatic = guestStaticRepository.save(guestStatic);
        return guestStaticMapper.toDto(guestStatic);
    }

    /**
     * Partially update a guestStatic.
     *
     * @param guestStaticDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GuestStaticDTO> partialUpdate(GuestStaticDTO guestStaticDTO) {
        log.debug("Request to partially update GuestStatic : {}", guestStaticDTO);

        return guestStaticRepository
            .findById(guestStaticDTO.getId())
            .map(existingGuestStatic -> {
                guestStaticMapper.partialUpdate(existingGuestStatic, guestStaticDTO);

                return existingGuestStatic;
            })
            .map(guestStaticRepository::save)
            .map(guestStaticMapper::toDto);
    }

    /**
     * Get all the guestStatics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<GuestStaticDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GuestStatics");
        return guestStaticRepository.findAll(pageable).map(guestStaticMapper::toDto);
    }

    /**
     * Get one guestStatic by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<GuestStaticDTO> findOne(boolean isArhive) {
        log.debug("Request to get GuestStatic : {}", isArhive);
        return guestStaticRepository.getOneIsArchive(isArhive).map(guestStaticMapper::toDto);
    }

    /**
     * Delete the guestStatic by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete GuestStatic : {}", id);
        guestStaticRepository.deleteById(id);
    }
}
