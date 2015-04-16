package com.recipetresure;

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
import java.util.List;


public class SortByType extends ActionBarActivity {
    private ListView recipeTypeList;
    protected List<ParseObject> mRecipeData;
    List<String> tr;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_by_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recipeTypeList = (ListView) findViewById(R.id.typerecipelist);
        tr = new ArrayList<String>();

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        navigationFragment drawerFragment = (navigationFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragment);
        drawerFragment.setUp(R.id.navigation_drawer_fragment,
                (DrawerLayout)findViewById(R.id.drawer_layout), mToolbar);

        setSupportProgressBarIndeterminate(true);
        ParseQuery<ParseObject> recipeQuery1 = ParseQuery.getQuery("RecipeInfo");
        recipeQuery1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                mRecipeData = parseObjects;
                int i = 0;
                for (ParseObject t: mRecipeData) {
                    tr.add(t.getString(Constant.KEY_TYPE));
                    i++;
                }
                ArrayAdapter adapter = new ArrayAdapter(SortByType.this,android.R.layout.simple_list_item_1,tr);
                recipeTypeList.setAdapter(adapter);
            }
        });
        recipeTypeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SortByType.this, TypeDataActivity.class);
                intent.putExtra("Type",mRecipeData.get(position).getString(Constant.KEY_TYPE));
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sort_by_type, menu);
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
