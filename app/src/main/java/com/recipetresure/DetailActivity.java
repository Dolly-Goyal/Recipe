package com.recipetresure;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;


public class DetailActivity extends ActionBarActivity {
    TextView mPersonId, mRecipetitle;
    TextView mIngredients, mDescription;
    RatingBar mRatingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPersonId = (TextView)findViewById(R.id.pId);
        mRecipetitle = (TextView)findViewById(R.id.rtitle);
        mIngredients = (TextView)findViewById(R.id.Ingredients);
        mDescription = (TextView)findViewById(R.id.Description);
        mRatingBar = (RatingBar)findViewById(R.id.ratingBar2);

        //get data from listView
        String mRecipeUserId = getIntent().getStringExtra("username");
        String mRecipeTitle = getIntent().getStringExtra("title");
        String mRecipeIngredients = getIntent().getStringExtra("ingredients");
        String mRecipeDetails = getIntent().getStringExtra("details");

        mPersonId.setText(mRecipeUserId);
        mRecipetitle.setText(mRecipeTitle);
        mIngredients.setText(mRecipeIngredients);
        mDescription.setText(mRecipeDetails);
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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
