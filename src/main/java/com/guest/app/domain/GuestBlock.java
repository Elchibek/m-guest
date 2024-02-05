package com.guest.app.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A GuestBlock.
 */
@Document(collection = "guest_block")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GuestBlock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("name_entrance")
    private String nameEntrance;

    @NotNull
    @Field("num_entrance")
    private Integer numEntrance;

    @Field("name_floor")
    private String nameFloor;

    @NotNull
    @Field("num_floor")
    private Integer numFloor;

    @Field("name_house")
    private String nameHouse;

    @NotNull
    @Field("num_house")
    private Integer numHouse;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public GuestBlock id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public GuestBlock name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEntrance() {
        return this.nameEntrance;
    }

    public GuestBlock nameEntrance(String nameEntrance) {
        this.setNameEntrance(nameEntrance);
        return this;
    }

    public void setNameEntrance(String nameEntrance) {
        this.nameEntrance = nameEntrance;
    }

    public Integer getNumEntrance() {
        return this.numEntrance;
    }

    public GuestBlock numEntrance(Integer numEntrance) {
        this.setNumEntrance(numEntrance);
        return this;
    }

    public void setNumEntrance(Integer numEntrance) {
        this.numEntrance = numEntrance;
    }

    public String getNameFloor() {
        return this.nameFloor;
    }

    public GuestBlock nameFloor(String nameFloor) {
        this.setNameFloor(nameFloor);
        return this;
    }

    public void setNameFloor(String nameFloor) {
        this.nameFloor = nameFloor;
    }

    public Integer getNumFloor() {
        return this.numFloor;
    }

    public GuestBlock numFloor(Integer numFloor) {
        this.setNumFloor(numFloor);
        return this;
    }

    public void setNumFloor(Integer numFloor) {
        this.numFloor = numFloor;
    }

    public String getNameHouse() {
        return this.nameHouse;
    }

    public GuestBlock nameHouse(String nameHouse) {
        this.setNameHouse(nameHouse);
        return this;
    }

    public void setNameHouse(String nameHouse) {
        this.nameHouse = nameHouse;
    }

    public Integer getNumHouse() {
        return this.numHouse;
    }

    public GuestBlock numHouse(Integer numHouse) {
        this.setNumHouse(numHouse);
        return this;
    }

    public void setNumHouse(Integer numHouse) {
        this.numHouse = numHouse;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuestBlock)) {
            return false;
        }
        return getId() != null && getId().equals(((GuestBlock) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuestBlock{" +
            "id=" + getId() +
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
