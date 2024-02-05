package com.guest.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A DidntPay.
 */
@Document(collection = "didnt_pay")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DidntPay implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("count_person")
    private Integer countPerson;

    @Field("paid")
    private Integer paid;

    @Field("explanation")
    private String explanation;

    @DBRef
    @Field("guest")
    @JsonIgnoreProperties(
        value = { "restOfTheDay", "putAways", "guestContacts", "didntPays", "guestBlock", "entrance", "floor", "guestHouse", "guestFrom" },
        allowSetters = true
    )
    private Guest guest;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public DidntPay id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCountPerson() {
        return this.countPerson;
    }

    public DidntPay countPerson(Integer countPerson) {
        this.setCountPerson(countPerson);
        return this;
    }

    public void setCountPerson(Integer countPerson) {
        this.countPerson = countPerson;
    }

    public Integer getPaid() {
        return this.paid;
    }

    public DidntPay paid(Integer paid) {
        this.setPaid(paid);
        return this;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public DidntPay explanation(String explanation) {
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

    public DidntPay guest(Guest guest) {
        this.setGuest(guest);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DidntPay)) {
            return false;
        }
        return getId() != null && getId().equals(((DidntPay) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DidntPay{" +
            "id=" + getId() +
            ", countPerson=" + getCountPerson() +
            ", paid=" + getPaid() +
            ", explanation='" + getExplanation() + "'" +
            "}";
    }
}
