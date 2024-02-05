package com.guest.app.service.mapper;

import com.guest.app.domain.GuestStatic;
import com.guest.app.service.dto.GuestStaticDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GuestStatic} and its DTO {@link GuestStaticDTO}.
 */
@Mapper(componentModel = "spring")
public interface GuestStaticMapper extends EntityMapper<GuestStaticDTO, GuestStatic> {}
