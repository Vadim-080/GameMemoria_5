package com.example.gamememoria;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class ReceiverNapomin extends BroadcastReceiver {
    public ReceiverNapomin() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Test", "RAN");  // Ведет журнал событий

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, IntentServiceNapomin.class));
        } else {
            context.startService(new Intent(context, IntentServiceNapomin.class));
        }
    }
}
