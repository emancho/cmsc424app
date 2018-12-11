package com.example.vizva.sns_app;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class PubPostPage extends AppCompatActivity {

    private Button submit;
    private EditText input1, input2, input3, input4, input5;
    private double latitude;
    private double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_post_page);

        submit = findViewById(R.id.postSubmitButton);   // Submit Button
        input1 = findViewById(R.id.postTitleText);      // Post Title
        input2 = findViewById(R.id.postInput);          // Post message
        input3 = findViewById(R.id.postLocation);       // The location of the post
        input4 = findViewById(R.id.postDistance);       // Range of location
        input5 = findViewById(R.id.postTags);           // Where the tags are located

//        Toast.makeText(this, "POST PAGE", Toast.LENGTH_SHORT).show();



        // When the submit button is pressed, the app submits data to to both post database and
        // Archive database

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //connect with database

                //return back home
            }
        });


    }

    public void onMapSearch(View view) {
        String location = input3.getText().toString();
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
            input3.setText(latitude+", "+longitude);
        }
    }
}
