package com.guest.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.guest.app.domain.PutAway} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PutAwayDTO implements Serializable {

    private String id;

    private Integer countPerson;

    private Instant whenLeft;

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

    public Instant getWhenLeft() {
        return whenLeft;
    }

    public void setWhenLeft(Instant whenLeft) {
        this.whenLeft = whenLeft;
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
        if (!(o instanceof PutAwayDTO)) {
            return false;
        }

        PutAwayDTO putAwayDTO = (PutAwayDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, putAwayDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PutAwayDTO{" +
            "id='" + getId() + "'" +
            ", countPerson=" + getCountPerson() +
            ", whenLeft='" + getWhenLeft() + "'" +
            ", explanation='" + getExplanation() + "'" +
            ", guest=" + getGuest() +
            "}";
    }
}
