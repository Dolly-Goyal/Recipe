package com.recipetresure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class PersonDetailActivity extends Activity {
    EditText mPersonName,mUserName,mPassword;
    Button mSubmit,mCancel;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        mPersonName = (EditText) findViewById(R.id.name);
        mUserName = (EditText) findViewById(R.id.emailId);
        mPassword = (EditText) findViewById(R.id.password);
        mSubmit = (Button) findViewById(R.id.submit);
        mCancel = (Button) findViewById(R.id.cancel);
        mTextView = (TextView) findViewById(R.id.nameRecipe);
        mTextView.setShadowLayer(5,3,1, Color.RED);

        //Create DropDown Menu For Recipe Type
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.recipe_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonDetailActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String personName = mPersonName.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String userName = mUserName.getText().toString().trim();

                if (personName.isEmpty() || password.isEmpty() || userName.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PersonDetailActivity.this);
                    builder.setTitle("Oops!!!").setMessage(R.string.error_msg)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                    setProgressBarIndeterminateVisibility(true);
                    ParseUser newuser = new ParseUser();
                    newuser.setUsername(personName);
                    newuser.setEmail(userName);
                    newuser.setPassword(password);

                    newuser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            setProgressBarIndeterminateVisibility(false);

                            if (e==null) {
                                Intent intent = new Intent(PersonDetailActivity.this, RecipeForm.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(PersonDetailActivity.this);
                                builder.setTitle("Oops!").setMessage(e.getMessage())
                                        .setPositiveButton(android.R.string.ok, null);

                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
             }
        });
    }
}
