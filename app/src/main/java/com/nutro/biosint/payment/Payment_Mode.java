package com.nutro.biosint.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nutro.biosint.R;
import com.nutro.biosint.manageractivity.DrawerActivityManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Payment_Mode extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {

    public static EditText getUserCount;
    public static TextView total_Amt;
    public static Button money_Payment;
    int amt=0;
    String email = "";
    String mobile_Num = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__mode);

        Intent intent = getIntent();
        email = intent.getStringExtra("EMAIL");
        mobile_Num = intent.getStringExtra("MOBILENUMBER");

        Log.d("EmailAndUserId" , email+mobile_Num);

        getUserCount = findViewById(R.id.getUserCount);
        total_Amt = findViewById(R.id.total_Amt);
        money_Payment = findViewById(R.id.money_Payment);
        money_Payment.setOnClickListener(this);

        Checkout.preload(getApplicationContext());
        getUserCount.addTextChangedListener(new GenericTextWatcher(getUserCount));

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.money_Payment:
                startPayment();
                break;

        }
    }

    @Override
    public void onPaymentSuccess(String s) {

        Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(Payment_Mode.this, DrawerActivityManager.class));
        finish();

    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(this, "Error msg : /n"+ s, Toast.LENGTH_SHORT).show();

        Log.d(getString(R.string.payment_Error),"ERROR:" + s);

    }

    public class GenericTextWatcher implements TextWatcher {

        private View view;

        public GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {

            try {
                String text = editable.toString();

                Log.d("TAG", " After Chang text:" + text + ""+view.getId());

                if(editable == getUserCount.getEditableText()){

                   amt  =   getCount(Integer.parseInt(text));
                    total_Amt.setText("Total Amount : "+ amt);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public int getCount(int l){

        return (l * 99);

    }

    public void startPayment() {

        Checkout checkout = new Checkout();

        checkout.setKeyID("rzp_test_EFYJf29T1UAjUx");

        checkout.setImage(R.mipmap.ic_launcher);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", email.split("@")[0]);
            options.put("description", "Test");
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            if(!(amt==0)) {
                        int paise = amt *100;
                options.put("amount", paise);//pass amount in currency subunits
            }
            options.put("prefill.email", email);
            options.put("prefill.contact",mobile_Num);
            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(getString(R.string.payment_Error), "Error in starting Razorpay Checkout", e);
        }
    }
}
