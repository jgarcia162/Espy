package com.example.c4q_ac35.espy;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.graphics.*;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.c4q_ac35.espy.foursquare.ResponseAPI;
import com.example.c4q_ac35.espy.foursquare.Venue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EspyMain extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ResultCallback<Status> {

    private final String TAG = "Espy Main";
    private static final String CLIENT_ID = "GHO15NRJ1DFJECCEPOPOC555Y1MKI23LPQQZHG04F2AG3FPJ";
    private static String client_Secret = "4CV4XEO03BPPLXSMOFVOB4KG14SSKQYGH20X3VN1RM5RLBRY";
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 900;
    private AlarmManager mAlarmManager;

    private static final String LOG_TAG = "MainActivity";
    private AutoCompleteTextView mAutocompleteTextView;
    private PlacesAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(40.498425, -74.250219), new LatLng(40.792266, -73.776434));
    private GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;

    private MenuItem mSearchAction;
    private android.support.v7.widget.Toolbar mToolbar;
    private FloatingActionButton FAB;
    TabViewPager viewPager;
    MyPagerAdapter adapterViewPager;
    public static ArrayList<Geofence> mGeofenceList = new ArrayList<>();
    PendingIntent mGeofencePendingIntent;
    PendingIntent mNotificationPendingIntent;
    private boolean mGeofencesAdded;
    private SharedPreferences mSharedPreferences;
    private boolean mRequestingLocationUpdates = true;
    Location mCurrentLocation;
    private String mLastUpdateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        if(!mGoogleApiClient.isConnected()){
        mGoogleApiClient.connect();
        } else {

        }

        if(mGeofenceList.isEmpty()){
        // Initially set the PendingIntent used in addGeofences() and removeGeofences() to null.
        mGeofencePendingIntent = null;
        mNotificationPendingIntent = null;

        // Initially set the PendingIntent used in addGeofences() and removeGeofences() to null.
        mGeofencePendingIntent = null;
        // Get the value of mGeofencesAdded from SharedPreferences. Set to false as a default.
        mGeofencesAdded = mSharedPreferences.getBoolean(Constants.GEOFENCES_ADDED_KEY, false);
        }


        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
//        FAB = (FloatingActionButton) findViewById(R.id.fab);
        setUpTab();
//
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                        .setDefaultFontPath("fonts/poiret_one.ttf")
//                        .setFontAttrId(R.attr.fontPath)
//                        .build()
//            );




        if(getIntent().getAction().equals("OPEN_MAP")){
            viewPager.setCurrentItem(2);
        }else if(getIntent().getAction().equals("OPEN_FAVORITES")){
            viewPager.setCurrentItem(1);
        }
        //TODO ALARM TO HANDLE WEEKLY NOTIFICATIONS
        //setNotificationAlarm();
    }

    private void setUpTab() {
        viewPager = (TabViewPager) findViewById(R.id.vpPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.house_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.heart_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.map_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.user_icon));


        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapterViewPager);
//        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                final int width = viewPager.getWidth();
//                if (position == 0) { // represents transition from page 0 to page 1 (horizontal shift)
//                    int translationX = (int) ((-(width - FAB.getWidth()) / 2f) * positionOffset);
//                    FAB.setTranslationX(translationX);
//                    FAB.setTranslationY(0);
//                } else if (position == 1) { // represents transition from page 1 to page 2 (vertical shift)
//                    int translationY = (int) (FAB.getHeight() * positionOffset);
//                    FAB.setTranslationY(translationY);
//                    FAB.setTranslationX(-(width - FAB.getWidth()) / 2f);
//                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    /**
     * Builds and returns a GeofencingRequest. Specifies the list of geofences to be monitored.
     * Also specifies how the geofence notifications are initially triggered.
     */
    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
        // is already inside that geofence.
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

        // Add the geofences to be monitored by geofencing service.
        builder.addGeofences(mGeofenceList);

        // Return a GeofencingRequest.
        return builder.build();
    }

    /**
     * Adds geofences, which sets alerts to be notified when the device enters or exits one of the
     * specified geofences. Handles the success or failure results returned by addGeofences().
     */
    public void addGeofences() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, "Not connected to GoogleApiClient", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    // The GeofenceRequest object.
                    getGeofencingRequest(),
                    // A pending intent that is reused when calling removeGeofences(). This
                    // pending intent is used to generate an intent when a matched geofence
                    // transition is observed.
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult//().
            mGeofencesAdded = mSharedPreferences.getBoolean(Constants.GEOFENCES_ADDED_KEY,true);

        } catch (SecurityException securityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            logSecurityException(securityException);
        }
    }

    /**
     * Removes geofences, which stops further notifications when the device enters or exits
     * previously registered geofences.
     */
    public void removeGeofences() {
        if (!mGoogleApiClient.isConnected()) {
            Toast.makeText(this, "Not connected to GoogleApiClient", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // Remove geofences.
            LocationServices.GeofencingApi.removeGeofences(
                    mGoogleApiClient,
                    // This is the same pending intent that was used in addGeofences().
                    getGeofencePendingIntent()
            ).setResultCallback(this); // Result processed in onResult().
        } catch (SecurityException securityException) {
            logSecurityException(securityException);
        }
    }

    private void logSecurityException(SecurityException securityException) {
        Log.e(TAG, "Invalid location permission. " +
                "You need to use ACCESS_FINE_LOCATION with geofences", securityException);
    }

    /**
     * Runs when the result of calling addGeofences() and removeGeofences() becomes available.
     * Either method can complete successfully or with an error.
     * <p/>
     * Since this activity implements the {@link ResultCallback} interface, we are required to
     * define this method.
     *
     * @param status The Status returned through a PendingIntent when addGeofences() or
     *               removeGeofences() get called.
     */
    public void onResult(Status status) {
        if (status.isSuccess()) {
            // Update state and save in shared preferences.
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(Constants.GEOFENCES_ADDED_KEY, mGeofencesAdded);
            editor.apply();

            // Update the UI. Adding geofences enables the Remove Geofences button, and removing
            // geofences enables the Add Geofences button.

        } else {
            // Get the status code for the error and log it using a user-friendly message.
            String errorMessage = GeofenceErrorMessages.getErrorString(this,
                    status.getStatusCode());
            Log.e(TAG, errorMessage);
        }
    }

    private PendingIntent getGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        } else{
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when calling
        // addGeofences() and removeGeofences().

        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

    }

    private PendingIntent notificationPendingIntent() {
        if (mNotificationPendingIntent != null) {
            return mNotificationPendingIntent;
        }
        Intent notificationIntent = new Intent(this, NotificationsService.class);
        return PendingIntent.getService(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public void populateGeofenceList() {

        final double falchiLat = 40.742676;
        final double falchiLong = -73.935182;

        final float geofenceRadius = 300;
        mGeofenceList.add(new Geofence.Builder()
                .setRequestId("Doughnut Plant") //replace with place.getName()

                        // Set the circular region of this geofence.
                .setCircularRegion(
                        falchiLat, //Replace with place.getLat()
                        falchiLong, // Replace with place.getLong()
                        geofenceRadius
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        final double sevenLat = 40.744878;
        final double sevenLong = -73.934073;
        mGeofenceList.add(new Geofence.Builder()
                .setRequestId("7-11") //replace with place.getName()

                        // Set the circular region of this geofence.
                .setCircularRegion(
                        sevenLat, //Replace with place.getLat()
                        sevenLong, // Replace with place.getLong()
                        geofenceRadius
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        final double jimLat = 40.835837;
        final double jimLong = -73.940200;

        mGeofenceList.add(new Geofence.Builder()
                .setRequestId("Jimbos") //replace with place.getName()

                        // Set the circular region of this geofence.
                .setCircularRegion(
                        jimLat, //Replace with place.getLat()
                        jimLong, // Replace with place.getLong()
                        geofenceRadius
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        final double metLat = 40.740527;
        final double metLong = -73.995740;

        mGeofenceList.add(new Geofence.Builder()
                .setRequestId("Droidcon") //replace with place.getName()

                        // Set the circular region of this geofence.
                .setCircularRegion(
                        metLat, //Replace with place.getLat()
                        metLong, // Replace with place.getLong()
                        geofenceRadius
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());

        final double owoLat = 40.712925;
        final double owoLong = -74.013319;

        mGeofenceList.add(new Geofence.Builder()
                .setRequestId("One World Trade Center") //replace with place.getName()

                        // Set the circular region of this geofence.
                .setCircularRegion(
                        owoLat, //Replace with place.getLat()
                        owoLong, // Replace with place.getLong()
                        geofenceRadius
                )
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());
    }

    protected void startLocationUpdates() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(Constants.LOCATION_UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mCurrentLocation = location;
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                Toast.makeText(getApplicationContext(),mLastUpdateTime,Toast.LENGTH_SHORT).show();
            }
        };
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);
    }

    private void setNotificationAlarm() {
        mNotificationPendingIntent = notificationPendingIntent();

        mAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, Calendar.WEDNESDAY, 10000, mNotificationPendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_espy_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return true;
    }

    @Override
    public void onConnected(Bundle bundle) {
//        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        AsyncTask geofence = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                populateGeofenceList();
                addGeofences();
                return null;
            }
        };

        geofence.execute();

//        if (mRequestingLocationUpdates) {
//            startLocationUpdates();
//        }
        // mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //  mPlaceArrayAdapter.setGoogleApiClient(null);
        if (null != mGeofencePendingIntent) {
            removeGeofences();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {
    int num_tabs = 4;
    Fragment[] mFragments;


    public MyPagerAdapter(FragmentManager fm, int num_tabs) {
        super(fm);
        this.num_tabs = num_tabs;

        mFragments = new Fragment[4];
        mFragments[0] = new HomeSearchActivity();
        mFragments[1] = new FavoriteActivity();
        mFragments[2] = new MapActivity();
        mFragments[3] = new UserActivity();

    }

    @Override
    public int getCount() {
        return num_tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return HomeSearchActivity.newInstance(0, "Home");
            case 1:
                return FavoriteActivity.newInstance(1, "Favorites");
            case 2:
                return MapActivity.newInstance(2, "Map");
            case 3:
                return UserActivity.newInstance(3, "User");
            default:
                return null;
        }
    }

    private int[] imageResId = {
            R.drawable.house_icon,
            R.drawable.heart_icon,
            R.drawable.map_icon,
            R.drawable.user_icon,
    };


    @Override
    public CharSequence getPageTitle(int position) {
        Drawable image = getResources().getDrawable(imageResId[position]);
        assert image != null;
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

}
}