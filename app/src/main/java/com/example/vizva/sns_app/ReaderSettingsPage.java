package com.example.vizva.sns_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ReaderSettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_settings_page);

        Toast.makeText(this, "This is the Settings Feed", Toast.LENGTH_SHORT).show();
    }
}
