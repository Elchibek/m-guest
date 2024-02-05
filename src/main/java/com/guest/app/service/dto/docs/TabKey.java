package com.guest.app.service.dto.docs;

public class TabKey {

    private String name;
    private String fieldName;
    private Boolean selected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "TabKey [name=" + name + ", fieldName=" + fieldName + ", selected=" + selected + "]";
    }
}
