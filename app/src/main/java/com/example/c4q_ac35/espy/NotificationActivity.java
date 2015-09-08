package com.example.c4q_ac35.espy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

/**
 * Created by c4q-marbella on 8/20/15.
 */
public class NotificationActivity extends AppCompatActivity {
    NotificationManager notificationManager;
    public static final int ID_AUTOCANCEL_NOTIFICATION = 1;
    public static final int ID_SWIPE_NOTIFICATION = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_notification);

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Button autocancelnotification = (Button) findViewById(R.id.cancel_action);


        autocancelnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });


        autocancelnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification2();
            }
        });

    }



    public void showNotification(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("ESPY");
        builder.setContentText("Explore a location nearby");
        // builder.setSmallIcon(R.drawable.logo)
        Intent resultActivity = new Intent(getApplicationContext(),NotificationActivity.class);
        PendingIntent pendingResultActivity =
                PendingIntent.getActivity(getApplicationContext(), 0,resultActivity,PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingResultActivity);
        Notification notification = builder.build();
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        notificationManager.notify(1, notification);
    }

    public void showNotification2(){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Espy");
        builder.setContentText("Explore a location nearby");
       // builder.setSmallIcon(R.drawable.logo)

        Intent resultActivity = new Intent(getApplicationContext(),NotificationActivity.class);
        PendingIntent pendingResultActivity =
                PendingIntent.getActivity(getApplicationContext(), 0,resultActivity,PendingIntent.FLAG_NO_CREATE);
        builder.setContentIntent(pendingResultActivity);
        Notification notification = builder.build();
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        notificationManager.notify(2, notification);
    }



}
