package com.guest.app.service;

import com.guest.app.domain.DidntPay;
import com.guest.app.repository.DidntPayRepository;
import com.guest.app.service.dto.DidntPayDTO;
import com.guest.app.service.mapper.DidntPayMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.guest.app.domain.DidntPay}.
 */
@Service
public class DidntPayService {

    private final Logger log = LoggerFactory.getLogger(DidntPayService.class);

    private final DidntPayRepository didntPayRepository;

    private final DidntPayMapper didntPayMapper;

    public DidntPayService(DidntPayRepository didntPayRepository, DidntPayMapper didntPayMapper) {
        this.didntPayRepository = didntPayRepository;
        this.didntPayMapper = didntPayMapper;
    }

    /**
     * Save a didntPay.
     *
     * @param didntPayDTO the entity to save.
     * @return the persisted entity.
     */
    public DidntPayDTO save(DidntPayDTO didntPayDTO) {
        log.debug("Request to save DidntPay : {}", didntPayDTO);
        DidntPay didntPay = didntPayMapper.toEntity(didntPayDTO);
        didntPay = didntPayRepository.save(didntPay);
        return didntPayMapper.toDto(didntPay);
    }

    /**
     * Update a didntPay.
     *
     * @param didntPayDTO the entity to save.
     * @return the persisted entity.
     */
    public DidntPayDTO update(DidntPayDTO didntPayDTO) {
        log.debug("Request to update DidntPay : {}", didntPayDTO);
        DidntPay didntPay = didntPayMapper.toEntity(didntPayDTO);
        didntPay = didntPayRepository.save(didntPay);
        return didntPayMapper.toDto(didntPay);
    }

    /**
     * Partially update a didntPay.
     *
     * @param didntPayDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DidntPayDTO> partialUpdate(DidntPayDTO didntPayDTO) {
        log.debug("Request to partially update DidntPay : {}", didntPayDTO);

        return didntPayRepository
            .findById(didntPayDTO.getId())
            .map(existingDidntPay -> {
                didntPayMapper.partialUpdate(existingDidntPay, didntPayDTO);

                return existingDidntPay;
            })
            .map(didntPayRepository::save)
            .map(didntPayMapper::toDto);
    }

    /**
     * Get all the didntPays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<DidntPayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DidntPays");
        return didntPayRepository.findAll(pageable).map(didntPayMapper::toDto);
    }

    public Page<DidntPayDTO> findAllByGuestId(String guestId) {
        log.debug("Request to get all DidntPays");
        return didntPayRepository.findAllByGuestId(guestId, Pageable.unpaged()).map(didntPayMapper::toDto);
    }

    /**
     * Get one didntPay by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<DidntPayDTO> findOne(String id) {
        log.debug("Request to get DidntPay : {}", id);
        return didntPayRepository.findById(id).map(didntPayMapper::toDto);
    }

    /**
     * Delete the didntPay by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete DidntPay : {}", id);
        didntPayRepository.deleteById(id);
    }

    public void deleteByGuestId(String guestId) {
        log.debug("Request to delete DidntPay : {}", guestId);
        didntPayRepository.deleteByGuestId(guestId);
    }
}
