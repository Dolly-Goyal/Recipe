package com.recipetresure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends Activity {
    protected EditText mUserName,mPassword;
    protected Button mLogIn;
    private Button mSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);

        mUserName = (EditText) findViewById(R.id.loginUserName);
        mPassword = (EditText) findViewById(R.id.logInPassword);
        mLogIn = (Button) findViewById(R.id.logIn);
        mSignUp = (Button) findViewById(R.id.signUp);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,PersonDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUserName.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("Oops!").setMessage(R.string.login_error_msg)
                            .setPositiveButton(android.R.string.ok, null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else {
                        //Login
                        setProgressBarIndeterminateVisibility(true);
                        ParseUser.logInInBackground(username, password, new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                setProgressBarIndeterminateVisibility(false);

                                if (e == null) {
                                    //success!
                                    Intent intent = new Intent(LoginActivity.this, RecipeForm.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

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
