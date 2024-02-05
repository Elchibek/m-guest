package com.guest.app.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A GuestStatic.
 */
@Document(collection = "guest_static")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GuestStatic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("is_archive")
    private Boolean isArchive;

    @NotNull
    @Field("count_person")
    private Integer countPerson;

    @Field("to_day_departure")
    private Integer toDayDeparture;

    @Field("to_morrow_departure")
    private Integer toMorrowDeparture;

    @Field("total_price")
    private Integer totalPrice;

    @Field("total_didnt_pay")
    private Integer totalDidntPay;

    @Field("num_of_apartments")
    private Integer numOfApartments;

    @Field("affordable_apartments")
    private Integer affordableApartments;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public GuestStatic id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsArchive() {
        return this.isArchive;
    }

    public GuestStatic isArchive(Boolean isArchive) {
        this.setIsArchive(isArchive);
        return this;
    }

    public void setIsArchive(Boolean isArchive) {
        this.isArchive = isArchive;
    }

    public Integer getCountPerson() {
        return this.countPerson;
    }

    public GuestStatic countPerson(Integer countPerson) {
        this.setCountPerson(countPerson);
        return this;
    }

    public void setCountPerson(Integer countPerson) {
        this.countPerson = countPerson;
    }

    public Integer getToDayDeparture() {
        return this.toDayDeparture;
    }

    public GuestStatic toDayDeparture(Integer toDayDeparture) {
        this.setToDayDeparture(toDayDeparture);
        return this;
    }

    public void setToDayDeparture(Integer toDayDeparture) {
        this.toDayDeparture = toDayDeparture;
    }

    public Integer getToMorrowDeparture() {
        return this.toMorrowDeparture;
    }

    public GuestStatic toMorrowDeparture(Integer toMorrowDeparture) {
        this.setToMorrowDeparture(toMorrowDeparture);
        return this;
    }

    public void setToMorrowDeparture(Integer toMorrowDeparture) {
        this.toMorrowDeparture = toMorrowDeparture;
    }

    public Integer getTotalPrice() {
        return this.totalPrice;
    }

    public GuestStatic totalPrice(Integer totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalDidntPay() {
        return this.totalDidntPay;
    }

    public GuestStatic totalDidntPay(Integer totalDidntPay) {
        this.setTotalDidntPay(totalDidntPay);
        return this;
    }

    public void setTotalDidntPay(Integer totalDidntPay) {
        this.totalDidntPay = totalDidntPay;
    }

    public Integer getNumOfApartments() {
        return this.numOfApartments;
    }

    public GuestStatic numOfApartments(Integer numOfApartments) {
        this.setNumOfApartments(numOfApartments);
        return this;
    }

    public void setNumOfApartments(Integer numOfApartments) {
        this.numOfApartments = numOfApartments;
    }

    public Integer getAffordableApartments() {
        return this.affordableApartments;
    }

    public GuestStatic affordableApartments(Integer affordableApartments) {
        this.setAffordableApartments(affordableApartments);
        return this;
    }

    public void setAffordableApartments(Integer affordableApartments) {
        this.affordableApartments = affordableApartments;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuestStatic)) {
            return false;
        }
        return getId() != null && getId().equals(((GuestStatic) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuestStatic{" +
            "id=" + getId() +
            ", isArchive='" + getIsArchive() + "'" +
            ", countPerson=" + getCountPerson() +
            ", toDayDeparture=" + getToDayDeparture() +
            ", toMorrowDeparture=" + getToMorrowDeparture() +
            ", totalPrice=" + getTotalPrice() +
            ", totalDidntPay=" + getTotalDidntPay() +
            ", numOfApartments=" + getNumOfApartments() +
            ", affordableApartments=" + getAffordableApartments() +
            "}";
    }
}
