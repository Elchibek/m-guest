package com.guest.app.service;

import com.guest.app.domain.Floor;
import com.guest.app.domain.GuestHouse;
import com.guest.app.repository.GuestHouseRepository;
import com.guest.app.service.dto.GuestBlockDTO;
import com.guest.app.service.dto.GuestHouseDTO;
import com.guest.app.service.mapper.GuestHouseMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.guest.app.domain.GuestHouse}.
 */
@Service
public class GuestHouseService {

    private final Logger log = LoggerFactory.getLogger(GuestHouseService.class);

    private final GuestHouseRepository guestHouseRepository;

    private final GuestHouseMapper guestHouseMapper;

    public GuestHouseService(GuestHouseRepository guestHouseRepository, GuestHouseMapper guestHouseMapper) {
        this.guestHouseRepository = guestHouseRepository;
        this.guestHouseMapper = guestHouseMapper;
    }

    /**
     * Save a guestHouse.
     *
     * @param guestHouseDTO the entity to save.
     * @return the persisted entity.
     */
    public GuestHouseDTO save(GuestHouseDTO guestHouseDTO) {
        log.debug("Request to save GuestHouse : {}", guestHouseDTO);
        GuestHouse guestHouse = guestHouseMapper.toEntity(guestHouseDTO);
        guestHouse = guestHouseRepository.save(guestHouse);
        return guestHouseMapper.toDto(guestHouse);
    }

    public void save(Floor floor, GuestBlockDTO gDto) {
        for (int i = 1; i <= gDto.getNumHouse(); i++) {
            GuestHouse guestHouse = new GuestHouse();
            guestHouse.setName(gDto.getNameHouse());
            guestHouse.setNumHouse(i);
            guestHouse.setIsEmpty(false);
            guestHouse.setBackgroundColor("green");
            guestHouse.setCountPerson(0);
            guestHouse.setFloor(floor);
            guestHouse.setGuestBlockId(gDto.getId());
            guestHouseRepository.save(guestHouse);
        }
    }

    /**
     * Update a guestHouse.
     *
     * @param guestHouseDTO the entity to save.
     * @return the persisted entity.
     */
    public GuestHouseDTO update(GuestHouseDTO guestHouseDTO) {
        log.debug("Request to update GuestHouse : {}", guestHouseDTO);
        GuestHouse guestHouse = guestHouseMapper.toEntity(guestHouseDTO);
        guestHouse = guestHouseRepository.save(guestHouse);
        return guestHouseMapper.toDto(guestHouse);
    }

    /**
     * Partially update a guestHouse.
     *
     * @param guestHouseDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GuestHouseDTO> partialUpdate(GuestHouseDTO guestHouseDTO) {
        log.debug("Request to partially update GuestHouse : {}", guestHouseDTO);

        return guestHouseRepository
            .findById(guestHouseDTO.getId())
            .map(existingGuestHouse -> {
                guestHouseMapper.partialUpdate(existingGuestHouse, guestHouseDTO);

                return existingGuestHouse;
            })
            .map(guestHouseRepository::save)
            .map(guestHouseMapper::toDto);
    }

    /**
     * Get all the guestHouses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<GuestHouseDTO> findAll(String floorId, String guestBlockId) {
        log.debug("Request to get all GuestHouses");
        if (floorId != null && !floorId.isEmpty()) {
            return guestHouseRepository.findAllByFloorId(floorId, Pageable.unpaged()).map(guestHouseMapper::toDto);
        } else {
            return guestHouseRepository.findAll(Pageable.unpaged()).map(guestHouseMapper::toDto);
        }
    }

    /**
     * Get one guestHouse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<GuestHouseDTO> findOne(String id) {
        log.debug("Request to get GuestHouse : {}", id);
        return guestHouseRepository.findById(id).map(guestHouseMapper::toDto);
    }

    public Optional<GuestHouseDTO> findFirstGuestBlockId(String guestBlockId) {
        log.debug("Request to get GuestHouse Guest Block Id: {}", guestBlockId);
        return guestHouseRepository.findFirstByGuestBlockId(guestBlockId).map(guestHouseMapper::toDto);
    }

    /**
     * Delete the guestHouse by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete GuestHouse : {}", id);
        guestHouseRepository.deleteById(id);
    }

    public void deleteAll() {
        guestHouseRepository.deleteAll();
    }
}
