package com.guest.app.service;

import com.guest.app.domain.ArrivalDepartureStatic;
import com.guest.app.repository.ArrivalDepartureStaticRepository;
import com.guest.app.service.dto.ArrivalDepartureStaticDTO;
import com.guest.app.service.mapper.ArrivalDepartureStaticMapper;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.guest.app.domain.ArrivalDepartureStatic}.
 */
@Service
public class ArrivalDepartureStaticService {

    private final Logger log = LoggerFactory.getLogger(ArrivalDepartureStaticService.class);

    private final ArrivalDepartureStaticRepository arrivalDepartureStaticRepository;

    private final ArrivalDepartureStaticMapper arrivalDepartureStaticMapper;

    public ArrivalDepartureStaticService(
        ArrivalDepartureStaticRepository arrivalDepartureStaticRepository,
        ArrivalDepartureStaticMapper arrivalDepartureStaticMapper
    ) {
        this.arrivalDepartureStaticRepository = arrivalDepartureStaticRepository;
        this.arrivalDepartureStaticMapper = arrivalDepartureStaticMapper;
    }

    public ArrivalDepartureStaticDTO save(ArrivalDepartureStaticDTO arrivalDepartureStaticDTO) {
        log.debug("Request to save ArrivalDepartureStatic : {}", arrivalDepartureStaticDTO);
        ArrivalDepartureStatic arrivalDepartureStatic = arrivalDepartureStaticMapper.toEntity(arrivalDepartureStaticDTO);
        arrivalDepartureStatic = arrivalDepartureStaticRepository.save(arrivalDepartureStatic);
        return arrivalDepartureStaticMapper.toDto(arrivalDepartureStatic);
    }

    public ArrivalDepartureStaticDTO update(ArrivalDepartureStaticDTO arrivalDepartureStaticDTO) {
        log.debug("Request to update ArrivalDepartureStatic : {}", arrivalDepartureStaticDTO);
        ArrivalDepartureStatic arrivalDepartureStatic = arrivalDepartureStaticMapper.toEntity(arrivalDepartureStaticDTO);
        arrivalDepartureStatic = arrivalDepartureStaticRepository.save(arrivalDepartureStatic);
        return arrivalDepartureStaticMapper.toDto(arrivalDepartureStatic);
    }

    public Optional<ArrivalDepartureStaticDTO> partialUpdate(ArrivalDepartureStaticDTO arrivalDepartureStaticDTO) {
        log.debug("Request to partially update ArrivalDepartureStatic : {}", arrivalDepartureStaticDTO);

        return arrivalDepartureStaticRepository
            .findById(arrivalDepartureStaticDTO.getId())
            .map(existingArrivalDepartureStatic -> {
                arrivalDepartureStaticMapper.partialUpdate(existingArrivalDepartureStatic, arrivalDepartureStaticDTO);

                return existingArrivalDepartureStatic;
            })
            .map(arrivalDepartureStaticRepository::save)
            .map(arrivalDepartureStaticMapper::toDto);
    }

    public Page<ArrivalDepartureStaticDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArrivalDepartureStatics");
        return arrivalDepartureStaticRepository.findAll(pageable).map(arrivalDepartureStaticMapper::toDto);
    }

    public Optional<ArrivalDepartureStaticDTO> findOne(String id) {
        log.debug("Request to get ArrivalDepartureStatic : {}", id);
        return arrivalDepartureStaticRepository.findById(id).map(arrivalDepartureStaticMapper::toDto);
    }

    public Optional<ArrivalDepartureStaticDTO> findOneStartDate(Instant startDate) {
        log.debug("Request to get ArrivalDepartureStatic : {}", startDate);
        return arrivalDepartureStaticRepository.getOneStartDate(startDate.toString()).map(arrivalDepartureStaticMapper::toDto);
    }

    public Optional<ArrivalDepartureStaticDTO> findOneEndDate(Instant endDate) {
        log.debug("Request to get ArrivalDepartureStatic : {}", endDate);
        return arrivalDepartureStaticRepository.getOneStartDate(endDate.toString()).map(arrivalDepartureStaticMapper::toDto);
    }

    public void delete(String id) {
        log.debug("Request to delete ArrivalDepartureStatic : {}", id);
        arrivalDepartureStaticRepository.deleteById(id);
    }
}
