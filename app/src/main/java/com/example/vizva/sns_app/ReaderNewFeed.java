package com.example.vizva.sns_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ReaderNewFeed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_new_feed);

        Toast.makeText(this, "This is the News Feed", Toast.LENGTH_SHORT).show();
    }
}
