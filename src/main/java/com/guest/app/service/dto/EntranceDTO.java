package com.guest.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.guest.app.domain.Entrance} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EntranceDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private Integer numEntrance;

    private List<FloorDTO> fDtos;

    private GuestBlockDTO guestBlock;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumEntrance() {
        return numEntrance;
    }

    public void setNumEntrance(Integer numEntrance) {
        this.numEntrance = numEntrance;
    }

    public GuestBlockDTO getGuestBlock() {
        return guestBlock;
    }

    public void setGuestBlock(GuestBlockDTO guestBlock) {
        this.guestBlock = guestBlock;
    }

    public List<FloorDTO> getfDtos() {
        return fDtos;
    }

    public void setfDtos(List<FloorDTO> fDtos) {
        this.fDtos = fDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntranceDTO)) {
            return false;
        }

        EntranceDTO entranceDTO = (EntranceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entranceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EntranceDTO [id=" + id + ", name=" + name + ", numEntrance=" + numEntrance + ", fDtos=" + fDtos
                 + ", guestBlock=" + guestBlock + "]";
    }
}
