package com.rikin.jain.twitterclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogIn extends AppCompatActivity implements View.OnClickListener {
    private EditText edtEmail2, edtPassword2;
    private Button btnLognIn2, btnSignUp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        setTitle("SignUp");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#427df4")));
        edtEmail2 = findViewById(R.id.edtEmail2);
        edtPassword2 = findViewById(R.id.edtPassword2);
        edtPassword2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnLognIn2);
                }
                return false;
            }
        });
        btnLognIn2 = findViewById(R.id.btnLogIn2);
        btnLognIn2.setOnClickListener(this);
        btnSignUp2 = findViewById(R.id.btnSignUp2);
        btnSignUp2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogIn2:
                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Logging In");
                progressDialog.show();
                ParseUser.logInInBackground(edtEmail2.getText().toString(), edtPassword2.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if( user != null && e== null){
                            FancyToast.makeText(LogIn.this,ParseUser.getCurrentUser().getUsername() +" is logged in successfully",
                                    FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        }
                        progressDialog.dismiss();
                        transitionToTwitterUsersActivity();
                    }
                });


                break;
            case R.id.btnSignUp2:
                finish();
                break;
        }
    }
    public void logInLayoutIsTapped(View view){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void transitionToTwitterUsersActivity(){
        Intent intent = new Intent(LogIn.this,TwitterUsers.class);
        startActivity(intent);
        finish();
    }
}
