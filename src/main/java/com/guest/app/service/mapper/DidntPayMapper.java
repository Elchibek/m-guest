package com.guest.app.service.mapper;

import com.guest.app.domain.DidntPay;
import com.guest.app.domain.Guest;
import com.guest.app.service.dto.DidntPayDTO;
import com.guest.app.service.dto.GuestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DidntPay} and its DTO {@link DidntPayDTO}.
 */
@Mapper(componentModel = "spring")
public interface DidntPayMapper extends EntityMapper<DidntPayDTO, DidntPay> {
    @Mapping(target = "guest", source = "guest", qualifiedByName = "guestId")
    DidntPayDTO toDto(DidntPay s);

    @Named("guestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GuestDTO toDtoGuestId(Guest guest);
}
