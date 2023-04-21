package com.example.listencallog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private PhoneStateReceiver mReceiver;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Permission.REQUEST_CODE_READ_PHONE_STATE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Đăng ký BroadcastReceiver
                    mReceiver = new PhoneStateReceiver();
                    IntentFilter filter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
                    registerReceiver(mReceiver, filter);
                    Log.d("PhoneStateReceiver", "onRequestPermissionsResult: Registered!");
                }
                break;
            case Permission.REQUEST_CODE_BLUETOOTH:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
        Log.d("PhoneStateReceiver", "onCreate: "+sharedPreferences.getString("backgr","Not"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Permission permission = new Permission(this);
            if(!permission.checkPermissions(new String[]{android.Manifest.permission.READ_PHONE_STATE})){
                permission.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},Permission.REQUEST_CODE_READ_PHONE_STATE);
            } else {
                mReceiver = new PhoneStateReceiver();
                IntentFilter filter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
                registerReceiver(mReceiver, filter);
                Log.d("PhoneStateReceiver", "onRequestPermissionsResult: Registered!");
            }
        } else {
            mReceiver = new PhoneStateReceiver();
            IntentFilter filter = new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
            registerReceiver(mReceiver, filter);
            Log.d("PhoneStateReceiver", "onRequestPermissionsResult: Registered!");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Hủy đăng ký BroadcastReceiver khi activity bị hủy
        unregisterReceiver(mReceiver);
    }
}
