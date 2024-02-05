package com.guest.app.domain;

import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ArrivalDepartureStatic.
 */
@Document(collection = "arrival_departure_static")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArrivalDepartureStatic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("start_date")
    private Instant startDate;

    @Field("end_date")
    private Instant endDate;

    @Field("start_count_person")
    private Integer startCountPerson;

    @Field("end_count_person")
    private Integer endCountPerson;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ArrivalDepartureStatic id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public ArrivalDepartureStatic startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public ArrivalDepartureStatic endDate(Instant endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Integer getStartCountPerson() {
        return this.startCountPerson;
    }

    public ArrivalDepartureStatic endCountPerson(Integer endCountPerson) {
        this.setEndCountPerson(endCountPerson);
        return this;
    }

    public void setEndCountPerson(Integer endCountPerson) {
        this.endCountPerson = endCountPerson;
    }

    public Integer getEndCountPerson() {
        return this.endCountPerson;
    }

    public ArrivalDepartureStatic startCountPerson(Integer startCountPerson) {
        this.setStartCountPerson(startCountPerson);
        return this;
    }

    public void setStartCountPerson(Integer startCountPerson) {
        this.startCountPerson = startCountPerson;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArrivalDepartureStatic)) {
            return false;
        }
        return getId() != null && getId().equals(((ArrivalDepartureStatic) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArrivalDepartureStatic{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", startCountPerson=" + getStartCountPerson() +
            ", endCountPerson=" + getEndCountPerson() +
            "}";
    }
}
