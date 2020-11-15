package com.nutro.biosint.utils;

import android.content.Context;
import android.provider.Settings;

public class AppConstants {


    public static String getDeviceID(Context context) {
        String deviceID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceID;
    }

    public static final int ADMIN_ROLE=1;
    public static final int EMP_ROLE=2;

    //Ctrl+Alt+O --> Remove unused imports
    public static final int GPS_PROVIDER_CODE = 55;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1256;
    public static final float DEFAULT_ZOOM = 15;
    public static final int CAMERA_ACCESS = 123;

    public static final int SUCCESS_RESULT = 1;
    public static final int FAILURE_RESULT = 0;

    public static final int RESOLVE_HINT = 1;

    public static final String PACKAGE_NAME = "com.google.android.gms.location.sample.locationaddress";

    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = "RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    //WORK IN PROGRESS  STATE
    public static final int WORK_NOT_INIT=-1;
    public static final int WORK_INIT=0;
    public static final int WORK_ASSIGNED=1;
    public static final int WORK_COMPLETED=2;


}
