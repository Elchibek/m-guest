package com.guest.app.service.mapper;

import com.guest.app.domain.Guest;
import com.guest.app.domain.PutAway;
import com.guest.app.service.dto.GuestDTO;
import com.guest.app.service.dto.PutAwayDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PutAway} and its DTO {@link PutAwayDTO}.
 */
@Mapper(componentModel = "spring")
public interface PutAwayMapper extends EntityMapper<PutAwayDTO, PutAway> {
    @Mapping(target = "guest", source = "guest", qualifiedByName = "guestId")
    PutAwayDTO toDto(PutAway s);

    @Named("guestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GuestDTO toDtoGuestId(Guest guest);
}
