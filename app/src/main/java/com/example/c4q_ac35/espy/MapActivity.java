package com.example.c4q_ac35.espy;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.c4q_ac35.espy.db.FirebaseDatabase;
import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by c4q-ac35 on 8/13/15.
 *
 * FOR TESTING PURPOSES
 */

public class MapActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks{
    GoogleApiClient mGoogleApiClient;
    Button home , geofenceButton;
    Geofence.Builder geofence;
    List<Geofence> mGeofences;
    List<EspyGeofence> mGeofenceList;
    private PendingIntent mGeofencePendingIntent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        home = (Button) findViewById(R.id.home_button);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, EspyMain.class);
                startActivity(intent);
            }
        });

        geofenceButton = (Button) findViewById(R.id.geofence_button);
        createMap();

        Firebase.setAndroidContext(MapActivity.this);
        Firebase myFireBase = new FirebaseDatabase("https://espy.firebaseio.com/");
        GeoFire geoFire = new GeoFire(myFireBase);
        geoFire.setLocation("Rite Aid", new GeoLocation(40.835860, -73.940228));

    }



    @Override
    public void onConnected(Bundle connectionHint) {
        mGeofencePendingIntent = getGeofenceTransitionPendingIntent();

        //Falchi Coordinates
        final double falchiLat = 40.742676;
        final double falchiLong = -73.935182;

        final float geofenceRadius = 100;

        mGeofences = new ArrayList<Geofence>();

        mGeofences.add(new Geofence.Builder().setRequestId("Falchi")
                        .setCircularRegion(falchiLat,falchiLong,geofenceRadius)
                        .setExpirationDuration(Constants.GEOFENCE_EXPIRATION_TIME)
                        .setLoiteringDelay(5000)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
                                | Geofence.GEOFENCE_TRANSITION_DWELL
                                | Geofence.GEOFENCE_TRANSITION_EXIT)
                        .build());

        new GeofencingRequest.Builder().addGeofences(mGeofences);

        mGeofencePendingIntent = getGeofenceTransitionPendingIntent();

        LocationServices.GeofencingApi.addGeofences(mGoogleApiClient, mGeofences, mGeofencePendingIntent);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private PendingIntent getGeofenceTransitionPendingIntent() {
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void createMap(){
        EspyMapFragment espyMapFragment = new EspyMapFragment();
        FragmentManager fm = this.getSupportFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.map_box,espyMapFragment).commit();
    }



//        final EspyGeofence falchi = new EspyGeofence("Falchi",falchiLat,falchiLong,geofenceRadius,Constants.GEOFENCE_EXPIRATION_TIME,Geofence.GEOFENCE_TRANSITION_ENTER|Geofence.GEOFENCE_TRANSITION_EXIT);

    //Build geofences
//        jimbo.toGeofence();
//        rice.toGeofence();
//        falchi.toGeofence();
//
//        //Add geofences to geofence list
//        mGeofenceList.add(rice);
//        mGeofenceList.add(falchi);
//        mGeofenceList.add(jimbo);
//    final double riceLat = 40.721840;
//    final double riceLong = -73.995774;
//
//    //Jimbos
//    final double jimLat = 40.835981;
//    final double jimLon = -73.940245;
//
//    final EspyGeofence rice = new EspyGeofence("Rice To Riches", riceLat, riceLong,geofenceRadius,Constants.GEOFENCE_EXPIRATION_TIME,Geofence.GEOFENCE_TRANSITION_ENTER|Geofence.GEOFENCE_TRANSITION_EXIT);
//
//    final EspyGeofence jimbo = new EspyGeofence("Jimbo",jimLat,jimLon,geofenceRadius,Constants.GEOFENCE_EXPIRATION_TIME,Geofence.GEOFENCE_TRANSITION_ENTER|Geofence.GEOFENCE_TRANSITION_EXIT);
}
