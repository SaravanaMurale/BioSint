package com.nutro.biosint.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.nutro.biosint.R;

import static com.nutro.biosint.utils.MathUtil.validateEmail;
import static com.nutro.biosint.utils.MathUtil.validateMobile;
import static com.nutro.biosint.utils.MathUtil.validateName;

public class SignupActivity extends AppCompatActivity {

    private Button createAccount;
    private EditText contactPerson;
    private EditText contactEmail;
    private EditText contactMobileNum;
    private EditText contactCompanyName;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();


        initView();

        contactPerson.addTextChangedListener(new MyTextWatcher(contactPerson));
        contactEmail.addTextChangedListener(new MyTextWatcher(contactEmail));
        contactMobileNum.addTextChangedListener(new MyTextWatcher(contactMobileNum));
        contactCompanyName.addTextChangedListener(new MyTextWatcher(contactCompanyName));

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.createUserWithEmailAndPassword(contactEmail.getText().toString(), "").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            String userId = mAuth.getCurrentUser().getUid();
                            String email = mAuth.getCurrentUser().getPhoneNumber();

                            System.out.println("EmailAndUserId " + userId + " " + email);

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


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

            String personName = contactPerson.getText().toString().trim();
            String email = contactEmail.getText().toString().trim();
            String mobileNum = contactMobileNum.getText().toString().trim();
            String companyName = contactCompanyName.getText().toString().trim();

            createAccount.setEnabled(validateMobile(mobileNum) && validateEmail(email) && validateName(personName) && validateName(companyName));

            if (createAccount.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                }
            } else if (!createAccount.isEnabled()) {

            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void initView() {



        contactEmail = (EditText) findViewById(R.id.login_email);
        contactMobileNum = (EditText) findViewById(R.id.login_mobile_number);
        contactCompanyName = (EditText) findViewById(R.id.login_company_name);
        contactPerson=(EditText)findViewById(R.id.login_contact_person);

        createAccount = (Button) findViewById(R.id.createAccount);

    }
}