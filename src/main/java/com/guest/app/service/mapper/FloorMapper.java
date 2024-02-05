package com.guest.app.service.mapper;

import com.guest.app.domain.Entrance;
import com.guest.app.domain.Floor;
import com.guest.app.service.dto.EntranceDTO;
import com.guest.app.service.dto.FloorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Floor} and its DTO {@link FloorDTO}.
 */
@Mapper(componentModel = "spring")
public interface FloorMapper extends EntityMapper<FloorDTO, Floor> {
    @Mapping(target = "entrance", source = "entrance", qualifiedByName = "entranceId")
    FloorDTO toDto(Floor s);

    @Named("entranceId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EntranceDTO toDtoEntranceId(Entrance entrance);
}
