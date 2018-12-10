package com.example.vizva.sns_app;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedOutputStream;
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

public class registionActivity extends Activity {

    private EditText  userName, userPassword, userEmail;
    private Button regButton;
    private TextView userLogin;
    private RadioGroup option;
    private FirebaseAuth mAuth;
    private String user_type;
    final String TAG = "registionActivity";


    @Override
    /**
     * onCreate - the init version for android apps
     *
     * This
     * */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registion);

        /**
         * This is is how you are able to access the textboxes and
         * buttons in the app.
         *
         * This is the username, password, email, register button, the create account text and the
         * radio buttons
         *
         * */
        userName = findViewById(R.id.regName);
        userPassword = findViewById(R.id.regPassword);
        userEmail = findViewById(R.id.regEmail);
        regButton = findViewById(R.id.submissionButton);
        userLogin = findViewById(R.id.backText);
        option = findViewById(R.id.radio_group);
        mAuth = FirebaseAuth.getInstance();

        Log.i(TAG, "The mAuth is: " + mAuth.toString());


        /**
         * What this is doing is setting an action to the register button.
         * When the button is pressed it does a whatever is in the scope of
         * the onClickListener
         *
         * */
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 *  The input of the textboxes aren't strings so you have to format them into
                 *  strings. This is done by getting the variable and calling the getText and
                 *  toString()
                 * */
                String email = userEmail.getText().toString();
                String pass = userPassword.getText().toString();

                if( validate() ) {

                    /**
                     * This gets whatever the radio button is since the reader and publisher are
                     * on different tables*/
                    if (option.getCheckedRadioButtonId() == R.id.readerRadio) {
                        user_type = "Readers";
                    } else {
                        user_type = "Publishers";
                    }
                    Log.i(TAG, "THE TYPE IS: " + user_type);


                                /**
                                 * This code is doing the method userReg then going back to the main
                                 * page at the startActivity
                                 * */
                                userReg(user_type);
                                Intent i = new Intent(registionActivity.this, MainActivity.class);
                                startActivity(i);
                }
            }
        });

        // Redirects
        /**
         * This sends the user back to the login page since they already have an account
         * */
        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registionActivity.this, MainActivity.class));
            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//    }

/**
 * Validate
 *
 * This method checks if any of the text fields are empty, the email is invalid or radio buttons
 * haven't been selected. True if valid, false if not
 *
 * */
    private Boolean validate(){
        Boolean result = false;

        String name = userName.getText().toString();
        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();

        if ( name.isEmpty() || password.isEmpty() || email.isEmpty() ) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else if(  !( Patterns.EMAIL_ADDRESS.matcher(email).matches() ) ){
            Toast.makeText(this, "Please enter a correct email", Toast.LENGTH_SHORT).show();
        }else if(option.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Pick a option", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;

    }
//    String type
/**
 * userReg
 *
 * This is the method that collects the user information that will be stored in the database and is
 * called in the onCreate mostly. The Background class will do most of the work honestly.
 *
 * */
    public void userReg(String type) {

        String user_email = userEmail.getText().toString().trim();
        String user_password = userPassword.getText().toString().trim();
        String user_name = userName.getText().toString().trim();

        BackgroundTask bg = new BackgroundTask();
        bg.execute(user_name, user_email, user_password, type);
    }


/**
 * Do not worry about the commented out code this is just stuff about firebase that I haven't
 * completed yet
 * */

//    private void actionMan(String email){
//        ActionCodeSettings actionCodeSettings =
//                ActionCodeSettings.newBuilder()
//                        // URL you want to redirect back to. The domain (www.example.com) for this
//                        // URL must be whitelisted in the Firebase Console.
//                        .setUrl("https://www.example.com/finishSignUp?cartId=1234")
//                        // This must be true
//                        .setHandleCodeInApp(true)
//                        .setIOSBundleId("com.example.ios")
//                        .setAndroidPackageName(
//                                "com.example.android",
//                                true, /* installIfNotAvailable */
//                                "12"    /* minimumVersion */)
//                        .build();
//
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.sendSignInLinkToEmail(email, actionCodeSettings)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
//                        }
//                    }
//                });
//    }

//    private void createNewUser(String email, String password) {
//        Log.i(TAG,"WE IN THIS BITCH");
//
////        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(registionActivity.this, new OnCompleteListener<AuthResult>() {
////                    @Override
////                    public void onComplete(@NonNull Task<AuthResult> task) {
////                        if (task.isSuccessful()) {
////                            Log.d(TAG, "Authentication successful");
////                        } else {
////                            Toast.makeText(registionActivity.this, "Authentication failed.",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                });
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(registionActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "createUserWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(registionActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//
//                        // ...
//                    }
//                });
//    }


//
//private void fireBaseReg(String user, String pass){
//
//    mAuth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(registionActivity.this ,new OnCompleteListener<AuthResult>() {
//        @Override
//        public void onComplete(@NonNull Task<AuthResult> task) {
//
//            if (task.isSuccessful()){ Log.i(TAG,"FireBase is successful"); }
//            else{ Log.i(TAG, "");}
//            }
//    });
//}


/***
 * Background Task
 *
 * This class runs a background thread on the app that finds the database and inserts the new user.
 * The 95% of the work is done in the doInBackground. The onPreExecute is the action done in the
 * beginning (kinda like init). The onProgressUpdate is what occurs during the background activity
 * and can be used as a message box for the user to know the progress of the thread. Lastly the
 * onPostExecute is whats occurs once doInBackground is done, usually at this step I print a toast
 * message or make an intent that transistions the app to go to another activity.
 */

class BackgroundTask extends AsyncTask<String,Void,String> {

    String my_url;

    @Override
    protected String doInBackground(String... params) {


        /**
         * These are the urls that you'll be connecting to which is how you'll interact with
         * the database
         * */
        if( params[3].equals("Readers") ){
            my_url =" http://192.168.64.2/webapp/reg_reader.php";
        }else{
            my_url = "http://192.168.64.2/webapp/reg_publisher.php";
        }

        try{

            /**
             * The params are from the parameters of the execute
             * The code below is what does the HttpConnection with the database to do a thing
             *
             * */


            String user = params[0], email = params[1], pass = params[2];
            OutputStream os;

//            Log.i(TAG, "The username is: " + user);
//            Log.i(TAG, "The email is: " + email);
//            Log.i(TAG, "The password is: " + pass);
//            Log.i(TAG, "The type is: " + type);

            URL url = new URL(my_url);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
//            Log.i(TAG, "The http has been opened ");
            http.setRequestMethod("POST");
            http.setDoOutput(true);

//            Log.i(TAG, "The http is: " + http);

            os = http.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//            Log.i(TAG, "After the bufferWriter");

            String data = URLEncoder.encode("user", "UTF-8")+"="+ URLEncoder.encode(user) +"&&" +
                    URLEncoder.encode("email", "UTF-8")+"="+ URLEncoder.encode(email) + "&&" +
                    URLEncoder.encode("pass", "UTF-8")+"="+ URLEncoder.encode(pass);

            Log.i(TAG, "The data is: " + data);

            bw.write(data);
            bw.close();
            os.close();

            http.connect();

//            Log.i(TAG, "The output and buffer is now closed");

            InputStream is = http.getInputStream();
            is.close();
//            Log.i(TAG, "The InputStream is closed");

            http.disconnect();
//            Log.i(TAG, "The http is disconnected");

            return "Data is Inserted";

        }catch(MalformedURLException e){
            Log.i(TAG, "Malformed "+ e.getMessage());

        }catch (IOException e){
            Log.i(TAG, "IOException " + e.getMessage());

        }
        return "Data not inserted";
    }
    /**
     *
     * onPreExecute and onPresgress aren't important, don't worry about that.
     *
     * */
    @Override
    protected void onPreExecute() { super.onPreExecute(); }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    /**
     * What occurs after when the doInBackGround is done
     * */

    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT ).show();
    }}
/**
 * This is a copy of the login from the MainActivity Page, its lazy but whatever, ITs works.
 * essentially I want to check the database and see if the username or email exist.
 * */

//class BackgroundTask2 extends AsyncTask<String,Void,String> {
//
//    String my_url;
//
//    @Override
//    protected String doInBackground(String... params) {
//
//        try{
//
//            String user = params[0], pass = params[1];
//            OutputStream os;
//
////            Log.i(TAG, "The username is: " + user);
////            Log.i(TAG, "The password is: " + pass);
//
//
//            URL url = new URL(my_url);
//            HttpURLConnection http = (HttpURLConnection) url.openConnection();
////            Log.i(TAG, "The http has been opened ");
//            http.setRequestMethod("POST");
//            http.setDoOutput(true);
//
////            Log.i(TAG, "The http is: " + http);
//
//            os = http.getOutputStream();
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
////            Log.i(TAG, "After the bufferWriter");
//
//            String data = URLEncoder.encode("user", "UTF-8")+"="+ URLEncoder.encode(user) +"&&" +
//                    URLEncoder.encode("pass", "UTF-8")+"="+ URLEncoder.encode(pass);
//
////                Log.i(TAG, "The data is: " + data);
//
//            bw.write(data);
//            bw.close();
//            os.close();
//
//            http.connect();
//
////            Log.i(TAG, "The output and buffer is now closed");
//
//            InputStream is = http.getInputStream();
//            is.close();
////            Log.i(TAG, "The InputStream is closed");
//
//            http.disconnect();
////            Log.i(TAG, "The http is disconnected");
//
//            return "Account already exist";
//
//        }catch(MalformedURLException e){
//            Log.i(TAG, "Malformed "+ e.getMessage());
//
//        }catch (IOException e){
//            Log.i(TAG, "IOException " + e.getMessage());
//
//        }
//        return "Account doesn't exist";
//    }
//
//    @Override
//    protected void onPreExecute() { my_url = "http://192.168.64.2/webapp/login.php"; }
//
//    @Override
//    protected void onProgressUpdate(Void... values) {
//        super.onProgressUpdate(values);
//    }
//
//    @Override
//    protected void onPostExecute(String s) {
//        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT ).show();
//        if(s.equals("Welcome")){
//            startActivity(new Intent(getApplicationContext(), HomeActivity.class ));
//        }
//    }}






}
