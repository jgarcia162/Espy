package com.example.c4q_ac35.espy;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c4q-ac35 on 8/16/15.
 */
public class GeofenceTransitionsIntentService extends IntentService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    protected static final String TAG = "geofence-transitions-service";
    public static int GEOFENCE_NOTIFICATION_ID = 0;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    GoogleApiClient mGoogleApiClient;

    public GeofenceTransitionsIntentService(){
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(Places.GEO_DATA_API)
                .addOnConnectionFailedListener(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationManager geoNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        GeofencingEvent geoFenceEvent = GeofencingEvent.fromIntent(intent);
        if (geoFenceEvent.hasError()) {
            String errorMessage = GeofenceErrorMessages.getErrorString(this, geoFenceEvent.getErrorCode());
            return;
        }
        int geofenceTransition = geoFenceEvent.getGeofenceTransition();

        if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER){
            List<Geofence> triggeringGeofences = geoFenceEvent.getTriggeringGeofences();

            String geofenceTransitionDetails = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );

            sendNotification(geofenceTransitionDetails);

        } else if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT){
            geoNotificationManager.cancel(GEOFENCE_NOTIFICATION_ID);
        }
    }

    private String getGeofenceTransitionDetails(
            Context context,
            int geofenceTransition,
            List<Geofence> triggeringGeofences) {

        String geofenceTransitionString = getTransitionString(geofenceTransition);

        // Get the Ids of each geofence that was triggered.
        ArrayList triggeringGeofencesIdsList = new ArrayList();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        String triggeringGeofencesIdsString = TextUtils.join(", ", triggeringGeofencesIdsList);

        if(triggeringGeofencesIdsList.size() == 1)
            return triggeringGeofencesIdsString + " is nearby!";
        else
            return "These places are nearby: " + triggeringGeofencesIdsString;
    }

    private void sendNotification(String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), EspyMain.class);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(EspyMain.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0,PendingIntent.FLAG_ONE_SHOT);

        // Get a notification builder that's compatible with platform versions >= 4
        android.support.v4.app.NotificationCompat.Builder builder = new android.support.v4.app.NotificationCompat.Builder(this);

        // Define the notification settings.
        builder.setSmallIcon(R.mipmap.espy_icon)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.espy_icon))
                .setContentTitle("Check this out!")
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true);


        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify(GEOFENCE_NOTIFICATION_ID, builder.build());
    }

    /**
     * Maps geofence transition types to their human-readable equivalents.
     *
     * @param transitionType    A transition type constant defined in Geofence
     * @return                  A String indicating the type of transition
     */
    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);
            default:
                return getString(R.string.unknown_geofence_transition);
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onResult(Status status) {

    }
}
