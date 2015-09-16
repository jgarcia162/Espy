package com.example.c4q_ac35.espy;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

/**
 * Created by c4q-ac35 on 8/31/15.
 */
public class NotificationsService extends IntentService {
    public static int WEEKLY_NOTIFICATION_ID = 1;
    protected static final String TAG = "notifications-service";
    protected boolean soundNotification = false;
    protected boolean flashNotification = false;
    public NotificationsService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

//        Toast.makeText(this,"TESTING",Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean nearbyEnabled = sharedPreferences.getBoolean("pref_enablednearby", false);
        boolean weeklyEnabled = sharedPreferences.getBoolean("pref_enabledweekly", false);
        boolean notifVibrate = sharedPreferences.getBoolean("pref_notification_vibrate", false);
        Log.d(TAG, "sharedPreferences:" + nearbyEnabled + weeklyEnabled + notifVibrate);

        soundNotification = sharedPreferences.getBoolean("pref_notification_sound",false);
        flashNotification = sharedPreferences.getBoolean("pref_notification_light",false);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(weeklyEnabled){
            Notification weeklyNotification = sendWeeklyNotification("Hey try something new this week!");
            notificationManager.notify(WEEKLY_NOTIFICATION_ID, weeklyNotification);
        }

    }


    public Notification sendWeeklyNotification(final String notificationDetails){
        Intent notificationIntent = new Intent(getApplicationContext(),EspyMain.class);
        notificationIntent.setAction("OPEN_FAVORITES");

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);

        taskStackBuilder.addParentStack(EspyMain.class);

        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent notificationPendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder = builder.setPriority(NotificationCompat.PRIORITY_LOW)
                .setSmallIcon(R.mipmap.espy_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.espy_icon))
                .setColor(Color.GREEN)
                .setContentTitle("Go out and explore!")
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);

        if(soundNotification){
          builder = builder.setSound(soundUri);
        }

        if(flashNotification){
           builder = builder.setLights(Color.argb(0,Color.BLUE,Color.YELLOW,Color.GREEN),200,200);
        }
            return builder.build();
    }
}
