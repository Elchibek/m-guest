package com.guest.app.service.mapper;

import com.guest.app.domain.GuestBlock;
import com.guest.app.service.dto.GuestBlockDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GuestBlock} and its DTO {@link GuestBlockDTO}.
 */
@Mapper(componentModel = "spring")
public interface GuestBlockMapper extends EntityMapper<GuestBlockDTO, GuestBlock> {}
