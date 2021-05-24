package com.example.parks;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.parks.data.AsyncResponse;
import com.example.parks.data.Repository;
import com.example.parks.model.Park;
import com.example.parks.model.ParkViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ParkViewModel parkViewModel;
    private List<Park> parkList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        parkViewModel = new ViewModelProvider(this)
                .get(ParkViewModel.class);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottohm_nagivation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();
            if(id == R.id.Maps_navbutton) {
                mMap.clear();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.map,mapFragment)
                        .commit();
                mapFragment.getMapAsync(this);
                return true;

            }
            else if( id == R.id.parks_info)
            {
                selectedFragment = ParksFragment.newInstance();

            } getSupportFragmentManager().beginTransaction()
                    .replace(R.id.map,selectedFragment)
                    .commit();
            return true;
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        parkList = new ArrayList<>();
        parkList.clear();
        Repository.getParks(parks -> {
            parkList = parks;
            for(Park park :parks ) {
            LatLng marker = new LatLng(Double.parseDouble(park.getLatitude()),Double.parseDouble(park.getLongitude()));
            mMap.addMarker(new MarkerOptions().position(marker).title(park.getFullName()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
            Log.d("Park", "onMapReady: " + park.getFullName());
        }
            parkViewModel.setSelectedParks(parkList);
        });
    }
}