package com.example.vizva.sns_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PubCreateTags extends AppCompatActivity {

    private EditText input1, input2;
    private Button insertButton, homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_create_tags);

        input1 = findViewById(R.id.createCatInput);       // Main Cat
        input2 = findViewById(R.id.createSubInput);       // Sub Cat

        insertButton = findViewById(R.id.createInsertButton);   // insert button
        homeButton = findViewById(R.id.returnToPubHomeButton);  // home button

        // When the home button is pressed

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PubCreateTags.this, HomeActivityPub.class);
                startActivity(i);
            }
        });

        // When the insert button is pressed

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gets string format of text inputs which will be used to insert into database
                String mainCat = input1.getText().toString(),
                        subCat = input2.getText().toString();

                // Connects to database:
                // Does either of three things: inserts new cat with sub, insert new sub with
                // existing cat or nothing because cat anf sub already exist


            }
        });






//        Toast.makeText(this, "Create Tags", Toast.LENGTH_SHORT).show();
    }
}
