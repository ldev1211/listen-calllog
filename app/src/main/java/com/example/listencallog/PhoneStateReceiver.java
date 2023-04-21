package com.example.listencallog;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import androidx.core.content.ContextCompat;

public class PhoneStateReceiver extends BroadcastReceiver {
    CallLogListener mCallLogListener;
    TelephonyManager mTelephonyManager;
    CallLogListenerCallBack mCallLogListenerCallback;

    @Override
    public void onReceive(Context context, Intent intent) {
        mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Permission permission = new Permission(context);
            if(permission.checkPermissions(new String[]{Manifest.permission.READ_PHONE_STATE})){
                mCallLogListenerCallback = new CallLogListenerCallBack();
                String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                mTelephonyManager.registerTelephonyCallback(context.getMainExecutor(), mCallLogListenerCallback);
                android.util.Log.d("TAG", "MifoneManager: registed");
            }
        } else {
            mCallLogListener = new CallLogListener(context);
            mTelephonyManager.listen(mCallLogListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
}
