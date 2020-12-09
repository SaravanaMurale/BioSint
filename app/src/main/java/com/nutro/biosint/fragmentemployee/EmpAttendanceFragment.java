package com.nutro.biosint.fragmentemployee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutro.biosint.R;
import com.skyfishjy.library.RippleBackground;

public class EmpAttendanceFragment extends Fragment {

    Button btnLogin;
    RippleBackground rippleBackground;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_attendance_fragment, container, false);

        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        rippleBackground = (RippleBackground) view.findViewById(R.id.rippleEffect);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String btnText=btnLogin.getText().toString();
                System.out.println("BTNTEXT"+btnText);

                if (btnLogin.getText().toString().equals("Login")) {
                    btnLogin.setText("Logout");
                    rippleBackground.startRippleAnimation();
                } else if (btnLogin.getText().toString().equals("Logout")) {
                    btnLogin.setText("Login");
                    rippleBackground.stopRippleAnimation();
                }
            }
        });


        return view;
    }
}
