package com.example.listencallog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.S)
class CallLogListenerCallBack extends TelephonyCallback implements TelephonyCallback.CallStateListener{
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
public class CallLogListener extends PhoneStateListener {
    private final Context mContext;

    private static final String TAG = "CallLogListener";
    private final List<CallLogItem> callLogs = new ArrayList<>();
    private boolean isCallInProgress = false;

    public CallLogListener(Context context) {
        mContext = context;
    }

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        super.onCallStateChanged(state, phoneNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.d("[CALL_STATE_IDLE]", phoneNumber);
                if (isCallInProgress) {
                    // Kết thúc cuộc gọi, tổng kết và gửi lên server
                    for (CallLogItem callLogItem : callLogs) {
                        Log.d("[CallLogItem]", callLogItem.getPhone());
                    }
                    // Reset danh sách call logs
                    callLogs.clear();
                    isCallInProgress = false;
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.d("[CALL_STATE_OFFHOOK]", phoneNumber);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.d("[CALL_STATE_RINGING]", phoneNumber);
                if (!isCallInProgress) {
                    // Bắt đầu cuộc gọi, lấy log cuộc gọi
                    CallLogItem callLogItem = getCallDetails(phoneNumber);
                    callLogs.add(callLogItem);
                    isCallInProgress = true;
                }
                break;
            default:
                break;
        }
    }

    private CallLogItem getCallDetails(String phoneNumber) {
        CallLogItem callLogItem = null;
        String[] projection = new String[]{
                CallLog.Calls.DATE,
                CallLog.Calls.TYPE,
                CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION};
        String selection = CallLog.Calls.NUMBER + " = ?";

        String[] selectionArgs = new String[]{phoneNumber};
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, selection, selectionArgs, CallLog.Calls.DATE + " DESC LIMIT 1");
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                @SuppressLint("Range") long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                callLogItem = new CallLogItem(number, duration, type, date, date);
            }
        } catch (SecurityException e) {
            Log.e(TAG, "getCallDetails: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return callLogItem;
    }

}
