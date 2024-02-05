package com.guest.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.guest.app.domain.Guest} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GuestDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private String guestInstitution;

    private String responsible;

    private Boolean isArchive = false;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    private Integer willStay;

    private Integer num;

    private Boolean isDeparture;

    private Integer countDidntPay;

    private Integer didntPay;

    private Boolean isPaid = true;

    @NotNull
    private Integer countPerson;

    private String explanation;

    @NotNull
    private Integer price;

    private Integer totalPrice;

    private Instant lastModifiedDate;

    private UserDTO user;

    @NotNull
    private GuestBlockDTO guestBlock;

    @NotNull
    private EntranceDTO entrance;

    @NotNull
    private FloorDTO floor;

    @NotNull
    private GuestHouseDTO guestHouse;

    @NotNull
    private GuestFromDTO guestFrom;

    private List<GuestContactDTO> contacts;

    // private List<PutAwayDTO> putAways;

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

    public String getGuestInstitution() {
        return guestInstitution;
    }

    public void setGuestInstitution(String guestInstitution) {
        this.guestInstitution = guestInstitution;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public Boolean getIsArchive() {
        return isArchive;
    }

    public void setIsArchive(Boolean isArchive) {
        this.isArchive = isArchive;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getWillStay() {
        return willStay;
    }

    public void setWillStay(Integer willStay) {
        this.willStay = willStay;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Boolean getIsDeparture() {
        return isDeparture;
    }

    public void setIsDeparture(Boolean isDeparture) {
        this.isDeparture = isDeparture;
    }

    public Integer getCountDidntPay() {
        return countDidntPay;
    }

    public void setCountDidntPay(Integer countDidntPay) {
        this.countDidntPay = countDidntPay;
    }

    public Integer getDidntPay() {
        return didntPay;
    }

    public void setDidntPay(Integer didntPay) {
        this.didntPay = didntPay;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Integer getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(Integer countPerson) {
        this.countPerson = countPerson;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public GuestBlockDTO getGuestBlock() {
        return guestBlock;
    }

    public void setGuestBlock(GuestBlockDTO guestBlock) {
        this.guestBlock = guestBlock;
    }

    public EntranceDTO getEntrance() {
        return entrance;
    }

    public void setEntrance(EntranceDTO entrance) {
        this.entrance = entrance;
    }

    public FloorDTO getFloor() {
        return floor;
    }

    public void setFloor(FloorDTO floor) {
        this.floor = floor;
    }

    public GuestHouseDTO getGuestHouse() {
        return guestHouse;
    }

    public void setGuestHouse(GuestHouseDTO guestHouse) {
        this.guestHouse = guestHouse;
    }

    public GuestFromDTO getGuestFrom() {
        return guestFrom;
    }

    public void setGuestFrom(GuestFromDTO guestFrom) {
        this.guestFrom = guestFrom;
    }

    public List<GuestContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<GuestContactDTO> contacts) {
        this.contacts = contacts;
    }

    // public List<PutAwayDTO> getPutAways() {
    //     return putAways;
    // }

    // public void setPutAways(List<PutAwayDTO> putAways) {
    //     this.putAways = putAways;
    // }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuestDTO)) {
            return false;
        }

        GuestDTO guestDTO = (GuestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, guestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuestDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", guestInstitution='" + getGuestInstitution() + "'" +
            ", responsible='" + getResponsible() + "'" +
            ", isArchive='" + getIsArchive() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", countDidntPay=" + getCountDidntPay() +
            ", paid='" + getIsPaid() + "'" +
            ", countPerson=" + getCountPerson() +
            ", explanation='" + getExplanation() + "'" +
            ", price=" + getPrice() +
            ", totalPrice=" + getTotalPrice() +
            ", guestBlock=" + getGuestBlock() +
            ", user=" + getUser() +
            ", entrance=" + getEntrance() +
            ", floor=" + getFloor() +
            ", guestHouse=" + getGuestHouse() +
            ", guestFrom=" + getGuestFrom() +
            ", guestContact=" + getContacts() +
            "}";
    }
}
