package com.example.vizva.sns_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

public class ReaderSettingsPage extends AppCompatActivity {

    private double latitude;
    private double longitude;
    private String User, Pass, Email;
    private EditText user, pass, email;
    private Button submit;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_settings_page);

        Toast.makeText(this, "This is the Settings Feed", Toast.LENGTH_SHORT).show();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        user = findViewById(R.id.userText);
        pass = findViewById(R.id.passText);
        email = findViewById(R.id.emailText);
        submit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  User = user.getText().toString();
                  Pass = pass.getText().toString();
                  Email = email.getText().toString();
              }
          }
        );

    }

    public void onMapSearch(View view) {
        EditText locationSearch = findViewById(R.id.coordinatesText);
        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            latitude = latLng.latitude;
            longitude = latLng.longitude;
            locationSearch.setText(latitude+", "+longitude);
        }
    }

    public void onSelfClick(View view){
        EditText locationSearch = findViewById(R.id.coordinatesText);
        if (ContextCompat.checkSelfPermission(ReaderSettingsPage.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(ReaderSettingsPage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                });
        locationSearch.setText(latitude+", "+longitude);
    }
}
