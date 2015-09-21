package com.example.sergbek.converterlab.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.sergbek.converterlab.R;
import com.example.sergbek.converterlab.asynctask.MapLoader;
import com.example.sergbek.converterlab.utils.Utils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutionException;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Toolbar mToolbar;
    private String mTitle;
    private String mCity;
    private String mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mTitle = getIntent().getStringExtra("title");
        mCity = getIntent().getStringExtra("city");
        mAddress = getIntent().getStringExtra("address");

        settingToolbar();

        if (Utils.isStateInternet(this)) {
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MapLoader mapLoader = new MapLoader();
        mapLoader.execute(mCity, mAddress);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        try {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapLoader.get(), mapLoader.getZoom()));
            mMap.addMarker(new MarkerOptions()
                    .position(mapLoader.get())
                    .title(mTitle)
                    .snippet(mAddress));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void settingToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow);
        mToolbar.setTitle(mTitle);
        mToolbar.setSubtitle(mCity);
        setSupportActionBar(mToolbar);
    }
}
