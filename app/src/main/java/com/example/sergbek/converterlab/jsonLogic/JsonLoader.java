package com.example.sergbek.converterlab.jsonLogic;


import android.content.Context;
import android.support.annotation.NonNull;

import com.example.sergbek.converterlab.model.Currencies;
import com.example.sergbek.converterlab.model.RootModelOrganization;
import com.example.sergbek.converterlab.model.CurrencyModel;
import com.example.sergbek.converterlab.model.JsonMap;
import com.example.sergbek.converterlab.model.Organization;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonLoader {

    Context mContext;
    private static final String QUERY_URL = "http://resources.finance.ua/ru/public/currency-cash.json";

    private final static String KEY_CURRENCIES = "currencies";
    private final static String KEY_ORG_TYPES = "orgTypes";
    private final static String KEY_REGIONS = "regions";
    private final static String KEY_CITIES = "cities";
    private final static String KEY_ORGANIZATIONS = "organizations";
    private final static String KEY_DATE = "date";

    public JsonLoader(Context context) {
        mContext = context;
    }

    public RootModelOrganization getRoot() {
        RootModelOrganization rootModelOrganization = null;
        try {
            Gson gson = new Gson();

            String jsonString = getJsonStrFromUrl(QUERY_URL);

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonOrganizations = jsonObject.getJSONArray(KEY_ORGANIZATIONS);

            rootModelOrganization = new RootModelOrganization();

            setDateInModel(rootModelOrganization, jsonObject);

            JsonMap jsonMap = getAllMaps(gson, jsonObject);

            List<Organization> organizationList = getAllOrganizations(gson, jsonOrganizations, jsonMap);

            rootModelOrganization.setJsonMap(jsonMap);
            rootModelOrganization.setListOrganization(organizationList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootModelOrganization;
    }

    @NonNull
    private List<Organization> getAllOrganizations(Gson gson, JSONArray jsonOrganizations, JsonMap jsonMap) throws JSONException {

        List<Organization> organizationList = new ArrayList<>();

        for (int i = 0; i < jsonOrganizations.length(); i++) {
            Organization organization = gson.fromJson(String.valueOf(jsonOrganizations.getJSONObject(i)), Organization.class);

            Currencies currencies = new Currencies();
            List<CurrencyModel> currencyModelList = new ArrayList<>();

            JSONObject jsonCurrency = jsonOrganizations
                    .getJSONObject(i)
                    .getJSONObject(KEY_CURRENCIES);

            for (String s : jsonMap.getMapAllCurrencies().keySet()) {
                if (jsonCurrency.has(s)) {
                    CurrencyModel currencyModel = gson.fromJson(String.valueOf(jsonCurrency.getJSONObject(s)),
                            CurrencyModel.class);
                    currencyModel.setAbbr(s);
                    currencyModel.setFullTitle(jsonMap.getMapAllCurrencies().get(s));
                    currencyModelList.add(currencyModel);
                }
            }
            currencies.setCurrencyModels(currencyModelList);
            organization.setCurrencies(currencies);
            organizationList.add(organization);
        }
        return organizationList;
    }

    private void setDateInModel(RootModelOrganization rootModelOrganization, JSONObject jsonObject) throws JSONException {
        rootModelOrganization.setDate(jsonObject.getString(KEY_DATE));
    }

    private String getJsonStrFromUrl(String myUrl) throws IOException {
        URL url = new URL(myUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(10000);
        connection.setRequestMethod("GET");

        int respCode = connection.getResponseCode();
        InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());

        if (respCode == HttpURLConnection.HTTP_OK) {
            final BufferedReader br = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        }

        connection.disconnect();

        return null;
    }

    private JsonMap getAllMaps(Gson gson, JSONObject jsonObject) throws JSONException {
        JsonMap jsonMap = new JsonMap();
        jsonMap.setMapAllCities(getMap(jsonObject, gson, KEY_CITIES));
        jsonMap.setMapAllCurrencies(getMap(jsonObject, gson, KEY_CURRENCIES));
        jsonMap.setMapAllRegions(getMap(jsonObject, gson, KEY_REGIONS));
        jsonMap.setMapAllOrgTypes(getMap(jsonObject, gson, KEY_ORG_TYPES));

        return jsonMap;
    }

    private Map<String, String> getMap(JSONObject jsonObject, Gson gson, String key) throws JSONException {

        Map<String, String> map;
        Type mapType = new TypeToken<Map>() {}.getType();

        map = gson.fromJson(String.valueOf(jsonObject.getJSONObject(key)), mapType);

        return map;
    }

}
