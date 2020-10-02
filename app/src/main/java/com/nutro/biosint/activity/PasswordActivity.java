package com.nutro.biosint.activity;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nutro.biosint.R;
import com.nutro.biosint.manager.DrawerActivityManager;

import static com.nutro.biosint.utils.MathUtil.validatePassword;

public class PasswordActivity extends AppCompatActivity {

    private EditText newPassword, confirmPassword;
    private Button btn_password_submit;

    String email = "";

    private FirebaseFirestore db;
    private CollectionReference addUserCollection;
    private DocumentReference addUserDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        newPassword = (EditText) findViewById(R.id.edit_new_password);
        confirmPassword = (EditText) findViewById(R.id.edit_confirm_password);

        btn_password_submit = (Button) findViewById(R.id.btn_submit);

        initFireStore();
        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");

        addUserCollection = db.collection("User");
        addUserDocument = addUserCollection.document(email);


        newPassword.addTextChangedListener(new MyTextWatcher(newPassword));
        confirmPassword.addTextChangedListener(new MyTextWatcher(confirmPassword));

        btn_password_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassword();
            }
        });

    }


    private void setPassword() {

        //String pwd=newPassword.getText().toString();
        //UserDTO userDTO = new UserDTO(newPassword.getText().toString());
        addUserDocument.update("password", newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(PasswordActivity.this, DrawerActivityManager.class);
                    startActivity(intent);
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

            String password = newPassword.getText().toString().trim();
            String confirm_Password = confirmPassword.getText().toString().trim();

            btn_password_submit.setEnabled(validatePassword(confirm_Password) && validatePassword(password) && password.equals(confirm_Password));

            if (btn_password_submit.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btn_password_submit.setBackground(getDrawable(R.drawable.background_rectangle_shape));
                    btn_password_submit.setTextColor(getApplication().getResources().getColor(R.color.white));
                }
            } else if (!btn_password_submit.isEnabled()) {
                btn_password_submit.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btn_password_submit.setBackground(getDrawable(R.color.gray));
                    //btn_password_submit.setTextColor(R.color.black);
                }
            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void initFireStore() {

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

    }
}