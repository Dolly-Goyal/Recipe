package com.recipetresure;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by DELL on 26-03-2015.
 */
public class RecipeTresure extends Application {
    @Override
    public void onCreate() {
    super.onCreate();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this,
                "UNKiSbGQwHpspKZGXIRD4NnWLsFuEcAddeHfcDfX",
                "81B4UzLZrakahLVm2NYlH8F1qZfiq5cI6VZxaP26");
    }
}
