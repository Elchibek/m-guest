package com.guest.app.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.guest.app.domain.GuestFrom} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GuestFromDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuestFromDTO)) {
            return false;
        }

        GuestFromDTO guestFromDTO = (GuestFromDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, guestFromDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuestFromDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
