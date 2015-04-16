package com.recipetresure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class RecipeForm extends ActionBarActivity {
    EditText recipeName,recipeIngredients,recipeDescription,recipeType,recipeFamous;
    Button submitRecipe,logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_form);

        recipeName = (EditText) findViewById(R.id.recipeName);
        recipeIngredients = (EditText) findViewById(R.id.recipeIngredients);
        recipeDescription = (EditText) findViewById(R.id.recipeDetails);
        submitRecipe = (Button) findViewById(R.id.submitRecipe);
        logOut = (Button) findViewById(R.id.logOut);
        recipeType = (EditText) findViewById(R.id.recipeType);
        recipeFamous = (EditText) findViewById(R.id.recipeFamous);

        //After submit recipe redirect on same page
        //Recipe data save in parse
        submitRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseObject recipeDetail = new ParseObject("RecipeInfo");
                recipeDetail.put("Recipe_Name", recipeName.getText().toString());
                recipeDetail.put("Recipe_Ingredients", recipeIngredients.getText().toString());
                recipeDetail.put("Recipe_Details", recipeDescription.getText().toString());
                recipeDetail.put("Recipe_Owner_user_name", ParseUser.getCurrentUser().getUsername());
                recipeDetail.put("Type_recipe", ParseUser.getCurrentUser().getUsername());
                recipeDetail.put("Place", ParseUser.getCurrentUser().getUsername());

                recipeDetail.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                    }
                });

                Intent intent = new Intent(RecipeForm.this,RecipeForm.class);
                startActivity(intent);
                finish();
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                navigateToLogin();
            }
        });
    }
    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
