package com.rikin.jain.twitterclone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SendTweet extends AppCompatActivity {
    private EditText edtTweet;
    private Button btnSendTweet, btnOtherTweets;
    private ListView viewTweetListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);
        setTitle("Tweet");
        edtTweet = findViewById(R.id.edtTweet);
       viewTweetListView = findViewById(R.id.viewTweetsListView);
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
        btnOtherTweets = findViewById(R.id.btnOtherTweets);
        btnOtherTweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<HashMap<String,String>> tweetList = new ArrayList<>();
                final SimpleAdapter adapter = new SimpleAdapter(SendTweet.this, tweetList, android.R.layout.simple_list_item_2, new String[]{"tweetUsername", "tweetValue"}, new int[]{android.R.id.text1,android.R.id.text2});
                try {
                    ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Tweets");
                    parseQuery.whereContainedIn("username", ParseUser.getCurrentUser().getList("followerOf"));
                    parseQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if(objects.size() >0 && e==null){
                                for(ParseObject tweetObject: objects){
                                    HashMap<String,String> userTweet = new HashMap<>();
                                    userTweet.put("tweetUsername", tweetObject.getString("username"));
                                    userTweet.put("tweetValue", tweetObject.getString("tweet"));
                                    tweetList.add(userTweet);
                                }
                                viewTweetListView.setAdapter(adapter);
                            }
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        btnOtherTweets = findViewById(R.id.btnOtherTweets);
        btnOtherTweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<HashMap<String,String>> tweetList = new ArrayList<>();
                final SimpleAdapter adapter = new SimpleAdapter(SendTweet.this, tweetList, android.R.layout.simple_list_item_2, new String[]{"tweetUsername", "tweetValue"}, new int[]{android.R.id.text1,android.R.id.text2});
                try {
                    ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("Tweets");
                    parseQuery.whereContainedIn("username", ParseUser.getCurrentUser().getList("followerOf"));
                    parseQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if(objects.size() >0 && e==null){
                                for(ParseObject tweetObject: objects){
                                    HashMap<String,String> userTweet = new HashMap<>();
                                    userTweet.put("tweetUsername", tweetObject.getString("username"));
                                    userTweet.put("tweetValue", tweetObject.getString("tweet"));
                                    tweetList.add(userTweet);
                                }
                                viewTweetListView.setAdapter(adapter);
                            }
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



    }

}
