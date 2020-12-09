package com.nutro.biosint.fragmentemployee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.nutro.biosint.R;
import com.nutro.biosint.modelrequest.EmpAttendanceDTO;
import com.nutro.biosint.utils.AppConstants;
import com.nutro.biosint.utils.MathUtil;
import com.nutro.biosint.utils.PreferenceUtil;
import com.skyfishjy.library.RippleBackground;

public class EmpAttendanceFragment extends Fragment {

    Button btnLogin;
    RippleBackground rippleBackground;

    private FirebaseFirestore db;
    private CollectionReference addEmpAttendanceTimeCollection;
    private DocumentReference addEmpAttendanceTimeDocument;

    interface EmpLoginClickListener {
        void empLoginClick();
    }


    interface EmpLogoutClickListener {
        void empLogoutClick();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_attendance_fragment, container, false);

        db = FirebaseFirestore.getInstance();
        addEmpAttendanceTimeCollection = db.collection("EmpAttendance");
        //addSignoutdoc=addEmpAttendanceTimeCollection.document();


        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        rippleBackground = (RippleBackground) view.findViewById(R.id.rippleEffect);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String btnText = btnLogin.getText().toString();
                System.out.println("BTNTEXT" + btnText);

                if (btnLogin.getText().toString().equals(AppConstants.LOGIN)) {
                    btnLogin.setText(AppConstants.LOGOUT);
                    rippleBackground.startRippleAnimation();

                    addLoginTimeInFireStore();


                } else if (btnLogin.getText().toString().equals(AppConstants.LOGOUT)) {
                    btnLogin.setText(AppConstants.LOGIN);
                    rippleBackground.stopRippleAnimation();

                    addLogoutTimeInFireStore();
                }
            }
        });


        return view;
    }


    private void addLoginTimeInFireStore() {

        addLoginTime(new EmpLoginClickListener() {
            @Override
            public void empLoginClick() {

                Toast.makeText(getActivity(), "You logged in successfully", Toast.LENGTH_LONG).show();

            }
        });


    }

    private void addLoginTime(final EmpLoginClickListener empLoginClickListener) {

        addEmpAttendanceTimeDocument = addEmpAttendanceTimeCollection.document();

        EmpAttendanceDTO empAttendanceDTO = new EmpAttendanceDTO(PreferenceUtil.getValueString(getContext(), PreferenceUtil.MY_MANAGER_USER_ID), PreferenceUtil.getValueString(getContext(), PreferenceUtil.MY_USER_ID), MathUtil.date(), MathUtil.time());
        addEmpAttendanceTimeDocument.set(empAttendanceDTO).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    empLoginClickListener.empLoginClick();
                }

            }
        });


    }


    private void addLogoutTimeInFireStore() {

        addLogoutTime(new EmpLogoutClickListener() {
            @Override
            public void empLogoutClick() {

                Toast.makeText(getActivity(), "You logged out successfully", Toast.LENGTH_LONG).show();

            }
        });

    }

    private void addLogoutTime(final EmpLogoutClickListener empLogoutClickListener) {

        addEmpAttendanceTimeDocument = addEmpAttendanceTimeCollection.document();
        EmpAttendanceDTO empAttendanceDTO = new EmpAttendanceDTO(PreferenceUtil.getValueString(getContext(), PreferenceUtil.MY_MANAGER_USER_ID), PreferenceUtil.getValueString(getContext(), PreferenceUtil.MY_USER_ID), MathUtil.date(), MathUtil.time());
        addEmpAttendanceTimeDocument.set(empAttendanceDTO).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    empLogoutClickListener.empLogoutClick();
                }

            }
        });


    }
}
