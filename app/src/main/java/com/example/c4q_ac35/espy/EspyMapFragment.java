package com.example.c4q_ac35.espy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.c4q_ac35.espy.foursquare.FourSquareAPI;
import com.example.c4q_ac35.espy.foursquare.Venue;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


/**
 * Created by c4q-ac35 on 8/12/15.
 */


public class EspyMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private static final String TAG = "EspyMapFragment";
    GoogleMap googleMap;
    public static Location myLocation;
    List<Geofence> mGeofenceList;
    FourSquareAPI servicesFoursquare;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getMapAsync(this); // loads map
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true); //finds current location

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        assert locationManager != null;
        String provider = locationManager.getBestProvider(criteria, true);

        myLocation = locationManager.getLastKnownLocation(provider);


        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //Choose type of map, normal, terrain, satellite, none

        //Adding a null check
        if (myLocation != null) {
            double latitude = myLocation.getLatitude();
            double longitude = myLocation.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(15)); // choose default zoom of map
        }

        double lat = 40.722695;
        double lon = -73.996545;
        //Todo: fix map to prompt user to turn on GPS _ high accuracy in LOcation Services
        //Adding a null check
//
//        if(myLocation==null){
//            LocationRequest mLocationRequest = new LocationRequest();
//            mLocationRequest.setInterval(Constants.LOCATION_UPDATE_INTERVAL);
//            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        } else {
//            LocationRequest mLocationRequest = new LocationRequest();
//            mLocationRequest.setInterval(Constants.LOCATION_UPDATE_INTERVAL);
//            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        }
//        googleMap.moveCamera(CameraUpdateFactory.zoomTo(13)); // choose default zoom of map

//        double latitude = myLocation.getLatitude();
//        double longitude = myLocation.getLongitude();
//        LatLng latLng = new LatLng(latitude, longitude);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13)); // choose default zoom of map


        //Set custom icon for markers
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.espy_marker);
        BitmapDescriptor iconMarker = BitmapDescriptorFactory.fromBitmap(icon);

        //Loop for setting markers and geofences for each location in results and or favorites list
        List<Venue> favoriteVenuesList;
        if (FavoritesFragment.venueList != null) {
            favoriteVenuesList = FavoritesFragment.venueList;
        } else {
            favoriteVenuesList = HomeSearchFragment.venueList;
        }
        if (favoriteVenuesList != null && !favoriteVenuesList.isEmpty()) {
            for (Venue venue : favoriteVenuesList) {
                double lati = venue.getLocation().getLat();
                double longi = venue.getLocation().getLng();
                Marker mark = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(lati, longi))
                        .title(venue.getName()));
                mark.setSnippet(" " + venue.getLocation().getFormattedAddress());
                mark.isInfoWindowShown();
                mark.setIcon(iconMarker);

            }
        } else if (favoriteVenuesList == null) {
            Toast.makeText(getActivity().getApplicationContext(), "You don't have any favorites yet!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "You don't have any favorites yet!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
