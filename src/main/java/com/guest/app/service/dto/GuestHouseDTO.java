package com.guest.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.guest.app.domain.GuestHouse} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GuestHouseDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private Integer numHouse;

    private Boolean isEmpty;

    private Integer countPerson;

    private String backgroundColor;

    private FloorDTO floor;

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

    public Integer getNumHouse() {
        return numHouse;
    }

    public void setNumHouse(Integer numHouse) {
        this.numHouse = numHouse;
    }

    public Boolean getIsEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public Integer getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(Integer countPerson) {
        this.countPerson = countPerson;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public FloorDTO getFloor() {
        return floor;
    }

    public void setFloor(FloorDTO floor) {
        this.floor = floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuestHouseDTO)) {
            return false;
        }

        GuestHouseDTO guestHouseDTO = (GuestHouseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, guestHouseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuestHouseDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", numHouse='" + getNumHouse() + "'" +
            ", isEmpty='" + getIsEmpty() + "'" +
            ", countPerson=" + getCountPerson() +
            ", backgroundColor='" + getBackgroundColor() + "'" +
            ", floor=" + getFloor() +
            "}";
    }
}
