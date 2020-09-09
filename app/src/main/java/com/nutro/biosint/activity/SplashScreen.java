package com.nutro.biosint.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.nutro.biosint.R;
import com.nutro.biosint.utils.PreferenceUtil;

public class SplashScreen extends AppCompatActivity {

    String user_id;
    int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new SplashDownCountDown(3000, 1000).start();
    }

    private class SplashDownCountDown extends CountDownTimer {

        SplashDownCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);


        }

        @Override
        public void onTick(long milliSecond) {

        }

        @Override
        public void onFinish() {

            Intent intent;

            user_id=PreferenceUtil.getValueString(SplashScreen.this, PreferenceUtil.USERID);

            if (user_id==null) {
                //intent = new Intent(SplashScreen.this, Log.class);

            } else {
                //intent = new Intent(SplashScreen.this, BottomTabbedActivity.class);

            }

            //startActivity(intent);
            finish();

        }
    }
}