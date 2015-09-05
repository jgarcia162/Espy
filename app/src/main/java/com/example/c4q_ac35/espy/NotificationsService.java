package com.example.c4q_ac35.espy;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by c4q-ac35 on 8/31/15.
 */
public class NotificationsService extends IntentService {
    public static int WEEKLY_NOTIFICATION_ID = 1;
    protected static final String TAG = "notifications-service";

    public NotificationsService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Toast.makeText(this,"TESTING",Toast.LENGTH_SHORT).show();
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification weeklyNotification = sendWeeklyNotification("Hey try something new this week!");
//        notificationManager.notify(WEEKLY_NOTIFICATION_ID, weeklyNotification);
    }

    private Notification sendWeeklyNotification(String notificationDetails){
        Intent notificationIntent = new Intent(getApplicationContext(),EspyMain.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);

        taskStackBuilder.addParentStack(EspyMain.class);

        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent notificationPendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setPriority(NotificationCompat.PRIORITY_LOW)
                .setSmallIcon(R.mipmap.espy_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.espy_icon))
                .setColor(Color.GREEN)
                .setContentTitle("Get Out And Explore!")
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);

            return builder.build();
    }
}
