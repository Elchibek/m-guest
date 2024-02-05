package com.guest.app.service;

import com.guest.app.domain.PutAway;
import com.guest.app.repository.PutAwayRepository;
import com.guest.app.service.dto.GuestDTO;
import com.guest.app.service.dto.PutAwayDTO;
import com.guest.app.service.mapper.PutAwayMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.guest.app.domain.PutAway}.
 */
@Service
public class PutAwayService {

    private final Logger log = LoggerFactory.getLogger(PutAwayService.class);

    private final PutAwayRepository putAwayRepository;

    private final PutAwayMapper putAwayMapper;

    public PutAwayService(PutAwayRepository putAwayRepository, PutAwayMapper putAwayMapper) {
        this.putAwayRepository = putAwayRepository;
        this.putAwayMapper = putAwayMapper;
    }

    /**
     * Save a putAway.
     *
     * @param putAwayDTO the entity to save.
     * @return the persisted entity.
     */
    public PutAwayDTO save(PutAwayDTO putAwayDTO) {
        log.debug("Request to save PutAway : {}", putAwayDTO);
        PutAway putAway = putAwayMapper.toEntity(putAwayDTO);
        if (putAway.getGuest().getId() != null) {
            // editGuest(putAwayDTO, false);
            putAway = putAwayRepository.save(putAway);
        }
        return putAwayMapper.toDto(putAway);
    }

    /**
     * Update a putAway.
     *
     * @param putAwayDTO the entity to save.
     * @return the persisted entity.
     */
    public PutAwayDTO update(PutAwayDTO putAwayDTO) {
        log.debug("Request to update PutAway : {}", putAwayDTO);
        PutAway putAway = putAwayMapper.toEntity(putAwayDTO);
        if (putAway.getGuest().getId() != null) {
            // editGuest(putAwayDTO, false);
            putAway = putAwayRepository.save(putAway);
        }
        return putAwayMapper.toDto(putAway);
    }

    /**
     * Partially update a putAway.
     *
     * @param putAwayDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PutAwayDTO> partialUpdate(PutAwayDTO putAwayDTO) {
        log.debug("Request to partially update PutAway : {}", putAwayDTO);
        return putAwayRepository
            .findById(putAwayDTO.getId())
            .map(existingPutAway -> {
                if (putAwayDTO.getGuest().getId() != null) {
                    // editGuest(putAwayDTO, false);
                    putAwayMapper.partialUpdate(existingPutAway, putAwayDTO);
                }

                return existingPutAway;
            })
            .map(putAwayRepository::save)
            .map(putAwayMapper::toDto);
    }

    /**
     * Get all the putAways.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<PutAwayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PutAways");
        return putAwayRepository.findAll(pageable).map(putAwayMapper::toDto);
    }

    /**
     * Get one putAway by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PutAwayDTO> findOne(String id) {
        log.debug("Request to get PutAway : {}", id);
        return putAwayRepository.findById(id).map(putAwayMapper::toDto);
    }

    /**
     * Delete the putAway by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete PutAway : {}", id);
        PutAwayDTO putAwayDTO = findOne(id).get();
        // if (putAwayDTO.getGuest() != null) editGuest(putAwayDTO, true);
        putAwayRepository.deleteById(id);
    }

    public void deleteByGuestId(String guestId) {
        putAwayRepository.deleteByGuestId(guestId);
    }
    // private void editGuest(PutAwayDTO pa, boolean isDelete) {
    //     GuestDTO guestDTO = guestService.findOne(pa.getGuest().getId()).get();
    //     guestDTO.setPrevCountDidntPay(guestDTO.getCountDidntPay()); // 1
    //     guestDTO.setPrevCountPerson(guestDTO.getCountPerson());
    //     int countPerson = guestDTO.getCountPerson();

    //     if (!isDelete && pa.getId() != null && pa.getPrevCountPerson() != null) {
    //         countPerson = (countPerson + pa.getPrevCountPerson()) - pa.getCountPerson();
    //     } else if (isDelete) countPerson = countPerson + pa.getCountPerson(); else countPerson = countPerson - pa.getCountPerson();

    //     guestDTO.setCountPerson(countPerson);
    //     guestService.update(guestDTO);
    // }
}
