package com.guest.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A GuestHouse.
 */
@Document(collection = "guest_house")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GuestHouse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("num_house")
    private Integer numHouse;

    @Field("is_empty")
    private Boolean isEmpty;

    @Field("count_person")
    private Integer countPerson;

    @Field("background_color")
    private String backgroundColor;

    @Field("guest_block_id")
    private String guestBlockId;

    @DBRef
    @Field("floor")
    @JsonIgnoreProperties(value = { "entrance" }, allowSetters = true)
    private Floor floor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public GuestHouse id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public GuestHouse name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumHouse() {
        return this.numHouse;
    }

    public GuestHouse numHouse(Integer numHouse) {
        this.setNumHouse(numHouse);
        return this;
    }

    public void setNumHouse(Integer numHouse) {
        this.numHouse = numHouse;
    }

    public Boolean getIsEmpty() {
        return this.isEmpty;
    }

    public GuestHouse isEmpty(Boolean isEmpty) {
        this.setIsEmpty(isEmpty);
        return this;
    }

    public void setIsEmpty(Boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public Integer getCountPerson() {
        return this.countPerson;
    }

    public GuestHouse countPerson(Integer countPerson) {
        this.setCountPerson(countPerson);
        return this;
    }

    public void setCountPerson(Integer countPerson) {
        this.countPerson = countPerson;
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public GuestHouse backgroundColor(String backgroundColor) {
        this.setBackgroundColor(backgroundColor);
        return this;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    // ------------------------
    public String getGuestBlockId() {
        return this.guestBlockId;
    }

    public GuestHouse guestBlockId(String guestBlockId) {
        this.setGuestBlockId(guestBlockId);
        return this;
    }

    public void setGuestBlockId(String guestBlockId) {
        this.guestBlockId = guestBlockId;
    }

    public Floor getFloor() {
        return this.floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public GuestHouse floor(Floor floor) {
        this.setFloor(floor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuestHouse)) {
            return false;
        }
        return getId() != null && getId().equals(((GuestHouse) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuestHouse{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", numHouse='" + getNumHouse() + "'" +
            ", isEmpty='" + getIsEmpty() + "'" +
            ", countPerson=" + getCountPerson() +
            ", backgroundColor='" + getBackgroundColor() + "'" +
            ", guestBlockId='" + getGuestBlockId() + "'" +
            "}";
    }
}
