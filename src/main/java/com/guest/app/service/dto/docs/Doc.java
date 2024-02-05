package com.guest.app.service.dto.docs;

import com.guest.app.service.dto.GuestFilterDTO;
import java.util.List;

public class Doc {

    private String docType;

    private List<TabKey> tabKeys;

    private GuestFilterDTO guestFilter;

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public GuestFilterDTO getGuestFilter() {
        return guestFilter;
    }

    public void setGuestFilter(GuestFilterDTO guestFilter) {
        this.guestFilter = guestFilter;
    }

    public List<TabKey> getTabKeys() {
        return tabKeys;
    }

    public void setTabKeys(List<TabKey> tabKeys) {
        this.tabKeys = tabKeys;
    }

    @Override
    public String toString() {
        return "Doc [docType=" + docType + ", tabKeys=" + tabKeys + ", guestFilter=" + guestFilter + "]";
    }
}
