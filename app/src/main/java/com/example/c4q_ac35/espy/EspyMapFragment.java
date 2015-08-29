package com.example.c4q_ac35.espy;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by c4q-ac35 on 8/12/15.
 */

public class EspyMapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleMap googleMap;
    Location myLocation;
    GoogleApiClient mapGoogleApiClient;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mapGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mapGoogleApiClient.connect();

        googleMap = getMap(); // loads map
        googleMap.setMyLocationEnabled(true); //finds current location

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


        Criteria criteria = new Criteria();


        String provider = locationManager.getBestProvider(criteria, true);

        myLocation = locationManager.getLastKnownLocation(provider);


        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //Choose type of map, normal, terrain, satellite, none

        //Adding a null check
        if(myLocation==null){
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        } else {
            double latitude = myLocation.getLatitude();
            double longitude = myLocation.getLongitude();
            //Geofence.Builder falchiGeofence = new
            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }

        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();

        LatLng latLng = new LatLng(latitude,longitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11)); // choose default zoom of map



        // Calls location service within context

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

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    //TODO method for loading list of "venues"
//    public void loadPlaces(List<Venue> venuesList ){
//
//    }
}
