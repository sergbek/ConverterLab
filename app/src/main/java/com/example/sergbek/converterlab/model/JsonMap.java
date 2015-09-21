package com.example.sergbek.converterlab.model;

import java.util.Map;

public class JsonMap {

    private Map<String, String> mapAllCurrencies;
    private Map<String, String> mapAllRegions;
    private Map<String, String> mapAllCities;
    private Map<String,String> mapAllOrgTypes;

    public Map<String, String> getMapAllCurrencies() {
        return mapAllCurrencies;
    }

    public void setMapAllCurrencies(Map<String, String> mapAllCurrencies) {
        this.mapAllCurrencies = mapAllCurrencies;
    }

    public Map<String, String> getMapAllRegions() {
        return mapAllRegions;
    }

    public void setMapAllRegions(Map<String, String> mapAllRegions) {
        this.mapAllRegions = mapAllRegions;
    }

    public Map<String, String> getMapAllCities() {
        return mapAllCities;
    }

    public void setMapAllCities(Map<String, String> mapAllCities) {
        this.mapAllCities = mapAllCities;
    }

    public Map<String, String> getMapAllOrgTypes() {
        return mapAllOrgTypes;
    }

    public void setMapAllOrgTypes(Map<String, String> mapAllOrgTypes) {
        this.mapAllOrgTypes = mapAllOrgTypes;
    }
}
