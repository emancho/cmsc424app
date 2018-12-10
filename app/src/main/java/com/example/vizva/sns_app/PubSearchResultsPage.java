package com.example.vizva.sns_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Size;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class PubSearchResultsPage extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<String> post;
    private String TAG = "PubSearchResultsPage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pub_search_results_page);


        post = new ArrayList<>();
        Log.i(TAG, "Before the for loop");
//        for (int i = 0; i < 10; i++){
//            Log.i(TAG, "The loop idx is: " + i);
////            Post post()
//            post.add("FUK");
//        }

        BackgroundTask3 b = new BackgroundTask3();

        b.execute();
        Log.i(TAG, "The post is contains: " + post);

//        recyclerView = findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new UserAdapter(post);
//        recyclerView.setAdapter(adapter);




    }

    class BackgroundTask3 extends AsyncTask<String,Void,ArrayList<String>> {

        private String my_url;

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            // Variables for the server side
            BufferedReader reader;
            String text;
            ArrayList<String> stuff = new ArrayList<>();
            //Send Data
            try {
                // Defined URL  where to send data
                URL url = new URL(my_url);

                // Send POST data request

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
//                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//                wr.write(data);
//                wr.flush();

                Log.i(TAG, "Buffer has been flushed()");

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;


                Log.i(TAG, "About to summon the while loop");
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    String[] bu = line.split("<br>");
                    if(bu.length > 1){
                        for(int i = 0; i< bu.length;i++){
                            stuff.add(bu[i]);
                        }
                    }else {
                        stuff.add(line + "\n");
                        sb.append(line + "\n");
                    }
                    Log.i(TAG, "The line is: " + line);

                }

                text = sb.toString();

                Log.i(TAG, "The text received from the http is: " + text);
//                String[] words = text.split("\n");

//                if (text.equals("Connection Successful\n")) {
//                    return params[2];
//                } else {
//                    return "Log info incorrect";
//                }
                return stuff;

                // the catches for a bad shit, don't worry about it
            } catch (MalformedURLException e) {
                Log.i(TAG, "MalformedURLException: " + e.getMessage());
            } catch (IOException e) {
                Log.i(TAG, "IOException: " + e.getMessage());
            }
            return stuff;
        }

        @Override
        protected void onPreExecute() { my_url ="http://192.168.64.2/webapp/pub_search.php"; }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<String> s) {

          if(s.size() == 0){
              Toast.makeText(PubSearchResultsPage.this, "FAILURE NIGGA", Toast.LENGTH_SHORT).show();
          }else {
//              for(int i = 0; i< s.size();i++ ){
              post.addAll(s);
              Log.i(TAG, "The post is: " + post);
          //}
              recyclerView = findViewById(R.id.recycler_view);
              recyclerView.setLayoutManager(new LinearLayoutManager(PubSearchResultsPage.this));
              adapter = new UserAdapter(post);
              recyclerView.setAdapter(adapter);


          }


        }}
}
