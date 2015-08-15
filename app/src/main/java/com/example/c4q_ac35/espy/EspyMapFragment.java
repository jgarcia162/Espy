package com.example.c4q_ac35.espy;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by c4q-ac35 on 8/12/15.
 */
public class EspyMapFragment extends SupportMapFragment{
    GoogleMap googleMap;
    Location myLocation;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        googleMap = getMap(); // loads map
        googleMap.setMyLocationEnabled(true); //finds current location

        // Calls location service within context
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria,true);
        myLocation = locationManager.getLastKnownLocation(provider);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //Choose type of map, normal, terrain, satellite, none
//        double latitude = myLocation.getLatitude();
//        double longitude = myLocation.getLongitude();
//        LatLng latLng = new LatLng(latitude,longitude);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11)); // choose default zoom of map

        double lat = 40.721857;
        double lon = -73.995956;

        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat,lon))
                .title("Rice To Riches"));
        marker.setSnippet("Phone Number: (212) 274-0008");
        marker.isInfoWindowShown();

    }

    //TODO method for loading list of "venues"
//    public void loadPlaces(List<Venue> venuesList ){
//
//    }
}
