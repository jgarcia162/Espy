package com.example.c4q_ac35.espy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class EspyMain extends FragmentActivity {
    private static final String CLIENT_ID ="GHO15NRJ1DFJECCEPOPOC555Y1MKI23LPQQZHG04F2AG3FPJ";
    private static String client_Secret = "4CV4XEO03BPPLXSMOFVOB4KG14SSKQYGH20X3VN1RM5RLBRY";
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 900;
    FragmentPagerAdapter adapterViewPager;
    EspyMapFragment espyMapFragment;
    MapActivity mapActivity;

//    @Bind(R.id.listbt) ImageButton listBt;
//    @Bind(R.id.map_button)ImageButton mapBt;
//    @Bind(R.id.searchbt) ImageButton searchBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_back);
//        ButterKnife.bind(this);

        //TODO;
        ViewPager viewPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        viewPager.getCurrentItem();
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getApplicationContext(), "Selected page position: " + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        // espyMapFragment = new EspyMapFragment();
        //mapActivity = new MapActivity();
//
//        mapBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Intent viewMap = new Intent(EspyMain.this,MapActivity.class);
//                //startActivity(viewMap);
//            }
//        });
//
//        listBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(EspyMain.this, UserInitalSetActivity.class);
//                startActivity(intent);
//            }
//        });

//        searchBt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
////                SharedPreferences info;
////                info = EspyMain.this.getSharedPreferences("PREFS_NAME", 0);
////                SharedPreferences.Editor editor = info.edit();
////
////                editor.putString("zipcode", zipCode);
//
//                Intent intent = new Intent(EspyMain.this, SearchResultsActivity.class);
//                startActivity(intent);
//
//            }
//        });

    }

    class MyPagerAdapter extends FragmentPagerAdapter{
        private int NUM_ITEMS = 3;

        public MyPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
           switch(position){
               case 0:
                   return MyList.newInstance(0, "Page # 1");
               case 1:
                   return SearchResultsActivity.newInstance(1,"Page # 2");
               case 2:
                   return MapActivity.newInstance(2, "Page # 3");
               default:
                   return null;
           }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

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
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent( EspyMain.this, SettingActivity.class);
            EspyMain.this.startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
