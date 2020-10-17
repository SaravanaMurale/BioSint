package com.nutro.biosint.fragmentmanager;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.OrderBy;
import com.nutro.biosint.R;
import com.nutro.biosint.modelrequest.AddClientDTO;
import com.nutro.biosint.utils.MathUtil;
import com.nutro.biosint.utils.PreferenceUtil;

import static com.nutro.biosint.utils.MathUtil.validateEmail;
import static com.nutro.biosint.utils.MathUtil.validateMobile;
import static com.nutro.biosint.utils.MathUtil.validateName;

public class AddClientsFragment extends Fragment {


    RadioGroup radioGroup;
    RadioButton genderradioButton;
    Button btn_addClient;
    int selectedId;

    EditText login_client_name, login_client_designation, login_client_organization, login_client_mobile, login_client_email, login_client_address, login_client_details;

    FirebaseFirestore db;
    CollectionReference addClientsCollection;
    DocumentReference addClientsDocument;

    interface AddClientListener {
        public void addClient();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.manager_add_clients_fragment, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        btn_addClient = (Button) view.findViewById(R.id.btn_addClient);

        initFormView(view);

        db = FirebaseFirestore.getInstance();
        addClientsCollection = db.collection("AddClients");

        login_client_name.addTextChangedListener(new MyTextWatcher(login_client_name));
        login_client_designation.addTextChangedListener(new MyTextWatcher(login_client_designation));
        login_client_organization.addTextChangedListener(new MyTextWatcher(login_client_organization));
        login_client_mobile.addTextChangedListener(new MyTextWatcher(login_client_mobile));
        login_client_email.addTextChangedListener(new MyTextWatcher(login_client_email));
        login_client_address.addTextChangedListener(new MyTextWatcher(login_client_address));
        login_client_details.addTextChangedListener(new MyTextWatcher(login_client_details));

        btn_addClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedId = radioGroup.getCheckedRadioButtonId();
                genderradioButton = (RadioButton) view.findViewById(selectedId);

                if (selectedId == -1) {
                    Toast.makeText(getActivity(), "Nothing selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), genderradioButton.getText().toString(), Toast.LENGTH_SHORT).show();

                    AddClientDTO addClientDTO = new AddClientDTO(login_client_name.getText().toString(), login_client_designation.getText().toString(), login_client_organization.getText().toString(), login_client_mobile.getText().toString(), login_client_email.getText().toString(), login_client_address.getText().toString(), login_client_details.getText().toString(), genderradioButton.getText().toString(), PreferenceUtil.getEmpUserId(getActivity()), "", MathUtil.dateAndTime(), false, 0, "");

                    addClientsInFireStore(addClientDTO, new AddClientListener() {
                        @Override
                        public void addClient() {
                            Toast.makeText(getActivity(), "Client Added Successfully", Toast.LENGTH_LONG).show();

                            getDocumentIdAndUpdate(new GetDocumentIdOfClientListener() {
                                @Override
                                public void getLastDocumentId(final String docuId) {

                                    //update document id in recently added client

                                    updateDocIdInLastInsertedDocument(docuId, new UpdateDocIdiInLastInsertedDocListener() {
                                        @Override
                                        public void updateDocumentId() {

                                            Toast.makeText(getActivity(), "Docuement Updated Successfully", Toast.LENGTH_LONG).show();

                                        }
                                    });

                                }
                            });

                        }
                    });

                }


            }
        });

        return view;
    }

    private void updateDocIdInLastInsertedDocument(String docuId, final UpdateDocIdiInLastInsertedDocListener updateDocIdiInLastInsertedDocListener) {

        addClientsCollection.document(docuId).update("clientDocId", docuId).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    updateDocIdiInLastInsertedDocListener.updateDocumentId();
                }

            }
        });

    }

    interface GetDocumentIdOfClientListener {
        public void getLastDocumentId(String docuId);
    }

    interface UpdateDocIdiInLastInsertedDocListener {
        public void updateDocumentId();
    }


    private void getDocumentIdAndUpdate(final GetDocumentIdOfClientListener getDocumentIdOfClientListener) {

        addClientsCollection.orderBy("managerUserId", Query.Direction.ASCENDING).limit(1).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                String docuId = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    System.out.println("IamExecuted " + documentSnapshot.getId());

                    docuId = documentSnapshot.getId();
                }



                getDocumentIdOfClientListener.getLastDocumentId(docuId);


            }
        });


    }

    private void addClientsInFireStore(AddClientDTO addClientDTO, final AddClientListener addClientListener) {
        addClientsDocument = addClientsCollection.document();

        addClientsDocument.set(addClientDTO).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    addClientListener.addClient();

                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error " + e.getMessage().toString(), Toast.LENGTH_LONG).show();
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

            String clientName = login_client_name.getText().toString().trim();
            String clientDesi = login_client_designation.getText().toString().trim();
            String clientOrg = login_client_organization.getText().toString().trim();
            String clientMobileNum = login_client_mobile.getText().toString().trim();
            String clientEmail = login_client_email.getText().toString().trim();
            String clientAddr = login_client_address.getText().toString().trim();
            String clientDetails = login_client_details.getText().toString().trim();

            btn_addClient.setEnabled(validateName(clientName) && validateName(clientDesi) && validateName(clientOrg) && validateMobile(clientMobileNum) && validateEmail(clientEmail) && validateName(clientAddr) && validateName(clientDetails));

            if (btn_addClient.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                }
            } else if (!btn_addClient.isEnabled()) {

            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void initFormView(View view) {

        login_client_name = (EditText) view.findViewById(R.id.login_client_name);
        login_client_designation = (EditText) view.findViewById(R.id.login_client_designation);
        login_client_organization = (EditText) view.findViewById(R.id.login_client_organization);
        login_client_mobile = (EditText) view.findViewById(R.id.login_client_mobile);
        login_client_email = (EditText) view.findViewById(R.id.login_client_email);
        login_client_address = (EditText) view.findViewById(R.id.login_client_address);
        login_client_details = (EditText) view.findViewById(R.id.login_client_details);

    }


}
