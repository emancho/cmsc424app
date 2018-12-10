package com.example.vizva.sns_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PubSearchPage extends AppCompatActivity {

    private EditText input1, input2, input3;
    private Button searchSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_search_page);

        input1 = findViewById(R.id.searchLocationInput);        //  the input for location
        input2 = findViewById(R.id.searchCategoryInput);        //  the input for category
        input3 = findViewById(R.id.searchTimeInput);            //  the input for time
        searchSubmit = findViewById(R.id.searchSubmitButton);   //  the search button


        searchSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String locaton = input1.getText().toString(),    // the string format of location
                        category = input2.getText().toString(),  // the string format of category
                        time = input3.getText().toString();      // the string format of time

                Intent i = new Intent(PubSearchPage.this, PubSearchResultsPage.class);
                startActivity(i);

                // reach into database and get all results that match what user wants

                // store results in ArrayList that can be used for RecyclerView which will display
                // the options

            }
        });

//        Toast.makeText(this, "Search page", Toast.LENGTH_SHORT).show();
    }
}
