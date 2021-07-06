package com.myapplicationdev.android.demomap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private final String DEBUG_TAG = MainActivity.class.getSimpleName();
    private final int MY_LOCATION_REQUEST_CODE = 0;

    // Views
    private Button normalBtn, terrainBtn, satelliteBtn;
    private GoogleMap googleMap;
    private boolean enableMyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initMap();
        askPermission();
    }

    private void addMarkers() {
        // Locations
        LatLng causewayPoint = new LatLng(1.436065, 103.786263);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(causewayPoint, 15));
        LatLng rp = new LatLng(1.44224, 103.785733);

        // Causeway Point Marker
        MarkerOptions markerOptions = new MarkerOptions()
                .position(causewayPoint)
                .title("Causeway Point")
                .snippet("Shopping after class")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        googleMap.addMarker(markerOptions);

        // Republic Polytechnic Marker
        markerOptions = new MarkerOptions()
                .position(rp)
                .title("Republic Polytechnic")
                .snippet("C347 Android Programming II")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
        googleMap.addMarker(markerOptions);
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_LOCATION_REQUEST_CODE);
    }

    private void initMap() {
        SupportMapFragment mapFragment
                = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initViews() {
        normalBtn = findViewById(R.id.normal_button);
        terrainBtn = findViewById(R.id.terrain_button);
        satelliteBtn = findViewById(R.id.satellite_button);
        normalBtn.setOnClickListener(this);
        terrainBtn.setOnClickListener(this);
        satelliteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int type = -1;
        switch (v.getId()) {
            case R.id.normal_button:
                type = GoogleMap.MAP_TYPE_NORMAL;
                break;
            case R.id.terrain_button:
                type = GoogleMap.MAP_TYPE_TERRAIN;
                break;
            case R.id.satellite_button:
                type = GoogleMap.MAP_TYPE_SATELLITE;
        }
        if (type != -1 && googleMap != null) {
            googleMap.setMapType(type);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (googleMap != null) {
            addMarkers();
            UiSettings uiSettings = googleMap.getUiSettings();
            uiSettings.setCompassEnabled(true);
            uiSettings.setZoomControlsEnabled(true);
            uiSettings.setZoomGesturesEnabled(true);
            uiSettings.setMyLocationButtonEnabled(true);
            uiSettings.setRotateGesturesEnabled(true);

            int permissionResult = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permissionResult == PermissionChecker.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(enableMyLocation);
            } else {
                askPermission();
            }
        }
    }
}