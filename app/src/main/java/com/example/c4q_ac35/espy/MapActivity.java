package com.example.c4q_ac35.espy;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.c4q_ac35.espy.db.FirebaseDatabase;
import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;

import java.util.List;

/**
 * Created by c4q-ac35 on 8/13/15.
 *
 * FOR TESTING PURPOSES
 */

public class MapActivity extends Fragment {
    Button home;
    GoogleApiClient mGoogleApiClient;
    Geofence.Builder geofence;
    List<Geofence> mGeofenceList;
    PendingIntent mGeofencePendingIntent;
    private String title;
    private int page;

    public static MapActivity newInstance (int page, String title){
        MapActivity mapActivity = new MapActivity();
        Bundle args = new Bundle();
        args.putInt("mapPage", page);
        args.putString("map", title);
        mapActivity.setArguments(args);
        return mapActivity;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_map);

//        home = (Button) findViewById(R.id.home_button);
//        home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MapActivity.this,EspyMain.class);
//                startActivity(intent);
//            }
//        });

        page = getArguments().getInt("mapPage", 2);
        title = getArguments().getString("map");





        //Falchi
        double lat = 40.742729;
        double lon = -73.935181;

        Firebase.setAndroidContext(getActivity());
        Firebase myFireBase = new FirebaseDatabase("https://espy.firebaseio.com/");
        GeoFire geoFire = new GeoFire(myFireBase);
        geoFire.setLocation("Rite Aid", new GeoLocation(40.835860, -73.940228));
//        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(40.835860, -73.940228),.0001);
//
//        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
//            @Override
//            public void onKeyEntered(String key, GeoLocation location) {
//                System.out.println(String.format("Key %s entered the search area at [%f,%f]", key, location.latitude, location.longitude));
//            }
//
//            @Override
//            public void onKeyExited(String key) {
//
//            }
//
//            @Override
//            public void onKeyMoved(String key, GeoLocation location) {
//
//            }
//
//            @Override
//            public void onGeoQueryReady() {
//
//            }
//
//            @Override
//            public void onGeoQueryError(FirebaseError error) {
//
//            }
//        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_map,container,false);
        createMap();
        return view;
    }

    private void createMap(){
        EspyMapFragment espyMapFragment = new EspyMapFragment();
        FragmentManager fm = this.getFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.map_box,espyMapFragment).commit();
    }


}
