package com.nutro.biosint.commonactivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nutro.biosint.R;
import com.nutro.biosint.modelrequest.AddManagerDTO;
import com.nutro.biosint.modelresponse.UserResponse;
import com.nutro.biosint.utils.AppConstants;
import com.nutro.biosint.utils.PreferenceUtil;

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

    FirebaseFirestore db;
    CollectionReference addUserCollection;
    DocumentReference addUserDocument;

    public interface UserDetailsFirestoreCallBack {
        public void userDetailsCallBack(UserResponse userResponse);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initFireBase();


        initView();

        addUserCollection = db.collection("User");


        contactPerson.addTextChangedListener(new MyTextWatcher(contactPerson));
        contactEmail.addTextChangedListener(new MyTextWatcher(contactEmail));
        contactMobileNum.addTextChangedListener(new MyTextWatcher(contactMobileNum));
        contactCompanyName.addTextChangedListener(new MyTextWatcher(contactCompanyName));

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkUserInFireStore(new UserDetailsFirestoreCallBack() {
                    @Override
                    public void userDetailsCallBack(UserResponse userResponse) {

                        if (userResponse == null) {

                            mAuth.createUserWithEmailAndPassword(contactEmail.getText().toString(), "aaaaaa").addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        String userId = mAuth.getCurrentUser().getUid();
                                        String email = mAuth.getCurrentUser().getEmail();

                                        PreferenceUtil.setValueString(SignupActivity.this, PreferenceUtil.MY_USER_ID, userId);

                                        addUserDocument = addUserCollection.document(email);

                                        AddManagerDTO userDTO = new AddManagerDTO(userId, contactMobileNum.getText().toString(), AppConstants.getDeviceID(SignupActivity.this), true, AppConstants.MANAGER_ROLE, contactEmail.getText().toString(), "aaaaaa");

                                        addUserDocument.set(userDTO);

                                        System.out.println("EmailAndUserId " + userId + " " + email);

                                        launchPasswordActivity(email);

                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });


                        } else if (userResponse.getUserId() != null) {
                            PreferenceUtil.setValueString(SignupActivity.this, PreferenceUtil.MY_USER_ID, userResponse.getUserId());
                            launchPasswordActivity(userResponse.getEmail());
                        }

                    }
                });


            }
        });
    }

    private void launchPasswordActivity(String email) {

        Intent intent = new Intent(SignupActivity.this, PasswordActivity.class);
        intent.putExtra("EMAIL", email);
        startActivity(intent);

    }

    private void checkUserInFireStore(final UserDetailsFirestoreCallBack userDetailsFirestoreCallBack) {

        addUserDocument = addUserCollection.document(contactEmail.getText().toString());

        addUserDocument.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                //if (documentSnapshot.exists()) {

                UserResponse userResponse = documentSnapshot.toObject(UserResponse.class);

                userDetailsFirestoreCallBack.userDetailsCallBack(userResponse);

                //}

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Exception" + e.getMessage().toString());
                Toast.makeText(SignupActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
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
        public void afterTextChanged(Editable s) {

        }
    }

    private void initView() {

        contactEmail = (EditText) findViewById(R.id.login_email);
        contactMobileNum = (EditText) findViewById(R.id.login_mobile_number);
        contactCompanyName = (EditText) findViewById(R.id.login_company_name);
        contactPerson = (EditText) findViewById(R.id.login_contact_person);

        createAccount = (Button) findViewById(R.id.createAccount);

    }

    private void initFireBase() {
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
}