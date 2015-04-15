package com.recipetresure;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeActivity extends ActionBarActivity {

    protected List<ParseObject> mRecipeData;
    protected SwipeRefreshLayout mSwipeRefreshLayout; //generate a progress bar
    private Toolbar mToolbar;
    GridView mListView;
    List<String> rn; //declare array list variable for custom adapter
    List<String> un; ////declare array list variable for custom adapter
    TextView SignIn,sortBy,SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mListView = (GridView) findViewById(R.id.listView);

        SignUp = (TextView) findViewById(R.id.signup);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PersonDetailActivity.class);
                startActivity(intent);
            }
        });

        sortBy = (TextView) findViewById(R.id.sortByUser);
        sortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SortByUserActivity.class);
                startActivity(intent);
            }
        });

        SignIn = (TextView) findViewById(R.id.signIn);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        navigationFragment drawerFragment = (navigationFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        drawerFragment.setUp(R.id.navigation_drawer_fragment,
                (DrawerLayout)findViewById(R.id.drawer_layout), mToolbar);

        //declare list variable as object of list
        rn = new ArrayList<String>();
        un = new ArrayList<String>();

        mRecipeData = new ArrayList<ParseObject>();
        //For refresh the Layout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

        setSupportProgressBarIndeterminate(true);
        //For fetch the data from parse database
        ParseQuery<ParseObject> recipeQuery = ParseQuery.getQuery("RecipeInfo");
        recipeQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                setSupportProgressBarIndeterminate(true);
                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                if (e == null) {
                    mRecipeData = parseObjects;
                    int i = 0;
                    for (ParseObject RecipeData : mRecipeData) {
                        rn.add(RecipeData.getString(Constant.KEY_RECIPE_TITLE));
                        un.add(RecipeData.getString(Constant.KEY_RECIPE_OWNER_USER));
                        Log.d("Parse fatch", rn.toString());
                        i++;
                    }

                    // call Custom adapter and set list of data
                    CustomAdapter adapter = new CustomAdapter(HomeActivity.this, rn, un);
                    mListView.setAdapter(adapter);

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(HomeActivity.this,DetailActivity.class);
                            // fetch data on particular item click event
                            intent.putExtra("username",mRecipeData.get(position).getString(Constant.KEY_RECIPE_OWNER_USER));
                            intent.putExtra("title",mRecipeData.get(position).getString(Constant.KEY_RECIPE_TITLE));
                            intent.putExtra("ingredients",mRecipeData.get(position).getString(Constant.KEY_RECIPE_INGREDIENTS));
                            intent.putExtra("details",mRecipeData.get(position).getString(Constant.KEY_DESCRIPTION_RECIPE));
                            startActivity(intent);
                        }
                    });
                }
            }
        });

        // recipeQuery1.orderByDescending(Constant.KEY_CREATED_AT);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
        //on click event it redirect sub activity
        if (id == R.id.navigation_drawer_fragment) {
            startActivity(new Intent(this, SortByUserActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}


