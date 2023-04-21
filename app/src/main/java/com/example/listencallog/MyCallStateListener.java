package com.example.listencallog;

import android.os.Build;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.S)
class MyCallStateListener extends TelephonyCallback implements TelephonyCallback.CallStateListener{
    @Override
    public void onCallStateChanged(int state) {
        android.util.Log.d("TAG", "onCallStateChanged: Changed"+state);
        switch (state) {
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.i("[Manager] Phone"," state is off hook");
//                setCallGsmON(true);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.i("[Manager] Phone"," state is ringing");
//                setCallGsmON(true);
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                Log.i("[Manager] Phone"," state is idle");
//                setCallGsmON(false);
                break;
        }
    }
}
