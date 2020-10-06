package com.nutro.biosint.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.nutro.biosint.R;
import com.nutro.biosint.manageractivity.DrawerActivityManager;
import com.nutro.biosint.utils.AppConstants;
import com.nutro.biosint.utils.PreferenceUtil;

public class SplashScreen extends AppCompatActivity {

    private String user_id;
    private int role;

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

            Intent intent = null;

            user_id = PreferenceUtil.getValueString(SplashScreen.this, PreferenceUtil.USERID);
            role = PreferenceUtil.getValueInt(SplashScreen.this, PreferenceUtil.USER_ROLE);

            if (user_id != null && AppConstants.ADMIN_ROLE == role) {
                intent = new Intent(SplashScreen.this, DrawerActivityManager.class);

            } else if (user_id != null && AppConstants.EMP_ROLE == role) {
                intent = new Intent(SplashScreen.this, DrawerActivityEmployee.class);

            } else if (user_id == null) {
                intent = new Intent(SplashScreen.this, LoginActivity.class);
            }

            startActivity(intent);
            finish();

        }
    }
}