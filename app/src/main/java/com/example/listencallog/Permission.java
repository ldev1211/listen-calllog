package com.example.listencallog;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Permission {
    private Context context;
    public static final int REQUEST_CODE_MULTIPLE_PERMISSION = 4;
    public static final int REQUEST_CODE_READ_PHONE_STATE = 0;
    public static final int REQUEST_CODE_CONTACT = 1;
    public static final int REQUEST_CODE_BLUETOOTH_CONNECT = 2;
    public static final int REQUEST_CODE_BLUETOOTH = 3;

    public Permission(Context context) {
        this.context = context;
    }

    public boolean checkPermissions(String[] permissions){
        boolean res = true;
        for(String permission: permissions){
            res &= ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
        }
        return res;
    }

    public void requestPermissions(String[] permissions,int requestCode){
        ((AppCompatActivity) context).requestPermissions(permissions,requestCode);
    }

}
