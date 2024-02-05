package com.guest.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.guest.app.domain.Floor} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FloorDTO implements Serializable {

    private String id;

    private String name;

    @NotNull
    private Integer numFloor;

    private String backgroundColor;

    private EntranceDTO entrance;

    List<GuestHouseDTO> gDtos;

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

    public Integer getNumFloor() {
        return numFloor;
    }

    public void setNumFloor(Integer numFloor) {
        this.numFloor = numFloor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public EntranceDTO getEntrance() {
        return entrance;
    }

    public void setEntrance(EntranceDTO entrance) {
        this.entrance = entrance;
    }

    public List<GuestHouseDTO> getgDtos() {
        return gDtos;
    }

    public void setgDtos(List<GuestHouseDTO> gDtos) {
        this.gDtos = gDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FloorDTO)) {
            return false;
        }

        FloorDTO floorDTO = (FloorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, floorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FloorDTO [id=" + id + ", name=" + name + ", numFloor=" + numFloor + ", backgroundColor="
                + backgroundColor + ", entrance=" + entrance + ", gDtos=" + gDtos + "]";
    }
}
