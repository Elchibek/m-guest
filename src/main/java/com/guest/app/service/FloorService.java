package com.guest.app.service;

import com.guest.app.domain.Entrance;
import com.guest.app.domain.Floor;
import com.guest.app.repository.FloorRepository;
import com.guest.app.service.dto.FloorDTO;
import com.guest.app.service.dto.GuestBlockDTO;
import com.guest.app.service.mapper.FloorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.guest.app.domain.Floor}.
 */
@Service
public class FloorService {

    private final Logger log = LoggerFactory.getLogger(FloorService.class);

    private final FloorRepository floorRepository;

    private final GuestHouseService guestHouseService;

    private final FloorMapper floorMapper;

    public FloorService(FloorRepository floorRepository, FloorMapper floorMapper, GuestHouseService guestHouseService) {
        this.floorRepository = floorRepository;
        this.floorMapper = floorMapper;
        this.guestHouseService = guestHouseService;
    }

    /**
     * Save a floor.
     *
     * @param floorDTO the entity to save.
     * @return the persisted entity.
     */
    public FloorDTO save(FloorDTO floorDTO) {
        log.debug("Request to save Floor : {}", floorDTO);
        Floor floor = floorMapper.toEntity(floorDTO);
        floor = floorRepository.save(floor);
        return floorMapper.toDto(floor);
    }

    public void save(Entrance entrance, GuestBlockDTO gDto) {
        for (int i = 1; i <= gDto.getNumFloor(); i++) {
            Floor floor = new Floor();
            floor.setNumFloor(i);
            floor.setEntrance(entrance);
            floor = floorRepository.save(floor);
            // Guest House Service Save
            guestHouseService.save(floor, gDto);
        }
    }

    /**
     * Update a floor.
     *
     * @param floorDTO the entity to save.
     * @return the persisted entity.
     */
    public FloorDTO update(FloorDTO floorDTO) {
        log.debug("Request to update Floor : {}", floorDTO);
        Floor floor = floorMapper.toEntity(floorDTO);
        floor = floorRepository.save(floor);
        return floorMapper.toDto(floor);
    }

    /**
     * Partially update a floor.
     *
     * @param floorDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FloorDTO> partialUpdate(FloorDTO floorDTO) {
        log.debug("Request to partially update Floor : {}", floorDTO);

        return floorRepository
            .findById(floorDTO.getId())
            .map(existingFloor -> {
                floorMapper.partialUpdate(existingFloor, floorDTO);

                return existingFloor;
            })
            .map(floorRepository::save)
            .map(floorMapper::toDto);
    }

    /**
     * Get all the floors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<FloorDTO> findAll(String entranceId, Pageable pageable) {
        log.debug("Request to get all Floors");
        if (entranceId != null && !entranceId.isEmpty()) return floorRepository
            .findAllByEntranceId(entranceId, pageable)
            .map(floorMapper::toDto); else return floorRepository.findAll(pageable).map(floorMapper::toDto);
    }

    public Page<FloorDTO> findAllByEntranceId(String entranceId, Pageable pageable) {
        log.debug("Request to get all Floors");
        Page<FloorDTO> page = floorRepository.findAllByEntranceId(entranceId, pageable).map(floorMapper::toDto);

        return page;
    }

    /**
     * Get one floor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<FloorDTO> findOne(String id) {
        log.debug("Request to get Floor : {}", id);
        return floorRepository.findById(id).map(floorMapper::toDto);
    }

    /**
     * Delete the floor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Floor : {}", id);
        floorRepository.deleteById(id);
    }

    public void deleteAll() {
        floorRepository.deleteAll();
    }
}
