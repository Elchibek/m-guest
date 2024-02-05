package com.guest.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.guest.app.domain.GuestStatic} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GuestStaticDTO implements Serializable {

    private String id;

    private Boolean isArchive;

    @NotNull
    private Integer countPerson;

    private Integer toDayDeparture;

    private Integer toMorrowDeparture;

    private Integer totalPrice;

    private Integer totalDidntPay;

    private Integer numOfApartments;

    private Integer affordableApartments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsArchive() {
        return isArchive;
    }

    public void setIsArchive(Boolean isArchive) {
        this.isArchive = isArchive;
    }

    public Integer getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(Integer countPerson) {
        this.countPerson = countPerson;
    }

    public Integer getToDayDeparture() {
        return toDayDeparture;
    }

    public void setToDayDeparture(Integer toDayDeparture) {
        this.toDayDeparture = toDayDeparture;
    }

    public Integer getToMorrowDeparture() {
        return toMorrowDeparture;
    }

    public void setToMorrowDeparture(Integer toMorrowDeparture) {
        this.toMorrowDeparture = toMorrowDeparture;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalDidntPay() {
        return totalDidntPay;
    }

    public void setTotalDidntPay(Integer totalDidntPay) {
        this.totalDidntPay = totalDidntPay;
    }

    public Integer getNumOfApartments() {
        return numOfApartments;
    }

    public void setNumOfApartments(Integer numOfApartments) {
        this.numOfApartments = numOfApartments;
    }

    public Integer getAffordableApartments() {
        return affordableApartments;
    }

    public void setAffordableApartments(Integer affordableApartments) {
        this.affordableApartments = affordableApartments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuestStaticDTO)) {
            return false;
        }

        GuestStaticDTO guestStaticDTO = (GuestStaticDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, guestStaticDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuestStaticDTO{" +
            "id='" + getId() + "'" +
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
