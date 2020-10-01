package com.nutro.biosint.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nutro.biosint.R;

import static com.nutro.biosint.utils.MathUtil.validatePassword;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);

        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginEmail.addTextChangedListener(new MyTextWatcher(loginEmail));
        loginPassword.addTextChangedListener(new MyTextWatcher(loginPassword));
        
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                dogetLoginDetails();
                
            }
        });

    }

    private void dogetLoginDetails() {
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String email = loginEmail.getText().toString().trim();
            String password = loginPassword.getText().toString().trim();

            loginBtn.setEnabled(validatePassword(email) && validatePassword(password));

            if (loginBtn.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    loginBtn.setBackground(getDrawable(R.drawable.background_rectangle_shape));
                    loginBtn.setTextColor(getApplication().getResources().getColor(R.color.white));
                }
            } else if (!loginBtn.isEnabled()) {
                loginBtn.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    loginBtn.setBackground(getDrawable(R.color.gray));
                    //btn_password_submit.setTextColor(R.color.black);
                }
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}