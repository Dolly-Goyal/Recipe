package com.recipetresure;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class SortByUserActivity extends ActionBarActivity {
    private ListView userNameList;
    protected List<ParseUser> mRecipeData;
    List<String> un1,rn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_by_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userNameList = (ListView) findViewById(R.id.userNameData);
        un1 = new ArrayList<String>();

        setSupportProgressBarIndeterminate(true);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.orderByDescending("username");

        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                mRecipeData = parseUsers;
                for(ParseUser u :mRecipeData) {
                    un1.add(u.getUsername());
                }
                ArrayAdapter adapter = new ArrayAdapter(SortByUserActivity.this,android.R.layout.simple_list_item_1,un1);
                userNameList.setAdapter(adapter);

            }
        });
        userNameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SortByUserActivity.this, UserDataActivity.class);
                mRecipeData.get(position).getUsername();
                intent.putExtra("userName", mRecipeData.get(position).getUsername());
                System.out.println(" UserName " + mRecipeData.get(position).getUsername());
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sort_by_user, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
