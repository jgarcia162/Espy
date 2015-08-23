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

public class MapActivity extends FragmentActivity {
    Button home;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        home = (Button) findViewById(R.id.home_button);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, EspyMain.class);
                startActivity(intent);
            }
        });

        createMap();

        Firebase.setAndroidContext(MapActivity.this);
        Firebase myFireBase = new FirebaseDatabase("https://espy.firebaseio.com/");
        GeoFire geoFire = new GeoFire(myFireBase);
        geoFire.setLocation("Rite Aid", new GeoLocation(40.835860, -73.940228));
    }

    private void createMap(){
        EspyMapFragment espyMapFragment = new EspyMapFragment();
        FragmentManager fm = this.getSupportFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.map_box,espyMapFragment).commit();
    }
}
