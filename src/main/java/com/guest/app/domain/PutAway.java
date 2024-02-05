package com.guest.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A PutAway.
 */
@Document(collection = "put_away")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PutAway implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("count_person")
    private Integer countPerson;

    @Field("when_left")
    private Instant whenLeft;

    @Field("explanation")
    private String explanation;

    @DBRef
    @Field("guest")
    @JsonIgnoreProperties(
        value = { "restOfTheDay", "putAways", "guestBlock", "entrance", "floor", "guestHouse", "guestFrom" },
        allowSetters = true
    )
    private Guest guest;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public PutAway id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCountPerson() {
        return this.countPerson;
    }

    public PutAway countPerson(Integer countPerson) {
        this.setCountPerson(countPerson);
        return this;
    }

    public void setCountPerson(Integer countPerson) {
        this.countPerson = countPerson;
    }

    public Instant getWhenLeft() {
        return this.whenLeft;
    }

    public PutAway whenLeft(Instant whenLeft) {
        this.setWhenLeft(whenLeft);
        return this;
    }

    public void setWhenLeft(Instant whenLeft) {
        this.whenLeft = whenLeft;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public PutAway explanation(String explanation) {
        this.setExplanation(explanation);
        return this;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Guest getGuest() {
        return this.guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public PutAway guest(Guest guest) {
        this.setGuest(guest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PutAway)) {
            return false;
        }
        return getId() != null && getId().equals(((PutAway) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PutAway{" +
            "id=" + getId() +
            ", countPerson=" + getCountPerson() +
            ", whenLeft='" + getWhenLeft() + "'" +
            ", explanation='" + getExplanation() + "'" +
            "}";
    }
}
