package com.example.vizva.sns_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivityPub extends AppCompatActivity {
   private Button button1,button2, button3, button4;
   private String TAG = "HomePublisher";
   private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pub);

        button1 = findViewById(R.id.makePostButton);
        button2 = findViewById(R.id.searchButton);
        button3 = findViewById(R.id.createButton);
        button4 = findViewById(R.id.logoutButton);

        Intent i = getIntent();
        user = i.getStringExtra("username");



        // When you hit the "make a post" button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(HomeActivityPub.this, PubPostPage.class);
                in.putExtra("username", user);
                startActivity(in);

            }
        });

        // When you hit the "search for a post" button
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(HomeActivityPub.this, PubSearchPage.class);
                in.putExtra("username", user);
                startActivity(in);

            }
        });

        // When you hit the "create category" button
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(HomeActivityPub.this, PubCreateTags.class);
                startActivity(in);

            }
        });

        // When you hit the "logout" button
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "The user hit the logout button");
                Intent in = new Intent(HomeActivityPub.this, MainActivity.class);
                Toast.makeText(HomeActivityPub.this, "Goodbye" + user, Toast.LENGTH_SHORT).show();
                startActivity(in);
            }
        });
    }}
