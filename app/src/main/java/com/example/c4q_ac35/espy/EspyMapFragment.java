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
public class EspyMapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks , GoogleApiClient.OnConnectionFailedListener{
    GoogleMap googleMap;
    GoogleApiClient mGoogleApiClient;
    Location myLocation;
    List<Geofence> mGeofenceList;
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

        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lon))
                .title("Rice To Riches"));
        marker.setSnippet("Phone Number: (212) 274-0008");
        marker.isInfoWindowShown();

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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    //TODO method for loading list of "venues"
//    public void loadPlaces(List<Venue> venuesList ){
//
//    }
}
