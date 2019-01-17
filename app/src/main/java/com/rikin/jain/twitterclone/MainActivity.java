 package com.rikin.jain.twitterclone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

 public class MainActivity extends AppCompatActivity implements View.OnClickListener{
     private EditText edtEmail, edtUsername, edtPassword;
     private Button btnSignUp, btnLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        setTitle("SignUp");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#427df4")));
        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);
                }
                return false;
            }
        });
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnSignUp.setOnClickListener(this);
        btnLogIn.setOnClickListener(this);



    }

     @Override
     public void onClick(View view) {
         switch (view.getId()){
             case R.id.btnSignUp:
                if( edtEmail.getText().toString().equals("") || edtUsername.getText().toString().equals("") ||
                        edtPassword.getText().toString().equals("")) {
                    FancyToast.makeText(MainActivity.this,"Email, Username or Password cannot be empty",
                            FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();

                }
                else{
                    ParseUser twitterUser = new ParseUser();
                 twitterUser.setEmail(edtEmail.getText().toString());
                 twitterUser.setUsername(edtUsername.getText().toString());
                 twitterUser.setPassword(edtPassword.getText().toString());
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up");
                    progressDialog.show();
                 twitterUser.signUpInBackground(new SignUpCallback() {
                     @Override
                     public void done(ParseException e) {
                         if(e==null){
                             FancyToast.makeText(MainActivity.this,ParseUser.getCurrentUser().getUsername() +" is signed up successfully",
                                     FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                         } else{
                             FancyToast.makeText(MainActivity.this,e.getMessage(),
                                     FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                         }
                         progressDialog.dismiss();
                         transitionToTwitterUsersActivity();
                     }
                 });}

                 break;
             case  R.id.btnLogIn:
                 Intent intent = new Intent(MainActivity.this, LogIn.class);
                 startActivity(intent);
                 break;
         }
     }

     public void signUpLayoutIsTapped(View view){
         try{
             InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
             inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

         }catch (Exception e){
             e.printStackTrace();
         }
     }
     public void transitionToTwitterUsersActivity(){
        Intent intent = new Intent(MainActivity.this,TwitterUsers.class);
        startActivity(intent);
        finish();
     }
 }
