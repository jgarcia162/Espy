package com.example.c4q_ac35.espy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.c4q_ac35.espy.foursquare.FourSquareAPI;
import com.example.c4q_ac35.espy.foursquare.ResponseAPI;
import com.example.c4q_ac35.espy.foursquare.Venue;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by c4q-ac35 on 8/12/15.
 */

public class EspyMapFragment extends SupportMapFragment implements Callback<ResponseAPI> {
    private static final String TAG = "EspyMapFragment";
    GoogleMap googleMap;
   public static Location myLocation;
    List<Geofence> mGeofenceList;
    FourSquareAPI servicesFoursquare;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        googleMap = getMap(); // loads map
        googleMap.setMyLocationEnabled(true); //finds current location

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        String provider = locationManager.getBestProvider(criteria, true);

        if(locationManager.getLastKnownLocation(provider) == null){
            new AlertDialog.Builder(getActivity())
                    .setTitle("No Location")
                    .setMessage("Please turn on your Location")
                    .setIcon(R.mipmap.espy_marker)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent viewIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(viewIntent);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }else{
            myLocation = locationManager.getLastKnownLocation(provider);
        }

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //Choose type of map, normal, terrain, satellite, none

        //Adding a null check
        if(myLocation!=null){
            double latitude = myLocation.getLatitude();
            double longitude = myLocation.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(13)); // choose default zoom of map
        }

        //Set custom icon for markers
        Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.espy_marker);
        BitmapDescriptor iconMarker = BitmapDescriptorFactory.fromBitmap(icon);

        //Loop for setting markers and geofences for each location in results and or favorites list
        List<Venue> favoriteVenuesList;
        if(FavoritesFragment.venueList!= null){
            favoriteVenuesList = FavoritesFragment.venueList;
        }else{
            favoriteVenuesList = HomeSearchFragment.venueList;
        }
        if(favoriteVenuesList != null && !favoriteVenuesList.isEmpty()){
        for(Venue venue: favoriteVenuesList){
            double lati = venue.getLocation().getLat();
            double longi = venue.getLocation().getLng();
            Marker mark = googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lati,longi))
                    .title(venue.getName()));
            mark.setSnippet( " "+ venue.getLocation().getFormattedAddress());
            mark.isInfoWindowShown();
            mark.setIcon(iconMarker);
        }
        }else if(favoriteVenuesList == null){
            Toast.makeText(getActivity().getApplicationContext(),"You don't have any favorites yet!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity().getApplicationContext(),"You don't have any favorites yet!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void success(ResponseAPI responseAPI, Response response) {
//        List<Venue> venueList = responseAPI.getResponse().getVenues();
//
//        for(int i =0;i<venueList.size();i++) {
//            com.example.c4q_ac35.espy.foursquare.Location location = venueList.get(i).getLocation();
//            final double venueLat = location.getLat();
//            final double venueLong = location.getLng();
//
//            Marker locationMarker = googleMap.addMarker(new MarkerOptions()
//                    .position(new LatLng(venueLat, venueLong))
//                    .title(venueList.get(i).getName()));
//            locationMarker.setSnippet("Phone Number: " + venueList.get(i).getContact().getPhone());
//            locationMarker.isInfoWindowShown();
//            Log.i(venueList.get(i).getContact().getPhone(), venueList.get(i).getName());
//        }
    }

    @Override
    public void failure(RetrofitError error) {
        Toast.makeText(getActivity(),"WHOOPS",Toast.LENGTH_SHORT).show();
    }
}
