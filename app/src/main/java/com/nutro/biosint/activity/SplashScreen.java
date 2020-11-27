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

    private String manager_user_id;
    private String my_user_id;
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

            manager_user_id = PreferenceUtil.getValueString(SplashScreen.this, PreferenceUtil.MY_MANAGER_USER_ID);
            my_user_id = PreferenceUtil.getValueString(SplashScreen.this, PreferenceUtil.MY_USER_ID);
            role = PreferenceUtil.getValueInt(SplashScreen.this, PreferenceUtil.USER_ROLE);


            if (manager_user_id != null && AppConstants.MANAGER_ROLE == role) {
                intent = new Intent(SplashScreen.this, DrawerActivityManager.class);

            } else if (my_user_id != null && AppConstants.EMP_ROLE == role) {
                intent = new Intent(SplashScreen.this, DrawerActivityEmployee.class);

            } else if (manager_user_id == null && my_user_id == null) {
                intent = new Intent(SplashScreen.this, LoginActivity.class);
            }

            startActivity(intent);
            finish();

        }
    }
}