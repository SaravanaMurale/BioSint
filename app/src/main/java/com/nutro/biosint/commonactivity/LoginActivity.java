package com.nutro.biosint.commonactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nutro.biosint.R;
import com.nutro.biosint.employeeactivity.DrawerActivityEmployee;
import com.nutro.biosint.manageractivity.DrawerActivityManager;
import com.nutro.biosint.modelresponse.UserResponse;
import com.nutro.biosint.utils.PreferenceUtil;
import com.nutro.biosint.utils.ToastUtils;

import static com.nutro.biosint.utils.MathUtil.validatePassword;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private Button loginBtn;

    TextView callSignUpActivity;

    private FirebaseFirestore firebaseFirestore;
    private CollectionReference userCollectionRef;
    private DocumentReference userDocumentRef;

    private interface UserCallBackListener {

        public void getUserData(UserResponse userResponse);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPassword = (EditText) findViewById(R.id.login_password);

        loginBtn = (Button) findViewById(R.id.loginBtn);

        callSignUpActivity = (TextView) findViewById(R.id.callSignUpActivity);

        initFireStore();

        loginEmail.addTextChangedListener(new MyTextWatcher(loginEmail));
        loginPassword.addTextChangedListener(new MyTextWatcher(loginPassword));

        callSignUpActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dogetLoginDetails(new UserCallBackListener() {
                    @Override
                    public void getUserData(UserResponse userResponse) {

                        if (userResponse != null) {

                            System.out.println("USERID " + userResponse.getUserId());
                            System.out.println("USERROLE " + userResponse.getUserRole());
                            System.out.println("ManagerUserId " + userResponse.getManagerUserId());

                            if (userResponse.getManagerUserId() != null) {
                                //He is Employee
                                PreferenceUtil.setValueString(LoginActivity.this, PreferenceUtil.MY_MANAGER_USER_ID, userResponse.getManagerUserId());
                            }

                            //He is manager
                            PreferenceUtil.setValueString(LoginActivity.this, PreferenceUtil.MY_USER_ID, userResponse.getUserId());
                            PreferenceUtil.setValueSInt(LoginActivity.this, PreferenceUtil.USER_ROLE, userResponse.getUserRole());

                            launchHomeActivity();
                        } else if (userResponse == null) {
                            ToastUtils.getInstance(LoginActivity.this).showLongToast(getString(R.string.wrong_username));
                        }

                    }
                });

            }
        });

    }

    private void launchHomeActivity() {
        Intent intent = null;
        if (PreferenceUtil.getValueInt(LoginActivity.this, PreferenceUtil.USER_ROLE) == 1) {
            intent = new Intent(LoginActivity.this, DrawerActivityManager.class);

        } else if (PreferenceUtil.getValueInt(LoginActivity.this, PreferenceUtil.USER_ROLE) == 2) {
            intent = new Intent(LoginActivity.this, DrawerActivityEmployee.class);
        }
        startActivity(intent);


    }

    private void initFireStore() {

        firebaseFirestore = FirebaseFirestore.getInstance();
        userCollectionRef = firebaseFirestore.collection("User");

    }

    private void dogetLoginDetails(final UserCallBackListener userCallBackListener) {
        //userDocumentRef = userCollectionRef.document(loginEmail.getText().toString());
        userCollectionRef
                .whereEqualTo("email",loginEmail.getText().toString())
                .whereEqualTo("password", loginPassword.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                    UserResponse userResponse = documentSnapshot.toObject(UserResponse.class);


                    userCallBackListener.getUserData(userResponse);


                }

            }
        });
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