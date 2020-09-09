package com.nutro.biosint.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    static Context sContext;
    static ToastUtils toastUtils;

    public ToastUtils() {
    }


    public static ToastUtils getInstance(Context context){

        if (toastUtils == null) {
            toastUtils = new ToastUtils();
            sContext = context;
        }
        return toastUtils;


    }

    public void showLongToast(String message){

        Toast.makeText(sContext,message,Toast.LENGTH_LONG).show();

    }

    public void showShortToast(String message){
        Toast.makeText(sContext,message,Toast.LENGTH_SHORT).show();
    }
}
