package com.nutro.biosint.fragmentmanager;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nutro.biosint.R;
import com.nutro.biosint.activity.SignupActivity;
import com.nutro.biosint.modelrequest.AddEmployeeDTO;
import com.nutro.biosint.utils.AppConstants;
import com.nutro.biosint.utils.PreferenceUtil;

import static com.nutro.biosint.utils.MathUtil.validateEmail;
import static com.nutro.biosint.utils.MathUtil.validateMobile;
import static com.nutro.biosint.utils.MathUtil.validateName;
import static com.nutro.biosint.utils.MathUtil.validatePassword;

public class AddEmployeeFragment extends Fragment {

    Button btnAddEmp;

    private EditText login_emp_email, login_emp_password, login_emp_name, login_emp_mobile, login_emp_design, login_emp_shift_start_time, login_emp_shift_end_time;

    FirebaseAuth mAuth;

    FirebaseFirestore db;
    CollectionReference addEmployeeCollection;
    DocumentReference addEmployeeDocument;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_add_employee_fragment, container,false);

        FirebaseApp.initializeApp(getActivity());
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        addEmployeeCollection = db.collection("User");

        btnAddEmp = (Button) view.findViewById(R.id.btnAddEmp);

        login_emp_email = (EditText) view.findViewById(R.id.login_emp_email);
        login_emp_password = (EditText) view.findViewById(R.id.login_emp_password);
        login_emp_name = (EditText) view.findViewById(R.id.login_emp_name);
        login_emp_mobile = (EditText) view.findViewById(R.id.login_emp_mobile);
        login_emp_design = (EditText) view.findViewById(R.id.login_emp_design);
        login_emp_shift_start_time = (EditText) view.findViewById(R.id.login_emp_shift_start_time);
        login_emp_shift_end_time = (EditText) view.findViewById(R.id.login_emp_shift_end_time);

        login_emp_email.addTextChangedListener(new MyTextWatcher(login_emp_email));
        login_emp_password.addTextChangedListener(new MyTextWatcher(login_emp_password));
        login_emp_name.addTextChangedListener(new MyTextWatcher(login_emp_name));
        login_emp_mobile.addTextChangedListener(new MyTextWatcher(login_emp_mobile));
        login_emp_design.addTextChangedListener(new MyTextWatcher(login_emp_design));
        login_emp_shift_start_time.addTextChangedListener(new MyTextWatcher(login_emp_shift_start_time));
        login_emp_shift_end_time.addTextChangedListener(new MyTextWatcher(login_emp_shift_end_time));

        return view;
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

            final String email = login_emp_email.getText().toString().trim();
            final String password = login_emp_password.getText().toString().trim();
            final String name = login_emp_name.getText().toString().trim();
            final String mobileNum = login_emp_mobile.getText().toString().trim();
            final String empDesignation = login_emp_design.getText().toString().trim();
            final String shiftStartTime = login_emp_shift_start_time.getText().toString().trim();
            final String shiftEndTime = login_emp_shift_end_time.getText().toString().trim();

            /*btnAddEmp.setEnabled(validateEmail(email) && validatePassword(password) && validateName(name) && validateMobile(mobileNum) && validateName(empDesignation)
                    && validateName(shiftStartTime) && validateName(shiftEndTime)
            );*/

            if (btnAddEmp.isEnabled()) {

                btnAddEmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    String empUserId = mAuth.getCurrentUser().getUid();
                                    String empEmail = mAuth.getCurrentUser().getEmail();

                                    addEmployeeDocument = addEmployeeCollection.document(empEmail);
                                    String managerUserId = PreferenceUtil.getValueString(getContext(), PreferenceUtil.USERID);

                                    AddEmployeeDTO addEmployeeDTO=new AddEmployeeDTO(empUserId,managerUserId,name,email,password,mobileNum,AppConstants.getDeviceID(getActivity()),empDesignation,shiftStartTime,shiftEndTime,AppConstants.EMP_ROLE,true);

                                    addEmployeeDocument.set(addEmployeeDTO);


                                }

                            }
                        });
                    }
                });

            } else if (!btnAddEmp.isEnabled()) {

            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }


}
