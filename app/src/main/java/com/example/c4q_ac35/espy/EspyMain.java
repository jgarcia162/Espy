package com.example.c4q_ac35.espy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.c4q_ac35.espy.db.MyFavoritesHelper;
import com.example.c4q_ac35.espy.foursquare.Venue;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EspyMain extends AppCompatActivity implements OnMapReadyCallback {

    private final String TAG = "Espy Main";
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 900;
    private static final String LOG_TAG = "MainActivity";

    private MenuItem mSearchAction;
    private Toolbar mToolbar;
    TabViewPager viewPager;
    MyPagerAdapter adapterViewPager;

    PendingIntent mNotificationPendingIntent;
    private boolean mGeofencesAdded;
    private SharedPreferences mSharedPreferences;
    private boolean mRequestingLocationUpdates = true;
    public static Location mCurrentLocation;
    public static String mLastUpdateTime;
    private List<Venue> mVenueList;
    private List<Venue> favoritesList;
    private FloatingActionButton mFab;

    private MyFavoritesHelper databaseHelper = null;

    private MyFavoritesHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, MyFavoritesHelper.class);
        }
        return databaseHelper;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(FavoritesFragment.venueList != null){
            if(!FavoritesFragment.venueList.isEmpty()){
       // initData();
            }
        }
        setContentView(R.layout.activity_home);

        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
//        setSupportActionBar(mToolbar);
//        FAB = (FloatingActionButton) findViewById(R.id.fab);
        setUpTab();

//        mFab = (FloatingActionButton) findViewById(R.id.plus);
//
//        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
//        ImageView heartIcon = new ImageView(this);
//        heartIcon.setImageResource(R.drawable.heart_icon);
//        SubActionButton button1 = itemBuilder.setContentView(heartIcon).build();
//
//        ImageView shareIcon = new ImageView(this);
//        shareIcon.setImageResource(R.drawable.share_icon);
//        SubActionButton button2 = itemBuilder.setContentView(shareIcon).build();
//
//        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
//                                            .addSubActionView(button1)
//                                            .addSubActionView(button2)
//                                            .attachTo(mFab)
//                                            .build();

        if (getIntent().getAction().equals("OPEN_MAP")) {
            viewPager.setCurrentItem(2);
        } else if (getIntent().getAction().equals("OPEN_FAVORITES")) {
            viewPager.setCurrentItem(1);
        }
        //TODO ALARM TO HANDLE WEEKLY NOTIFICATIONS
       // setNotificationAlarm();

        mFab = (FloatingActionButton) findViewById(R.id.faveBt);
    }

    public static void addGeofencesFromFavorites(){
       // EspyApplication.addGeofences();
    }

    private void initData() {
        try {
            for(Venue venue : FavoritesFragment.venueList) {
                Dao<Venue, Integer> venueDao = getHelper().getVenueDao();
                Venue v = venue;
                v.setId(venue.getId());
                v.setName(venue.getName());
                v.setLocation(venue.getLocation());
                venueDao.create(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void setUpTab() {
        viewPager = (TabViewPager) findViewById(R.id.vpPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
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
                adapterViewPager.notifyDataSetChanged();
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

//        private int[] imageResId = {
//                R.drawable.house_icon,
//                R.drawable.heart_icon,
//                R.drawable.map_icon,
//                R.drawable.user_icon,
//        };
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            Drawable image = getResources().getDrawable(imageResId[position]);
//            assert image != null;
//            image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//            SpannableString sb = new SpannableString(" ");
//            ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
//            sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            return sb;
//        }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mGoogleApiClient.connect();
//        populateGeofenceList();
//        addGeofences();
//    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        NotificationManager pushNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        pushNotificationManager.cancel(GeofenceTransitionsIntentService.PUSH_NOTIFICATION_ID);
        pushNotificationManager.cancel(NotificationsService.WEEKLY_NOTIFICATION_ID);

        Log.i("Intent Message", "NEW INTENT");
    }

    private PendingIntent notificationPendingIntent() {
        if (mNotificationPendingIntent != null) {
            return mNotificationPendingIntent;
        }
        Intent notificationIntent = new Intent(this, NotificationsService.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Notification.FLAG_AUTO_CANCEL);
        return PendingIntent.getService(this, Constants.WEEKLY_NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static void startLocationUpdates() {
        LocationRequest mLocationRequest = new LocationRequest();
        //mLocationRequest.setInterval(Constants.LOCATION_UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mCurrentLocation = location;
            }
        };
        LocationServices.FusedLocationApi.requestLocationUpdates(EspyApplication.getsGoogleApiClient(), mLocationRequest, mLocationListener);
    }

//    private void setNotificationAlarm() {
//        mNotificationPendingIntent = notificationPendingIntent();
//
//        mAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        mAlarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, Calendar.WEDNESDAY, 10000, mNotificationPendingIntent);
//    }

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
                Intent settingsIntent = new Intent(EspyMain.this, SettingActivity.class);
                EspyMain.this.startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {
        private final int numTabs;

        public MyPagerAdapter(FragmentManager fm, int numTabs) {
            super(fm);
            this.numTabs = numTabs;
        }

        @Override
        public int getCount() {
            return numTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return HomeSearchFragment.newInstance(0, "Home");
                case 1:
                    return FavoritesFragment.newInstance(1, "Favorites");
                case 2:
                    return MapFragment.newInstance(2, "Map");
                case 3:
                    return UserFragment.newInstance(3, "User");
                default:
                    return null;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}