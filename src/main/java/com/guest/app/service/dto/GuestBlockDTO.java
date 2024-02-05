package com.guest.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.guest.app.domain.GuestBlock} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GuestBlockDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private String nameEntrance;

    @NotNull
    private Integer numEntrance;

    private String nameFloor;

    @NotNull
    private Integer numFloor;

    private String nameHouse;

    @NotNull
    private Integer numHouse;

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

    public String getNameEntrance() {
        return nameEntrance;
    }

    public void setNameEntrance(String nameEntrance) {
        this.nameEntrance = nameEntrance;
    }

    public Integer getNumEntrance() {
        return numEntrance;
    }

    public void setNumEntrance(Integer numEntrance) {
        this.numEntrance = numEntrance;
    }

    public String getNameFloor() {
        return nameFloor;
    }

    public void setNameFloor(String nameFloor) {
        this.nameFloor = nameFloor;
    }

    public Integer getNumFloor() {
        return numFloor;
    }

    public void setNumFloor(Integer numFloor) {
        this.numFloor = numFloor;
    }

    public String getNameHouse() {
        return nameHouse;
    }

    public void setNameHouse(String nameHouse) {
        this.nameHouse = nameHouse;
    }

    public Integer getNumHouse() {
        return numHouse;
    }

    public void setNumHouse(Integer numHouse) {
        this.numHouse = numHouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuestBlockDTO)) {
            return false;
        }

        GuestBlockDTO guestBlockDTO = (GuestBlockDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, guestBlockDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuestBlockDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", nameEntrance='" + getNameEntrance() + "'" +
            ", numEntrance=" + getNumEntrance() +
            ", nameFloor='" + getNameFloor() + "'" +
            ", numFloor=" + getNumFloor() +
            ", nameHouse='" + getNameHouse() + "'" +
            ", numHouse=" + getNumHouse() +
            "}";
    }
}
