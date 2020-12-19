package com.nutro.biosint.fragmentemployee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.nutro.biosint.R;
import com.nutro.biosint.modelresponse.UserResponse;
import com.nutro.biosint.utils.PreferenceUtil;

public class EmployeeProfileFragment extends Fragment {

    FirebaseFirestore db;
    CollectionReference getMyDetailsCollection;
    DocumentReference documentReference;

    RelativeLayout userNameBlock, mobileNumberBlock, emailBlock, passwordBlock;

    TextView userNameEditText, mobileNumberEditText, emailEditText;
    String email;

    private interface UserRespnseListener {
        public void getUserResponse(UserResponse userResponse);
    }

    private interface UpdateUserListener {
        public void updateUserDetails();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.employee_profile_fragment, container, false);

        db = FirebaseFirestore.getInstance();
        getMyDetailsCollection = db.collection("User");


        userNameBlock = (RelativeLayout) view.findViewById(R.id.userNameBlock);
        mobileNumberBlock = (RelativeLayout) view.findViewById(R.id.mobileNumberBlock);
        emailBlock = (RelativeLayout) view.findViewById(R.id.emailBlock);
        passwordBlock = (RelativeLayout) view.findViewById(R.id.passwordBlock);

        userNameEditText = (TextView) view.findViewById(R.id.userName);
        mobileNumberEditText = (TextView) view.findViewById(R.id.mobileNumber);
        emailEditText = (TextView) view.findViewById(R.id.email);

        dogetUserDetails(new UserRespnseListener() {
            @Override
            public void getUserResponse(UserResponse userResponse) {

                userNameEditText.setText(userResponse.getName());
                mobileNumberEditText.setText(userResponse.getMobileNumber());
                emailEditText.setText(userResponse.getEmail());
                email = userResponse.getEmail();

            }
        });

        userNameBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("Enter Your Name", 1);
            }
        });
        mobileNumberBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("Enter Mobile Number", 2);
            }
        });

        emailBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "You Can't Update E-Mail", Toast.LENGTH_LONG).show();
                // openDialog("Enter Email", 3);
            }
        });

        passwordBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog("Enter Password", 4);
            }
        });


        return view;
    }

    private void openDialog(final String hintData, final int i) {

        // dialog=LoaderUtil.showProgressBar(getActivity());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.layout_dialog_userprofile, null);

        final EditText update = (EditText) view.findViewById(R.id.updateName);
        update.setHint(hintData);

        builder.setView(view);

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getActivity(), "Updated", Toast.LENGTH_LONG).show();

                updateUserDetails(update.getText().toString(), i, new UpdateUserListener() {
                    @Override
                    public void updateUserDetails() {

                        dogetUserDetails(new UserRespnseListener() {
                            @Override
                            public void getUserResponse(UserResponse userResponse) {

                                userNameEditText.setText(userResponse.getName());
                                mobileNumberEditText.setText(userResponse.getMobileNumber());
                                emailEditText.setText(userResponse.getEmail());
                                email = userResponse.getEmail();

                            }
                        });


                    }
                });


            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void updateUserDetails(String toString, int i, final UpdateUserListener updateUserListener) {

        documentReference = getMyDetailsCollection.document(email);

        if (i == 1) {
            //name
            documentReference.update("name", toString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(getContext(), "Name Updated Successfully", Toast.LENGTH_LONG).show();

                    updateUserListener.updateUserDetails();

                }
            });

        } else if (i == 2) {
            //mobile

            documentReference.update("mobileNumber", toString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(getContext(), "Mobile Number Updated Successfully", Toast.LENGTH_LONG).show();

                    updateUserListener.updateUserDetails();

                }
            });


        } else if (i == 3) {
            //email

        } else if (i == 4) {
            //passwore

            documentReference.update("password", toString).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                    Toast.makeText(getContext(), "Password Updated Successfully", Toast.LENGTH_LONG).show();

                    updateUserListener.updateUserDetails();

                }
            });


        }

    }

    private void dogetUserDetails(final UserRespnseListener userRespnseListener) {

        getMyDetailsCollection
                .whereEqualTo("userId", PreferenceUtil.getValueString(getContext(), PreferenceUtil.MY_USER_ID))
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    UserResponse userResponse = documentSnapshot.toObject(UserResponse.class);

                    userRespnseListener.getUserResponse(userResponse);

                }


            }
        });

    }
}
