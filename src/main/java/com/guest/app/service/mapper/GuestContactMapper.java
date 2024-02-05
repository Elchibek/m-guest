package com.guest.app.service.mapper;

import com.guest.app.domain.Guest;
import com.guest.app.domain.GuestContact;
import com.guest.app.service.dto.GuestContactDTO;
import com.guest.app.service.dto.GuestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GuestContact} and its DTO {@link GuestContactDTO}.
 */
@Mapper(componentModel = "spring")
public interface GuestContactMapper extends EntityMapper<GuestContactDTO, GuestContact> {
    @Mapping(target = "guest", source = "guest", qualifiedByName = "guestId")
    GuestContactDTO toDto(GuestContact s);

    @Named("guestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GuestDTO toDtoGuestId(Guest guest);
}
