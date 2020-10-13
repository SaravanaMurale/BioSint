package com.nutro.biosint;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.nutro.biosint.utils.MathUtil;
import com.nutro.biosint.utils.PermissionUtils;

import java.util.Calendar;

import static com.nutro.biosint.utils.AppConstants.LOCATION_PERMISSION_REQUEST_CODE;

public class MainActivity extends AppCompatActivity {

    Button sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendEmail = (Button) findViewById(R.id.sendEmail);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        String dateAndTime = MathUtil.dateAndTime();

        System.out.println("DATENADTIME " + dateAndTime);


        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "saravanamurali24@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "BioSint Username and Password");
                email.putExtra(Intent.EXTRA_TEXT, "123456789");

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client :"));*/

                if (!PermissionUtils.hasPermission(MainActivity.this, Manifest.permission.SEND_SMS)) {
                    //If it is not true(false) it will come inside
                    PermissionUtils.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, LOCATION_PERMISSION_REQUEST_CODE);

                    //Toast.makeText(ViewPagerActivity.this,"called",Toast.LENGTH_LONG).show();

                } else if (PermissionUtils.hasPermission(MainActivity.this, Manifest.permission.SEND_SMS)) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("9123521374", null, "12345678", null, null);
                    Toast.makeText(getApplicationContext(), "Message Sent",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }


}