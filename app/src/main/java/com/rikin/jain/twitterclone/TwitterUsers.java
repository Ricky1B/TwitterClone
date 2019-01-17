package com.rikin.jain.twitterclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class TwitterUsers extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listView;
    private ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_users);
        setTitle("Users");
        FancyToast.makeText(this, "Welcome " +ParseUser.getCurrentUser().getUsername(), FancyToast.LENGTH_SHORT, FancyToast.INFO,false ).show();
        listView = findViewById(R.id.listView);
        arrayList = new ArrayList();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked, arrayList);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(this);
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if( objects.size() > 0 && e == null){
                    for(ParseUser user : objects){
                        arrayList.add(user.getUsername());

                    }
                    listView.setAdapter(arrayAdapter);
                    for(String twitteruser: arrayList){
                        if(ParseUser.getCurrentUser().getList("followerOf") != null){
                            if(ParseUser.getCurrentUser().getList("followerOf").contains(twitteruser)){
                                listView.setItemChecked(arrayList.indexOf(twitteruser),true);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logoutUserItem){
            ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        Intent intent = new Intent(TwitterUsers.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });


        }
        else if(item.getItemId() == R.id.send_tweet){
            Intent intent= new Intent(TwitterUsers.this, SendTweet.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckedTextView checkedTextView = (CheckedTextView) view;
        if(checkedTextView.isChecked()){
            FancyToast.makeText(this, "You are now following " + arrayList.get(position),FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
             ParseUser.getCurrentUser().add("followerOf", arrayList.get(position));
        } else {
            FancyToast.makeText(this, "You unfollowed " + arrayList.get(position),FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
             ParseUser.getCurrentUser().getList("followerOf").remove(arrayList.get(position));
             List currentUserFollowerOfList = ParseUser.getCurrentUser().getList("followerOf");
             ParseUser.getCurrentUser().remove("followerOf");
             ParseUser.getCurrentUser().put("followerOf", currentUserFollowerOfList);
        }
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                System.out.println("Saved");
            }
        });
    }

}
