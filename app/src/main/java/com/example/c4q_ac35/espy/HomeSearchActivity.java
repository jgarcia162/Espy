package com.example.c4q_ac35.espy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.c4q_ac35.espy.foursquare.FourSquareAPI;
import com.example.c4q_ac35.espy.foursquare.ResponseAPI;
import com.example.c4q_ac35.espy.foursquare.Venue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;


public class HomeSearchActivity extends Fragment
        implements LocationListener, GoogleApiClient.OnConnectionFailedListener, OnStreetViewPanoramaReadyCallback
{

    private String title;
    private int page;
    private VenueAdapter adapter;
    public static final String BASE_API = "https://api.foursquare.com/v2";
    public static final String TAG = "HomeSearchActivity";
    FourSquareAPI servicesFourSquare = null;
    public Venue[] venuee = null;
    private boolean resultsFound = false;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static final long MIN_LOCATION_TIME = DateTimeUtils.ONE_HOUR;

    private static final String LOG_TAG = "HomeSearchActivity";
    private LocationManager locationManager;
    private AutoCompleteTextView mAutocompleteTextView;

    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(40.498425, -74.250219), new LatLng(40.792266, -73.776434));
    private PlacesAdapter mPlaceArrayAdapter;

    public static HomeSearchActivity newInstance(int page, String title) {
        HomeSearchActivity homeSearchActivity = new HomeSearchActivity();
        Bundle args = new Bundle();
        args.putInt("homePage", page);
        args.putString("home", title);
        homeSearchActivity.setArguments(args);
        return homeSearchActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getInt("homePage", 0);
        title = getArguments().getString("home");

        RestAdapter mRestAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog(TAG))
                .setEndpoint(BASE_API).build();

        servicesFourSquare = mRestAdapter.create(FourSquareAPI.class);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mPlaceArrayAdapter = new PlacesAdapter(getActivity(), android.R.layout.simple_list_item_1,
                ((EspyMain)getActivity()).getGoogleApiClient(), BOUNDS_MOUNTAIN_VIEW, null);

        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (locationManager == null) {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        }

        updateFeed();
    }

    private void updateFeed() {

        Location location = getLocation(locationManager, MIN_LOCATION_TIME);

        Log.d(TAG, "Location: " + location);

        if (location != null) {
            Log.d(TAG, "date: " + (System.currentTimeMillis() - location.getTime()));
            updateLocation(location);
        } else {

            // getting GPS status
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        if (isNetworkEnabled)
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
        if (isGPSEnabled)
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAutocompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.et_autocomplete_places);

        mAutocompleteTextView.setThreshold(3);

        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);


        mAutocompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if ( actionId == EditorInfo.IME_ACTION_SEARCH) {

                    performSearch (v.getText().toString(), 10);
                    return true;

                }
                return false;
            }
        });

    }

    private void performSearch(@NonNull String query, int limit) {

            servicesFourSquare.search(query, limit, new FourSquareCallback());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.listView);

        return view;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {

        if (isAdded() && getActivity() != null && !getActivity().isFinishing()) {
            Log.d(TAG, "OnLocationChange" + location);
            Log.d(TAG, "provider: " + location.getProvider());
            updateLocation(location);

            if (LocationManager.GPS_PROVIDER.equals(location.getProvider())) {
                locationManager.removeUpdates(this);

            }
        }

    }

    private void updateLocation(Location location) {
        final String ll = String.format("%g,%g", location.getLatitude(), location.getLongitude());
        servicesFourSquare.getFeed(ll, new FourSquareCallback());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "OnStatusChanged" + status + ", " + provider);

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "OnProviderEnabled" + provider);

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "OnProviderDisabled" + provider);

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(getActivity(),
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }


    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {

        streetViewPanorama.setPosition(new LatLng(-33.87365, 151.20689));

    }

    class FourSquareCallback implements Callback<ResponseAPI> {

        @Override
        public void success(final ResponseAPI responseAPI, Response response) {

            //venuee = new Venue[responseAPI.getResponse().getVenues().size()];

            resultsFound = true;
            //if (adapter == null) {
                List<Venue> venueList = responseAPI.getResponse().getVenues();

                //venuee = venueList.toArray(new Venue[venueList.size()]);
                adapter = new VenueAdapter(getActivity(), venueList);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));

            //}

            Log.d(TAG, "Success");
        }

        @Override
        public void failure(RetrofitError error) {
            Log.d(TAG, "Failure");

        }
    }


    @Nullable public android.location.Location getLocation(LocationManager mLocationManager, long maxAge) {


        android.location.Location location = null;

        // getting GPS status
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        } else {
            // First get location from Network Provider
            if (isNetworkEnabled) {
                Log.d("Network", "Network");
                location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null && (System.currentTimeMillis() - location.getTime()) < maxAge) {
                    return location;
                }
            }
            //get the location by gps
            if (isGPSEnabled) {

                Log.d("GPS Enabled", "GPS Enabled");
                location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null && (System.currentTimeMillis() - location.getTime()) < maxAge) {
                    return location;
                }
            }
        }
        return null;
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            performSearch(parent.getItemAtPosition(position).toString(), 1);

        }

    };

    // Todo: Hide the keyboard
    //Todo: Hide the AutoComplete
    //Todo: The AsynTask if the searching is null "saying that is loading"

}