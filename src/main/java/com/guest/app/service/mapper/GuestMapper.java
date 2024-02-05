package com.guest.app.service.mapper;

import com.guest.app.domain.Entrance;
import com.guest.app.domain.Floor;
import com.guest.app.domain.Guest;
import com.guest.app.domain.GuestBlock;
import com.guest.app.domain.GuestFrom;
import com.guest.app.domain.GuestHouse;
import com.guest.app.domain.User;
import com.guest.app.service.dto.EntranceDTO;
import com.guest.app.service.dto.FloorDTO;
import com.guest.app.service.dto.GuestBlockDTO;
import com.guest.app.service.dto.GuestDTO;
import com.guest.app.service.dto.GuestFromDTO;
import com.guest.app.service.dto.GuestHouseDTO;
import com.guest.app.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Guest} and its DTO {@link GuestDTO}.
 */
@Mapper(componentModel = "spring")
public interface GuestMapper extends EntityMapper<GuestDTO, Guest> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "guestBlock", source = "guestBlock", qualifiedByName = "guestBlockId")
    @Mapping(target = "entrance", source = "entrance", qualifiedByName = "entranceId")
    @Mapping(target = "floor", source = "floor", qualifiedByName = "floorId")
    @Mapping(target = "guestHouse", source = "guestHouse", qualifiedByName = "guestHouseId")
    @Mapping(target = "guestFrom", source = "guestFrom", qualifiedByName = "guestFromId")
    GuestDTO toDto(Guest s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    UserDTO toDtoUserId(User user);

    @Named("guestBlockId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GuestBlockDTO toDtoGuestBlockId(GuestBlock guestBlock);

    @Named("entranceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "numEntrance", source = "numEntrance")
    EntranceDTO toDtoEntranceId(Entrance entrance);

    @Named("floorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "numFloor", source = "numFloor")
    FloorDTO toDtoFloorId(Floor floor);

    @Named("guestHouseId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "countPerson", source = "countPerson")
    GuestHouseDTO toDtoGuestHouseId(GuestHouse guestHouse);

    @Named("guestFromId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    GuestFromDTO toDtoGuestFromId(GuestFrom guestFrom);
}
