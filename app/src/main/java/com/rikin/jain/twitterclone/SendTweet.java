package com.rikin.jain.twitterclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SendTweet extends AppCompatActivity {
    private EditText edtTweet;
    private Button btnSendTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);
        setTitle("Tweet");
        edtTweet = findViewById(R.id.edtTweet);
        btnSendTweet = findViewById(R.id.btnSendTweet);
        btnSendTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject parseObject = new ParseObject("Tweets");
                parseObject.put("tweet",edtTweet.getText().toString());
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            FancyToast.makeText(SendTweet.this, "Your tweet is sent", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        }
                    }
                });
            }
        });


    }

}
