package com.example.c4q_ac35.espy;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.c4q_ac35.espy.foursquare.Venue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c4q-ac35 on 8/12/15.
 */

public class EspyMapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{
    GoogleMap googleMap;
    GoogleApiClient mGoogleApiClient;
    Location myLocation;
    List<EspyGeofence> mGeofenceList;
    ArrayList<Location> mListOfLocations;
    float GEOFENCE_RADIUS_IN_METERS = 1000;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        googleMap = getMap(); // loads map
        googleMap.setMyLocationEnabled(true); //finds current location

     

        // Calls location service within context

        double lat = 40.722695;
        double lon = -73.996545;

        //Geofence.Builder falchiGeofence = new

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

       String provider = locationManager.getBestProvider(criteria, true);

        //Falchi Coordinates
        double falchiLat = 40.742676;
        double falchiLong = -73.935182;

        double riceLat = 40.721840;
        double riceLong = -73.995774;

        float geofenceRadius = 100;

        //Create geofence objects
        EspyGeofence falchi = new EspyGeofence("Falchi",falchiLat,falchiLong,geofenceRadius,Constants.GEOFENCE_EXPIRATION_TIME,Geofence.GEOFENCE_TRANSITION_ENTER|Geofence.GEOFENCE_TRANSITION_EXIT);

        EspyGeofence rice = new EspyGeofence("Rice To Riches", riceLat, riceLong,geofenceRadius,Constants.GEOFENCE_EXPIRATION_TIME,Geofence.GEOFENCE_TRANSITION_ENTER|Geofence.GEOFENCE_TRANSITION_EXIT);

        //Build geofences
        rice.toGeofence();
        falchi.toGeofence();

        //Add geofences to geofence list
//        mGeofenceList.add(rice);
//        mGeofenceList.add(falchi);

        //add location marker to map
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(riceLat,riceLong))
                .title("Rice To Riches"));
        marker.setSnippet("Phone Number: (212) 274-0008");
        marker.isInfoWindowShown();

//        //Loop for setting markers and geofences for each location in list
//        for(Location location : mListOfLocations ){
//            double lat = location.getLatitude();
//            double lon = location.getLongitude();
//            EspyGeofence locationFence = new EspyGeofence(location.getName().toString(),lat,lon,geofenceRadius,Constants.GEOFENCE_EXPIRATION_TIME,Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
//            locationFence.toGeofence();
//
//            Marker locationMarker = googleMap.addMarker(new MarkerOptions()
//                    .position(new LatLng(location.getLatitude(),location.getLongitude()))
//                    .title(location.getName()));
//            locationMarker.setSnippet("Phone NUmber: " + location.getPhone().toString());
//            locationMarker.isInfoWindowShown();
//        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        buildGoogleApiClient();

        myLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //Choose type of map, normal, terrain, satellite, none
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();

        LatLng latLng = new LatLng(latitude,longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11)); // choose default zoom of map
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    //TODO method for loading list of "venues"
//    public void loadPlaces(List<Venue> venuesList ){
//
//    }
}
