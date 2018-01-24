package com.example.evertsmits.hometory.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by Evert Smits
 * SplashActivity is the engine behind the splash screen
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * onCreate function for the splashActivity
     * @param savedInstanceState default variable
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, CategoryOverviewActivity.class);
                startActivity(i);
            }
        }, 2000);
    }

}
