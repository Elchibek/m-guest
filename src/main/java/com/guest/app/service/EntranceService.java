package com.guest.app.service;

import com.guest.app.domain.Entrance;
import com.guest.app.domain.GuestBlock;
import com.guest.app.repository.EntranceRepository;
import com.guest.app.service.dto.EntranceDTO;
import com.guest.app.service.dto.GuestBlockDTO;
import com.guest.app.service.mapper.EntranceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.guest.app.domain.Entrance}.
 */
@Service
public class EntranceService {

    private final Logger log = LoggerFactory.getLogger(EntranceService.class);

    private final EntranceRepository entranceRepository;

    private final FloorService floorService;

    private final EntranceMapper entranceMapper;

    public EntranceService(EntranceRepository entranceRepository, EntranceMapper entranceMapper, FloorService floorService) {
        this.entranceRepository = entranceRepository;
        this.entranceMapper = entranceMapper;
        this.floorService = floorService;
    }

    /**
     * Save a entrance.
     *
     * @param entranceDTO the entity to save.
     * @return the persisted entity.
     */
    public EntranceDTO save(EntranceDTO entranceDTO) {
        log.debug("Request to save Entrance : {}", entranceDTO);
        Entrance entrance = entranceMapper.toEntity(entranceDTO);
        entrance = entranceRepository.save(entrance);
        return entranceMapper.toDto(entrance);
    }

    public void save(GuestBlock gBlock, GuestBlockDTO gDto) {
        for (int i = 1; i <= gDto.getNumEntrance(); i++) {
            Entrance entrance = new Entrance();
            entrance.setName(gDto.getNameEntrance());
            entrance.setNumEntrance(i);
            entrance.setGuestBlock(gBlock);
            entrance = entranceRepository.save(entrance);
            // Floor Service
            floorService.save(entrance, gDto);
        }
    }

    /**
     * Update a entrance.
     *
     * @param entranceDTO the entity to save.
     * @return the persisted entity.
     */
    public EntranceDTO update(EntranceDTO entranceDTO) {
        log.debug("Request to update Entrance : {}", entranceDTO);
        Entrance entrance = entranceMapper.toEntity(entranceDTO);
        entrance = entranceRepository.save(entrance);
        return entranceMapper.toDto(entrance);
    }

    /**
     * Partially update a entrance.
     *
     * @param entranceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EntranceDTO> partialUpdate(EntranceDTO entranceDTO) {
        log.debug("Request to partially update Entrance : {}", entranceDTO);

        return entranceRepository
            .findById(entranceDTO.getId())
            .map(existingEntrance -> {
                entranceMapper.partialUpdate(existingEntrance, entranceDTO);

                return existingEntrance;
            })
            .map(entranceRepository::save)
            .map(entranceMapper::toDto);
    }

    /**
     * Get all the entrances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<EntranceDTO> findAll(String guestBlockId, Pageable pageable) {
        log.debug("Request to get all Entrances");
        if (guestBlockId != null && !guestBlockId.isEmpty()) {
            return entranceRepository.findAllByGuestBlockId(guestBlockId, pageable).map(entranceMapper::toDto);
        } else {
            return entranceRepository.findAll(pageable).map(entranceMapper::toDto);
        }
    }

    public Page<EntranceDTO> findAllByGuestBlockId(String guestBlockId, Pageable pageable) {
        log.debug("Request to get all Entrances");
        Page<EntranceDTO> page = entranceRepository.findAllByGuestBlockId(guestBlockId, pageable).map(entranceMapper::toDto);
        page.map(e -> {
            e.setfDtos(floorService.findAllByEntranceId(e.getId(), pageable).getContent());
            return e;
        });
        return page;
    }

    /**
     * Get one entrance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<EntranceDTO> findOne(String id) {
        log.debug("Request to get Entrance : {}", id);
        return entranceRepository.findById(id).map(entranceMapper::toDto);
    }

    public Optional<EntranceDTO> findFirstGuestBlockId(String guestBlockId) {
        log.debug("Request to get Entrance GuestBlock ID: {}", guestBlockId);
        return entranceRepository.findFirstByGuestBlockId(guestBlockId).map(entranceMapper::toDto);
    }

    /**
     * Delete the entrance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Entrance : {}", id);
        entranceRepository.deleteById(id);
    }

    public void deleteAll() {
        entranceRepository.deleteAll();
    }
}
