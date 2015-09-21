package com.example.sergbek.converterlab.model;

import java.util.List;

public class RootModelOrganization {
    private String date;
    private List<Organization> listOrganization;
    private JsonMap jsonMap;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Organization> getListOrganization() {
        return listOrganization;
    }

    public void setListOrganization(List<Organization> listOrganization) {
        this.listOrganization = listOrganization;
    }

    public JsonMap getJsonMap() {
        return jsonMap;
    }

    public void setJsonMap(JsonMap jsonMap) {
        this.jsonMap = jsonMap;
    }
}
