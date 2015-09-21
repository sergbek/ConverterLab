package com.example.sergbek.converterlab.asynctask;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class MapLoader extends AsyncTask<String, Void, LatLng> {
    private int mZoom;

    @Override
    protected LatLng doInBackground(String... params) {
        setZoom(params[1]);
        return parseCoordinates(getUrl(params[0], params[1]));
    }

    private void setZoom(String _address) {
        if (validateText(_address)) {
            mZoom = 17;
        } else {
            mZoom = 12;
        }
    }

    private boolean validateText(String text) {
        if (text.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private String getUrl(String city, String address) {
        StringBuilder builder = new StringBuilder("http://maps.google.com/maps/api/geocode/json?address=Ukraine+");
        if (validateText(address)) {
            try {
                builder.append(URLEncoder.encode(city, "utf8") + "+" + URLEncoder.encode(address, "utf8") + "&sensor=false");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return builder.toString();
        } else {
            try {
                builder.append(URLEncoder.encode(city, "utf8") + "&sensor=false");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return builder.toString();
        }
    }


    private LatLng parseCoordinates(String uri) {
        String json = null;
        URL url = null;
        try {
            url = new URL(uri);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");

            int respCode = conn.getResponseCode();
            if (respCode == 200) {
                final BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                json = sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject;
        double lat = 0;
        double lng = 0;
        try {
            jsonObject = new JSONObject(json);

            lng = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        LatLng latLng = new LatLng(lat, lng);

        return latLng;
    }

    public int getZoom() {
        return mZoom;
    }

}
