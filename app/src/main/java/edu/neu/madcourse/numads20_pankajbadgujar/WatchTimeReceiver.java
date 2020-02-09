package edu.neu.madcourse.numads20_pankajbadgujar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class WatchTimeReceiver extends BroadcastReceiver {
    private static final String TAG = "WatchTime";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,R.string.one_minute_alert,Toast.LENGTH_SHORT).show();
        Log.i(TAG, Calendar.getInstance().getTime().toString());
    }
}
