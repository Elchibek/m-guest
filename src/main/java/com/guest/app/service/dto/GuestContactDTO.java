package com.guest.app.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.guest.app.domain.GuestContact} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GuestContactDTO implements Serializable {

    private String id;

    private String name;

    private String phone;

    private Boolean isDelete;

    private GuestDTO guest;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        if (!(o instanceof GuestContactDTO)) {
            return false;
        }

        GuestContactDTO guestContactDTO = (GuestContactDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, guestContactDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuestContactDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", guest=" + getGuest() +
            "}";
    }
}
