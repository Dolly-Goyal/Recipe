package com.recipetresure;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import java.util.Date;
import java.util.List;


public class UserDataActivity extends ActionBarActivity {
    protected List<ParseObject> mRecipeData;
    ListView UserRecipeList;
    String s;
    List<ParseObject> po;
    List<String> rName;
    List<Date> date;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        ParseQuery<ParseUser> userParseQuery = ParseUser.getQuery();

        s = getIntent().getStringExtra("userName");
        UserRecipeList = (ListView) findViewById(R.id.particularUserData);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        navigationFragment drawerFragment = (navigationFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        drawerFragment.setUp(R.id.navigation_drawer_fragment,
                (DrawerLayout)findViewById(R.id.drawer_layout), mToolbar);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("RecipeInfo");
        query.whereEqualTo(Constant.KEY_RECIPE_OWNER_USER, s);
        rName = new ArrayList<String>();
        date =new ArrayList<Date>();

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                po = parseObjects;

                for (ParseObject ob :po){
                    rName.add(ob.getString(Constant.KEY_RECIPE_TITLE));
                    date.add(ob.getCreatedAt());
                    System.out.println("recipeName" + rName);
                    System.out.print("Date" +date);

                }
                UserDataAdapter adapter = new UserDataAdapter(UserDataActivity.this, rName, date);
                UserRecipeList.setAdapter(adapter);
            }
        });
        UserRecipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(UserDataActivity.this, DetailActivity.class);
                intent.putExtra("username",po.get(position).getString(Constant.KEY_RECIPE_OWNER_USER));
                intent.putExtra("title",po.get(position).getString(Constant.KEY_RECIPE_TITLE));
                intent.putExtra("ingredients",po.get(position).getString(Constant.KEY_RECIPE_INGREDIENTS));
                intent.putExtra("details",po.get(position).getString(Constant.KEY_DESCRIPTION_RECIPE));
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_data, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
