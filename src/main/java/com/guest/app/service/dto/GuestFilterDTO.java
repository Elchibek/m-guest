package com.guest.app.service.dto;

import java.time.LocalDateTime;

public class GuestFilterDTO {

    private String userId;
    // ---------------------------------------------------------
    private String searchText;
    private String searchType;
    // ---------------------------------------------------------
    private String guestFromId;
    private String guestBlockId;
    private String entranceId;
    private String floorId;
    private String houseId;
    // ---------------------------------------------------------
    private String typeBetween;
    private Integer from;
    private Integer before;
    // ---------------------------------------------------------
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String typeDate;
    // ---------------------------------------------------------
    private Boolean isUpdate;
    private Boolean isDeleted;
    private Boolean isChange;
    private String parentId;
    // ---------------------------------------------------------
    private Boolean isPaid;
    private Boolean isDeparture;
    private Boolean isArchive;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getTypeBetween() {
        return typeBetween;
    }

    public void setTypeBetween(String typeBetween) {
        this.typeBetween = typeBetween;
    }

    public String getGuestFromId() {
        return guestFromId;
    }

    public void setGuestFromId(String guestFromId) {
        this.guestFromId = guestFromId;
    }

    public String getGuestBlockId() {
        return guestBlockId;
    }

    public void setGuestBlockId(String guestBlockId) {
        this.guestBlockId = guestBlockId;
    }

    public String getEntranceId() {
        return entranceId;
    }

    public void setEntranceId(String entranceId) {
        this.entranceId = entranceId;
    }

    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getBefore() {
        return before;
    }

    public void setBefore(Integer before) {
        this.before = before;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getTypeDate() {
        return typeDate;
    }

    public void setTypeDate(String typeDate) {
        this.typeDate = typeDate;
    }

    public Boolean getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsChange() {
        return isChange;
    }

    public void setIsChange(Boolean isChange) {
        this.isChange = isChange;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Boolean getIsDeparture() {
        return isDeparture;
    }

    public void setIsDeparture(Boolean isDeparture) {
        this.isDeparture = isDeparture;
    }

    public Boolean getIsArchive() {
        return isArchive;
    }

    public void setIsArchive(Boolean isArchive) {
        this.isArchive = isArchive;
    }

    @Override
    public String toString() {
        return (
            "GuestFilterDTO [searchText=" +
            searchText +
            ", searchType=" +
            searchType +
            ", guestFromId=" +
            guestFromId +
            ", guestBlockId=" +
            guestBlockId +
            ", entranceId=" +
            entranceId +
            ", floorId=" +
            floorId +
            ", houseId=" +
            houseId +
            ", from=" +
            from +
            ", before=" +
            before +
            ", startDate=" +
            startDate +
            ", endDate=" +
            endDate +
            ", typeDate=" +
            typeDate +
            ", paid=" +
            isPaid +
            "]"
        );
    }
}
