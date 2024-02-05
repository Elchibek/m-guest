package com.guest.app.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.guest.app.domain.ArrivalDepartureStatic} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArrivalDepartureStaticDTO implements Serializable {

    private String id;

    private Instant startDate;

    private Instant endDate;

    private Integer startCountPerson;

    private Integer endCountPerson;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getStartCountPerson() {
        return startCountPerson;
    }

    public void setStartCountPerson(Integer startCountPerson) {
        this.startCountPerson = startCountPerson;
    }

    public Integer getEndCountPerson() {
        return endCountPerson;
    }

    public void setEndCountPerson(Integer endCountPerson) {
        this.endCountPerson = endCountPerson;
    }

    @Override
    public String toString() {
        return (
            "ArrivalDepartureStaticDTO [id=" +
            id +
            ", startDate=" +
            startDate +
            ", endDate=" +
            endDate +
            ", startCountPerson=" +
            startCountPerson +
            ", endCountPerson=" +
            endCountPerson +
            "]"
        );
    }
}
