package com.guest.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Floor.
 */
@Document(collection = "floor")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Floor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @NotNull
    @Field("num_floor")
    private Integer numFloor;

    @Field("background_color")
    private String backgroundColor;

    @DBRef
    @Field("entrance")
    @JsonIgnoreProperties(value = { "guestBlock" }, allowSetters = true)
    private Entrance entrance;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Floor id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Floor name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumFloor() {
        return this.numFloor;
    }

    public Floor numFloor(Integer numFloor) {
        this.setNumFloor(numFloor);
        return this;
    }

    public void setNumFloor(Integer numFloor) {
        this.numFloor = numFloor;
    }

    public String getBackgroundColor() {
        return this.backgroundColor;
    }

    public Floor backgroundColor(String backgroundColor) {
        this.setBackgroundColor(backgroundColor);
        return this;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Entrance getEntrance() {
        return this.entrance;
    }

    public void setEntrance(Entrance entrance) {
        this.entrance = entrance;
    }

    public Floor entrance(Entrance entrance) {
        this.setEntrance(entrance);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Floor)) {
            return false;
        }
        return getId() != null && getId().equals(((Floor) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Floor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", numFloor=" + getNumFloor() +
            ", backgroundColor='" + getBackgroundColor() + "'" +
            "}";
    }
}
