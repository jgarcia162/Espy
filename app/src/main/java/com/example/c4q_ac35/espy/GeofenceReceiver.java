package com.example.c4q_ac35.espy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by c4q-ac35 on 9/3/15.
 */
public class GeofenceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent = new Intent(context,GeofenceTransitionsIntentService.class);
        context.startService(intent);
    }
}
