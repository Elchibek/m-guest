package com.guest.app.service.mapper;

import com.guest.app.domain.ArrivalDepartureStatic;
import com.guest.app.service.dto.ArrivalDepartureStaticDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArrivalDepartureStatic} and its DTO {@link ArrivalDepartureStaticDTO}.
 */
@Mapper(componentModel = "spring")
public interface ArrivalDepartureStaticMapper extends EntityMapper<ArrivalDepartureStaticDTO, ArrivalDepartureStatic> {}
