package com.guest.app.service.mapper;

import com.guest.app.domain.GuestFrom;
import com.guest.app.service.dto.GuestFromDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GuestFrom} and its DTO {@link GuestFromDTO}.
 */
@Mapper(componentModel = "spring")
public interface GuestFromMapper extends EntityMapper<GuestFromDTO, GuestFrom> {}
