package com.example.c4q_ac35.espy;

import android.app.IntentService;
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

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by c4q-ac35 on 8/31/15.
 */
public class NotificationsService extends IntentService {
    int WEEKLY_NOTIFICATION_ID = 1;
    protected static final String TAG = "notifications-service";

    public NotificationsService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
                sendWeeklyNotification("Hey try something new this week!");
    }

    private void sendWeeklyNotification(String notificationDetails){
        Intent notificationIntent = new Intent(getApplicationContext(),MyLIst.class);

        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);

        taskStackBuilder.addParentStack(EspyMain.class);

        taskStackBuilder.addNextIntent(notificationIntent);

        PendingIntent notificationPendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.ic_plusone_standard_off_client)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_plusone_tall_off_client))
                .setColor(Color.GREEN)
                .setContentTitle("Get Out And Explore!")
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(WEEKLY_NOTIFICATION_ID, builder.build());
    }
}
