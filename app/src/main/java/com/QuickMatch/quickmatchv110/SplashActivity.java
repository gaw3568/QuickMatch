package com.QuickMatch.quickmatchv110;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {
    //스플래시 시간
    final int SPLASH_DISPLAY_LENGTH = 2000;
    private SharedPreferences lp;
    private SharedPreferences.Editor lEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        lp = getSharedPreferences("login", MODE_PRIVATE);
        lEdit = lp.edit();

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (lp.getBoolean("auto_login", false) == false) {
                    Intent mainIntent = new Intent(getApplicationContext(), Login_Main.class);
                    startActivity(mainIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }else{
                    Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
