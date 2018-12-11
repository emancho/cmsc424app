package com.example.vizva.sns_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    private EditText name, password;
    private TextView userRegister;
    private Button login, test;
    private String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private RadioGroup option;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init the authentication flow, the doc said so.

        name = findViewById(R.id.usrText);
        password = findViewById(R.id.passText);
        login = findViewById(R.id.loginButton);
        userRegister = findViewById(R.id.regText);
        mAuth = FirebaseAuth.getInstance();
        test = findViewById(R.id.button);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReaderSettingsPage.class));
            }
        });

        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, registionActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                String user_name = name.toString();
//                String user_pass = password.toString();
                Log.i(TAG,"IM WITHIN THE CLICK LISTENER");
                userRegister = findViewById(R.id.regText);
//                Log.i(TAG, "inside onclick for login");

                //checks if verified
//                if ( user.isEmailVerified() ){
                Log.i(TAG, "The option was about to be got");
                option = findViewById(R.id.radioGroupHomePage);
                BackgroundTask2 bg = new BackgroundTask2();
                String user = name.getText().toString();
                String pass = password.getText().toString();

                Log.i(TAG, "Before the getCheckedRadioButtonId");

                String user_type;
                if (option.getCheckedRadioButtonId() == R.id.readerRadioHome){
                    user_type = "Readers";
                }else{
                    user_type = "Publishers";
                }
                Log.i(TAG, "The user type is :" + user_type);
                Log.i(TAG, "The background is about to run");

                bg.execute(user, pass, user_type);



//                }else{
//                    Toast.makeText(getApplicationContext(), "YOU'T AREN'T VERIFIED", Toast.LENGTH_SHORT).show();
//                }
//                Log.i(TAG, "Background Task is created");
            }
        });

    }

    /**
     * This is what runs in the background when then object is executed.
     * The putExtra stores a variable that can be passed to the other
     * Activity Pages
     *
     * */

    class BackgroundTask2 extends AsyncTask<String,Void,String> {

        private String my_url, redirect;

        @Override
        protected String doInBackground(String... params) {
            // User defined variables
            String user = params[0],
                    pass = params[1];

            // Variables for the server side
            BufferedReader reader;
            String text;

            if(params[2].equals("Readers")){
                my_url = "http://192.168.64.2/webapp/login_reader.php";
            }else{
                my_url = "http://192.168.64.2/webapp/login_publisher.php";
            }

            //Send Data
            try {
                String data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user) + "&&" +
                        URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(pass);

                Log.i(TAG, "The data is: " + data);

                // Defined URL  where to send data
                URL url = new URL(my_url);

                // Send POST data request

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                Log.i(TAG, "Buffer has been flushed()");

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;


                Log.i(TAG, "About to summon the while loop");
                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                    Log.i(TAG, "The line is: " + line);
                }

                text = sb.toString();

                Log.i(TAG, "The text received from the http is: " + text);
//                String[] words = text.split("\n");

                if (!text.equals("Failed to connect to MySQL:\n")) {
                    return params[2];
                } else {
                    return "Log info incorrect";
                }

                // the catches for a bad shit, don't worry about it
            } catch (MalformedURLException e) {
                Log.i(TAG, "MalformedURLException: " + e.getMessage());
            } catch (IOException e) {
                Log.i(TAG, "IOException: " + e.getMessage());
            }
            return "";
        }

        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {

            Log.i(TAG, "The end result is:" + s);


            if( s.equals("Readers") || s.equals("Publishers") ){
                Log.i(TAG, "About to enter in this bitch");

                if(s.equals("Readers")){
                    Log.i(TAG, "LOGGING INTO READERS");
                    Toast.makeText(getApplicationContext(),"Welcome " + name.getText().toString(), Toast.LENGTH_SHORT ).show();
                    Intent i = new Intent(MainActivity.this , HomeActivityReader.class);
//                    i.putExtra("username", name.getText().toString());
                    startActivity(i);
                }else {
                    Log.i(TAG, "LOGGING INTO PUBLISHERS");
//                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(MainActivity.this, HomeActivityPub.class));
                    Toast.makeText(getApplicationContext(),"Welcome " + name.getText().toString(), Toast.LENGTH_SHORT ).show();
                    Intent i = new Intent(MainActivity.this , HomeActivityPub.class);
                    i.putExtra("username", name.getText().toString());
                    startActivity(i);
                }
            }
//            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT ).show();
        }}}
