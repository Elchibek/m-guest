package com.guest.app.service;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.guest.app.domain.Entrance;
import com.guest.app.domain.GuestBlock;
import com.guest.app.domain.GuestHouse;
import com.guest.app.repository.GuestBlockRepository;
import com.guest.app.service.dto.GuestBlockDTO;
import com.guest.app.service.mapper.GuestBlockMapper;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.guest.app.domain.GuestBlock}.
 */
@Service
public class GuestBlockService {

    private final Logger log = LoggerFactory.getLogger(GuestBlockService.class);

    private final MongoTemplate mt;

    private final GuestBlockRepository guestBlockRepository;

    private final EntranceService entranceService;

    private final FloorService floorService;

    private final GuestHouseService guestHouseService;

    private final GuestService guestService;

    private final GuestBlockMapper guestBlockMapper;

    public GuestBlockService(
        MongoTemplate mongoTemplate,
        GuestBlockRepository guestBlockRepository,
        GuestBlockMapper guestBlockMapper,
        EntranceService entranceService,
        FloorService floorService,
        GuestService guestService,
        GuestHouseService guestHouseService
    ) {
        this.mt = mongoTemplate;
        this.guestBlockRepository = guestBlockRepository;
        this.guestBlockMapper = guestBlockMapper;
        this.entranceService = entranceService;
        this.floorService = floorService;
        this.guestService = guestService;
        this.guestHouseService = guestHouseService;
    }

    /**
     * Save a guestBlock.
     *
     * @param guestBlockDTO the entity to save.
     * @return the persisted entity.
     */
    public GuestBlockDTO save(GuestBlockDTO guestBlockDTO) {
        log.debug("Request to save GuestBlock : {}", guestBlockDTO);
        GuestBlock guestBlock = guestBlockMapper.toEntity(guestBlockDTO);
        guestBlock = guestBlockRepository.save(guestBlock);
        guestBlockDTO.setId(guestBlock.getId());
        entranceService.save(guestBlock, guestBlockDTO);

        return guestBlockMapper.toDto(guestBlock);
    }

    /**
     * Update a guestBlock.
     *
     * @param guestBlockDTO the entity to save.
     * @return the persisted entity.
     */
    public GuestBlockDTO update(GuestBlockDTO guestBlockDTO) {
        log.debug("Request to update GuestBlock : {}", guestBlockDTO);
        GuestBlock guestBlock = guestBlockMapper.toEntity(guestBlockDTO);
        updatAllParent(guestBlock.getId(), guestBlockDTO);
        guestBlock = guestBlockRepository.save(guestBlock);
        return guestBlockMapper.toDto(guestBlock);
    }

    /**
     * Partially update a guestBlock.
     *
     * @param guestBlockDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GuestBlockDTO> partialUpdate(GuestBlockDTO guestBlockDTO) {
        log.debug("Request to partially update GuestBlock : {}", guestBlockDTO);

        return guestBlockRepository
            .findById(guestBlockDTO.getId())
            .map(existingGuestBlock -> {
                guestBlockMapper.partialUpdate(existingGuestBlock, guestBlockDTO);
                updatAllParent(guestBlockDTO.getId(), guestBlockDTO);
                return existingGuestBlock;
            })
            .map(guestBlockRepository::save)
            .map(guestBlockMapper::toDto);
    }

    /**
     * Get all the guestBlocks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<GuestBlockDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GuestBlocks");
        return guestBlockRepository.findAll(pageable).map(guestBlockMapper::toDto);
    }

    public List<GuestBlock> findAll() {
        log.debug("Request to get all GuestBlocks");
        return guestBlockRepository.findAll();
    }

    /**
     * Get one guestBlock by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<GuestBlockDTO> findOne(String id) {
        log.debug("Request to get GuestBlock : {}", id);
        return guestBlockRepository.findById(id).map(guestBlockMapper::toDto);
    }

    /**
     * Delete the guestBlock by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete GuestBlock : {}", id);
        long count = guestService.countGuestBlock(id);
        if (count <= 0) {
            guestBlockRepository.deleteById(id);
            floorService.deleteAll();
            entranceService.deleteAll();
            guestHouseService.deleteAll();
        }
    }

    private void updatAllParent(String guestBlockId, GuestBlockDTO block) {
        String entranceName = entranceService.findFirstGuestBlockId(guestBlockId).get().getName();
        String guestHouseName = guestHouseService.findFirstGuestBlockId(guestBlockId).get().getName();

        if (entranceName != null && !entranceName.equals(block.getNameEntrance())) {
            mt.updateMulti(
                Query.query(where("guestBlock.id").is(guestBlockId)),
                Update.update("name", block.getNameEntrance()),
                Entrance.class,
                "entrance"
            );
        }
        if (guestHouseName != null && !guestHouseName.equals(block.getNameHouse())) {
            mt.updateMulti(
                Query.query(where("guestBlockId").is(guestBlockId)),
                Update.update("name", block.getNameHouse()),
                GuestHouse.class,
                "guest_house"
            );
        }
    }
}
