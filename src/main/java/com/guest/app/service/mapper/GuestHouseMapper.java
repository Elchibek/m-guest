package com.guest.app.service.mapper;

import com.guest.app.domain.Floor;
import com.guest.app.domain.GuestHouse;
import com.guest.app.service.dto.FloorDTO;
import com.guest.app.service.dto.GuestHouseDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GuestHouse} and its DTO {@link GuestHouseDTO}.
 */
@Mapper(componentModel = "spring")
public interface GuestHouseMapper extends EntityMapper<GuestHouseDTO, GuestHouse> {
    @Mapping(target = "floor", source = "floor", qualifiedByName = "floorId")
    GuestHouseDTO toDto(GuestHouse s);

    @Named("floorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FloorDTO toDtoFloorId(Floor floor);
}
