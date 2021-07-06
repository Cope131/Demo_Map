package com.myapplicationdev.android.demomap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback{

    private final String DEBUG_TAG = MainActivity.class.getSimpleName();

    // Views
    private Button normalBtn, terrainBtn, satelliteBtn;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initMap();
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
    }
}