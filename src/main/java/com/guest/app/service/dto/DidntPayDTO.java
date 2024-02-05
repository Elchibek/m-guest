package com.guest.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.guest.app.domain.DidntPay} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DidntPayDTO implements Serializable {

    private String id;

    private Integer countPerson;

    private Integer paid;

    private String explanation;

    private Boolean isDelete;

    private GuestDTO guest;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(Integer countPerson) {
        this.countPerson = countPerson;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public GuestDTO getGuest() {
        return guest;
    }

    public void setGuest(GuestDTO guest) {
        this.guest = guest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DidntPayDTO)) {
            return false;
        }

        DidntPayDTO didntPayDTO = (DidntPayDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, didntPayDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DidntPayDTO{" +
            "id='" + getId() + "'" +
            ", countPerson=" + getCountPerson() +
            ", paid=" + getPaid() +
            ", explanation='" + getExplanation() + "'" +
            ", guest=" + getGuest() +
            "}";
    }
}
