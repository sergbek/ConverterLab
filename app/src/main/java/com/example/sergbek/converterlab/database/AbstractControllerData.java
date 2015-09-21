package com.example.sergbek.converterlab.database;


import com.example.sergbek.converterlab.model.RootModelOrganization;
import com.example.sergbek.converterlab.model.Organization;

import java.util.List;
import java.util.Map;

public interface AbstractControllerData {
    void addOrganization(List<Organization> listOrganizations);

    void addRegions(Map<String, String> mapRegions);

    void addCities(Map<String, String> mapCities);

    void addCurrencies(Map<String, String> mapCurrencies);

    void addOrgTypes(Map<String, String> mapOrgTypes);

    void addCourse(List<Organization> listOrganizations);

    void setDateBase(RootModelOrganization rootModelOrganization);

    List<Organization> getAllOrganizations();

    Organization getOrganization(String id);

    String getDate();
}
