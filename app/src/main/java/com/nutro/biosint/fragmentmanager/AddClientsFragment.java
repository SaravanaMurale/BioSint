package com.nutro.biosint.fragmentmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nutro.biosint.R;

public class AddClientsFragment extends Fragment {


    RadioGroup radioGroup;
    RadioButton genderradioButton;
    Button btn_addClient;

    int selectedId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.manager_add_clients_fragment, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        btn_addClient = (Button) view.findViewById(R.id.btn_addClient);

        btn_addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedId = radioGroup.getCheckedRadioButtonId();
                genderradioButton = (RadioButton) view.findViewById(selectedId);

                if (selectedId == -1) {
                    Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), genderradioButton.getText().toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }


}
