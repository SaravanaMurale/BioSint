package com.nutro.biosint.fragmentemployee;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.nutro.biosint.modelrequest.AddExpenseDTO;
import com.nutro.biosint.utils.MathUtil;
import com.nutro.biosint.utils.PreferenceUtil;

import java.util.Calendar;

import static com.nutro.biosint.utils.MathUtil.validateAmount;
import static com.nutro.biosint.utils.MathUtil.validateName;

public class AddExpenseFragment extends Fragment {

    private EditText login_add_exp_purpose, login_add_expense_details, login_add_expense_amount;
    private TextView addExpenseSelectDate;
    private String userSelectedDate;

    FirebaseFirestore db;
    CollectionReference addEmployeeExpenseCollection;
    DocumentReference addEmployeeExpenseDocument;

    Button btnAddExpense;

    private interface AddExpenseListener {

        public void addExpense();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_expense__deatails_fragment, container, false);

        initFireBase();

        addEmployeeExpenseCollection = db.collection("EmployeeExpense");

        login_add_exp_purpose = (EditText) view.findViewById(R.id.login_add_exp_purpose);
        login_add_expense_details = (EditText) view.findViewById(R.id.login_add_expense_details);
        login_add_expense_amount = (EditText) view.findViewById(R.id.login_add_expense_amount);

        addExpenseSelectDate = (TextView) view.findViewById(R.id.addExpenseSelectDate);

        btnAddExpense=(Button)view.findViewById(R.id.btnAddExpense);

        login_add_exp_purpose.addTextChangedListener(new MyTextWatcher(login_add_exp_purpose));
        login_add_expense_details.addTextChangedListener(new MyTextWatcher(login_add_expense_details));
        login_add_expense_amount.addTextChangedListener(new MyTextWatcher(login_add_expense_amount));

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        String dateAndTime = MathUtil.dateAndTime();

        addExpenseSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        int monthOfYear = month + 1;
                        String date = dayOfMonth + "/" + monthOfYear + "/" + year;
                        userSelectedDate = date;
                        addExpenseSelectDate.setText(date);


                    }
                }, year, month, day);

                datePickerDialog.show();


            }
        });


        return view;
    }

    private void initFireBase() {

        db = FirebaseFirestore.getInstance();
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

            final String purpose = login_add_exp_purpose.getText().toString().trim();
            final String details = login_add_expense_details.getText().toString().trim();
            final String amount = login_add_expense_amount.getText().toString().trim();

            btnAddExpense.setEnabled(validateName(purpose) && validateName(details) && validateAmount(amount));

            if (btnAddExpense.isEnabled()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        btnAddExpense.setBackground(getActivity().getDrawable(R.drawable.background_rectangle_shape));
                        btnAddExpense.setTextColor(getActivity().getApplication().getResources().getColor(R.color.white));

                        btnAddExpense.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                addExpenseInFireStore(purpose, details, amount, new AddExpenseListener() {
                                    @Override
                                    public void addExpense() {

                                        Toast.makeText(getActivity(), "ExpenseAdded", Toast.LENGTH_LONG).show();

                                    }
                                });
                            }
                        });


                    }

                }
            } else if (!btnAddExpense.isEnabled()) {
                btnAddExpense.setEnabled(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btnAddExpense.setBackground(getActivity().getDrawable(R.color.gray));

                }

            }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private void addExpenseInFireStore(String purpose, String details, String amount, final AddExpenseListener addExpenseListener) {

        addEmployeeExpenseDocument = addEmployeeExpenseCollection.document();

        AddExpenseDTO addExpenseDTO = new AddExpenseDTO(PreferenceUtil.getManagerId(getContext()), PreferenceUtil.getEmpUserId(getContext()), purpose, userSelectedDate, details, amount, MathUtil.time());

        addEmployeeExpenseDocument.set(addExpenseDTO).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    addExpenseListener.addExpense();
                }

            }
        });


    }

}
