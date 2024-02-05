package com.guest.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.guest.app.security.SecurityUtils;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.TextScore;

/**
 * A Guest.
 */
@Document(collection = "guest")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Guest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    // auto-index-creation: true мунусуз иштебейт
    @TextIndexed
    @Field("name")
    private String name;

    // auto-index-creation: true мунусуз иштебейт
    @TextIndexed
    @Field("guest_institution")
    private String guestInstitution;

    // auto-index-creation: true мунусуз иштебейт
    @TextIndexed
    @Field("responsible")
    private String responsible;

    @Field("is_archive")
    private Boolean isArchive = false;

    @Field("start_date")
    private Instant startDate;

    @Field("end_date")
    private Instant endDate;

    @Field("will_stay")
    private Integer willStay;

    @Field("num")
    private Integer num;

    @Field("is_departure")
    private Boolean isDeparture;

    @Field("count_didnt_pay")
    private Integer countDidntPay = 0;

    @Field("didnt_pay")
    private Integer didntPay = 0;

    @Field("is_paid")
    private Boolean isPaid = true;

    @Field("count_person")
    private Integer countPerson;

    @TextIndexed
    @Field("explanation")
    private String explanation;

    @Field("price")
    private Integer price;

    @Field("total_price")
    private Integer totalPrice;

    @Field("is_deleted")
    private Boolean isDeleted = false;

    @Field("is_update")
    private Boolean isUpdate = false;

    @Field("is_change")
    private Boolean isChange = false;

    @Field("parent_id")
    private String parentId;

    @LastModifiedDate
    @Field("last_modified_date")
    private Instant lastModifiedDate = Instant.now();

    @TextScore
    Float score;

    @DBRef
    @Field("putAway")
    @JsonIgnoreProperties(value = { "guest" }, allowSetters = true)
    private Set<PutAway> putAways = new HashSet<>();

    @DBRef
    @Field("guestContact")
    @JsonIgnoreProperties(value = { "guest" }, allowSetters = true)
    private Set<GuestContact> guestContacts = new HashSet<>();

    @DBRef
    @Field("user")
    @JsonIgnoreProperties(
        value = { "email", "login", "activated", "createdBy", "createdDate", "lastModifiedBy", "lastModifiedDate" },
        allowSetters = true
    )
    private User user;

    @DBRef
    @Field("guestBlock")
    private GuestBlock guestBlock;

    @DBRef
    @Field("entrance")
    @JsonIgnoreProperties(value = { "guestBlock" }, allowSetters = true)
    private Entrance entrance;

    @DBRef
    @Field("floor")
    @JsonIgnoreProperties(value = { "entrance" }, allowSetters = true)
    private Floor floor;

    @DBRef
    @Field("guestHouse")
    @JsonIgnoreProperties(value = { "floor" }, allowSetters = true)
    private GuestHouse guestHouse;

    @DBRef
    @Field("guestFrom")
    private GuestFrom guestFrom;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Guest id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Guest name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuestInstitution() {
        return this.guestInstitution;
    }

    public Guest guestInstitution(String guestInstitution) {
        this.setGuestInstitution(guestInstitution);
        return this;
    }

    public void setGuestInstitution(String guestInstitution) {
        this.guestInstitution = guestInstitution;
    }

    public String getResponsible() {
        return this.responsible;
    }

    public Guest responsible(String responsible) {
        this.setResponsible(responsible);
        return this;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public Boolean getIsArchive() {
        return this.isArchive;
    }

    public Guest isArchive(Boolean isArchive) {
        this.setIsArchive(isArchive);
        return this;
    }

    public void setIsArchive(Boolean isArchive) {
        this.isArchive = isArchive;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Guest startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return this.endDate;
    }

    public Guest endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getWillStay() {
        return this.willStay;
    }

    public Guest willStay(Integer willStay) {
        this.setWillStay(willStay);
        return this;
    }

    public void setWillStay(Integer willStay) {
        this.willStay = willStay;
    }

    public Integer getNum() {
        return this.num;
    }

    public Guest num(Integer num) {
        this.setNum(num);
        return this;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Boolean getIsDeparture() {
        return this.isDeparture;
    }

    public Guest isDeparture(Boolean isDeparture) {
        this.setIsDeparture(isDeparture);
        return this;
    }

    public void setIsDeparture(Boolean isDeparture) {
        this.isDeparture = isDeparture;
    }

    public Integer getCountDidntPay() {
        return this.countDidntPay;
    }

    public Guest countDidntPay(Integer countDidntPay) {
        this.setCountDidntPay(countDidntPay);
        return this;
    }

    public void setCountDidntPay(Integer countDidntPay) {
        this.countDidntPay = countDidntPay;
    }

    public Integer getDidntPay() {
        return this.didntPay;
    }

    public Guest didntPay(Integer didntPay) {
        this.setDidntPay(didntPay);
        return this;
    }

    public void setDidntPay(Integer didntPay) {
        this.didntPay = didntPay;
    }

    public Boolean getIsPaid() {
        return this.isPaid;
    }

    public Guest isPaid(Boolean isPaid) {
        this.setIsPaid(isPaid);
        return this;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Integer getCountPerson() {
        return this.countPerson;
    }

    public Guest countPerson(Integer countPerson) {
        this.setCountPerson(countPerson);
        return this;
    }

    public void setCountPerson(Integer countPerson) {
        this.countPerson = countPerson;
    }

    public String getExplanation() {
        return this.explanation;
    }

    public Guest explanation(String explanation) {
        this.setExplanation(explanation);
        return this;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Integer getPrice() {
        return this.price;
    }

    public Guest price(Integer price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotalPrice() {
        return this.totalPrice;
    }

    public Guest totalPrice(Integer totalPrice) {
        this.setTotalPrice(totalPrice);
        return this;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Guest isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsUpdate() {
        return this.isUpdate;
    }

    public Guest isUpdate(Boolean isUpdate) {
        this.setIsUpdate(isUpdate);
        return this;
    }

    public void setIsUpdate(Boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Boolean getIsChange() {
        return this.isChange;
    }

    public Guest isChange(Boolean isChange) {
        this.setIsChange(isChange);
        return this;
    }

    public void setIsChange(Boolean isChange) {
        this.isChange = isChange;
    }

    public String getParentId() {
        return this.parentId;
    }

    public Guest parentId(String parentId) {
        this.setParentId(parentId);
        return this;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<PutAway> getPutAways() {
        return this.putAways;
    }

    public void setPutAways(Set<PutAway> putAways) {
        if (this.putAways != null) {
            this.putAways.forEach(i -> i.setGuest(null));
        }
        if (putAways != null) {
            putAways.forEach(i -> i.setGuest(this));
        }
        this.putAways = putAways;
    }

    public Guest putAways(Set<PutAway> putAways) {
        this.setPutAways(putAways);
        return this;
    }

    public Guest addPutAway(PutAway putAway) {
        this.putAways.add(putAway);
        putAway.setGuest(this);
        return this;
    }

    public Guest removePutAway(PutAway putAway) {
        this.putAways.remove(putAway);
        putAway.setGuest(null);
        return this;
    }

    // --------------------------
    public Set<GuestContact> getGuestContacts() {
        return this.guestContacts;
    }

    public void setGuestContacts(Set<GuestContact> guestContacts) {
        if (this.guestContacts != null) {
            this.guestContacts.forEach(i -> i.setGuest(null));
        }
        if (guestContacts != null) {
            guestContacts.forEach(i -> i.setGuest(this));
        }
        this.guestContacts = guestContacts;
    }

    public Guest putGuestContacts(Set<GuestContact> guestContacts) {
        this.setGuestContacts(guestContacts);
        return this;
    }

    public Guest addGuestContact(GuestContact guestContact) {
        this.guestContacts.add(guestContact);
        guestContact.setGuest(this);
        return this;
    }

    public Guest removeGuestContact(GuestContact guestContact) {
        this.guestContacts.remove(guestContact);
        guestContact.setGuest(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Guest user(User user) {
        this.setUser(user);
        return this;
    }

    public GuestBlock getGuestBlock() {
        return this.guestBlock;
    }

    public void setGuestBlock(GuestBlock guestBlock) {
        this.guestBlock = guestBlock;
    }

    public Guest guestBlock(GuestBlock guestBlock) {
        this.setGuestBlock(guestBlock);
        return this;
    }

    public Entrance getEntrance() {
        return this.entrance;
    }

    public void setEntrance(Entrance entrance) {
        this.entrance = entrance;
    }

    public Guest entrance(Entrance entrance) {
        this.setEntrance(entrance);
        return this;
    }

    public Floor getFloor() {
        return this.floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Guest floor(Floor floor) {
        this.setFloor(floor);
        return this;
    }

    public GuestHouse getGuestHouse() {
        return this.guestHouse;
    }

    public void setGuestHouse(GuestHouse guestHouse) {
        this.guestHouse = guestHouse;
    }

    public Guest guestHouse(GuestHouse guestHouse) {
        this.setGuestHouse(guestHouse);
        return this;
    }

    public GuestFrom getGuestFrom() {
        return this.guestFrom;
    }

    public void setGuestFrom(GuestFrom guestFrom) {
        this.guestFrom = guestFrom;
    }

    public Guest guestFrom(GuestFrom guestFrom) {
        this.setGuestFrom(guestFrom);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Guest)) {
            return false;
        }
        return getId() != null && getId().equals(((Guest) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "Guest [id=" +
            getId() +
            ", name=" +
            getName() +
            ", guestInstitution=" +
            getGuestInstitution() +
            ", responsible=" +
            getResponsible() +
            ", isArchive=" +
            getIsArchive() +
            ", startDate=" +
            getStartDate() +
            ", endDate=" +
            getEndDate() +
            ", willStay=" +
            getWillStay() +
            ", num=" +
            getNum() +
            ", isDeparture=" +
            getIsDeparture() +
            ", countDidntPay=" +
            getCountDidntPay() +
            ", paid=" +
            getIsPaid() +
            ", countPerson=" +
            getCountPerson() +
            ", explanation=" +
            getExplanation() +
            ", price=" +
            getPrice() +
            ", totalPrice=" +
            getTotalPrice() +
            ", isDeleted=" +
            getIsDeleted() +
            ", isUpdate=" +
            getIsUpdate() +
            ", isChange=" +
            getIsChange() +
            ", parentId=" +
            getParentId() +
            ", lastModifiedDate=" +
            getLastModifiedDate() +
            ", putAways=" +
            getPutAways() +
            ", guestContacts=" +
            getGuestContacts() +
            ", user=" +
            getUser() +
            ", guestBlock=" +
            getGuestBlock() +
            ", entrance=" +
            getEntrance() +
            ", floor=" +
            getFloor() +
            ", guestHouse=" +
            getGuestHouse() +
            ", guestFrom=" +
            getGuestFrom() +
            "]"
        );
    }
    // prettier-ignore

}
