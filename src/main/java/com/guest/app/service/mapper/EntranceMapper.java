package com.guest.app.service.mapper;

import com.guest.app.domain.Entrance;
import com.guest.app.domain.GuestBlock;
import com.guest.app.service.dto.EntranceDTO;
import com.guest.app.service.dto.GuestBlockDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entrance} and its DTO {@link EntranceDTO}.
 */
@Mapper(componentModel = "spring")
public interface EntranceMapper extends EntityMapper<EntranceDTO, Entrance> {
    @Mapping(target = "guestBlock", source = "guestBlock", qualifiedByName = "guestBlock")
    EntranceDTO toDto(Entrance s);

    @Named("guestBlock")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GuestBlockDTO toDtoGuestBlockId(GuestBlock guestBlock);
}
