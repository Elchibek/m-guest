package com.guest.app.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Entrance.
 */
@Document(collection = "entrance")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Entrance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("num_entrance")
    private Integer numEntrance;

    @DBRef
    @Field("guestBlock")
    private GuestBlock guestBlock;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Entrance id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Entrance name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumEntrance() {
        return this.numEntrance;
    }

    public Entrance numEntrance(Integer numEntrance) {
        this.setNumEntrance(numEntrance);
        return this;
    }

    public void setNumEntrance(Integer numEntrance) {
        this.numEntrance = numEntrance;
    }

    public GuestBlock getGuestBlock() {
        return this.guestBlock;
    }

    public void setGuestBlock(GuestBlock guestBlock) {
        this.guestBlock = guestBlock;
    }

    public Entrance guestBlock(GuestBlock guestBlock) {
        this.setGuestBlock(guestBlock);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entrance)) {
            return false;
        }
        return getId() != null && getId().equals(((Entrance) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entrance{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", numEntrance=" + getNumEntrance() +
            "}";
    }
}
