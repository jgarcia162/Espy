package com.example.c4q_ac35.espy;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.c4q_ac35.espy.foursquare.Venue;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EspyMain extends AppCompatActivity implements OnMapReadyCallback {

    private final String TAG = "Espy Main";


    private AlarmManager mAlarmManager;
    private android.support.v7.widget.Toolbar mToolbar;
    private FloatingActionButton mFab;
    TabViewPager viewPager;
    MyPagerAdapter adapterViewPager;
    PendingIntent mNotificationPendingIntent;
    private boolean mGeofencesAdded;
    private SharedPreferences mSharedPreferences;
    private boolean mRequestingLocationUpdates = true;
    Location mCurrentLocation;
    private String mLastUpdateTime;
    private List<Venue> mVenueList;
    private List<Venue> favoritesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        PreferenceManager.setDefaultValues(this, R.xml.user_settings_layout, false);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.app_bar);
//        setSupportActionBar(mToolbar);
        setUpTab();

        if (getIntent().getAction().equals("OPEN_MAP")) {
            viewPager.setCurrentItem(2);
        } else if (getIntent().getAction().equals("OPEN_FAVORITES")) {
            viewPager.setCurrentItem(1);
        }
        //TODO ALARM TO HANDLE WEEKLY NOTIFICATIONS
        setNotificationAlarm();

     //  mFab = (FloatingActionButton) findViewById(R.id.faveBt);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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

    class MyPagerAdapter extends FragmentStatePagerAdapter {
        int num_tabs = 4;

        public MyPagerAdapter(FragmentManager fm, int num_tabs) {
            super(fm);
            this.num_tabs = num_tabs;

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
                    return FavoritesFragment.newInstance(1, "Favorites");
                case 2:
                    return MapActivity.newInstance(2, "Map");
               case 3:
                    return UserFragment.newInstance(3, "User");
                default:
                    return null;
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        NotificationManager pushNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        pushNotificationManager.cancel(GeofenceTransitionsIntentService.PUSH_NOTIFICATION_ID);

        Log.i("Intent Message", "NEW INTENT");
    }

    /**
     * Adds geofences, which sets alerts to be notified when the device enters or exits one of the
     * specified geofences. Handles the success or failure results returned by addGeofences().
     */


    private PendingIntent notificationPendingIntent() {
        if (mNotificationPendingIntent != null) {
            return mNotificationPendingIntent;
        }
        Intent notificationIntent = new Intent(this, NotificationsService.class);
        return PendingIntent.getService(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


//    protected void startLocationUpdates() {
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setInterval(Constants.LOCATION_UPDATE_INTERVAL);
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        LocationListener mLocationListener = new LocationListener() {
//            @Override
//            public void onLocationChanged(Location location) {
//                mCurrentLocation = location;
//                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//                Toast.makeText(getApplicationContext(), mLastUpdateTime, Toast.LENGTH_SHORT).show();
//            }
//        };
//        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);
//    }

    private void setNotificationAlarm() {
        mNotificationPendingIntent = notificationPendingIntent();
//        mAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        //google calender code from current date to 7*24*60*60*1000
//        mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, Calendar.FRIDAY, 20000, mNotificationPendingIntent);

        alarmManager.set(AlarmManager.RTC_WAKEUP,  20000, mNotificationPendingIntent);
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

        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(EspyMain.this, SettingsActivity.class);
                EspyMain.this.startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //ADD TO FAVORITES WHEN BUTTON ON HOLDER IS CLICKED
    public void addToFavorites(View view) {
        mVenueList = HomeSearchActivity.venueList;
        //favoritesList = FavoriteActivity.venueList;
        //TODO FIND HOLDER POSITION
        //view.
//        Venue venue = mVenueList.get(position);
//        favoritesList.add(venue);

        if (favoritesList != null) {
            Toast.makeText(this, "TESTING", Toast.LENGTH_SHORT).show();
        }


    }

}