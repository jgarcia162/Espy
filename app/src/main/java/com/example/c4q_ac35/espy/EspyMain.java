package com.example.c4q_ac35.espy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


public class EspyMain extends ActionBarActivity {
    private static final String CLIENT_ID ="GHO15NRJ1DFJECCEPOPOC555Y1MKI23LPQQZHG04F2AG3FPJ"; //foursquare
    private static String client_Secret = "4CV4XEO03BPPLXSMOFVOB4KG14SSKQYGH20X3VN1RM5RLBRY"; //foursquare
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST = 900;

    EspyMapFragment espyMapFragment;
    MapActivity mapActivity;

    @Bind(R.id.listbt) ImageButton listBt;
    @Bind(R.id.map_button)ImageButton mapBt;
    @Bind(R.id.searchbt) ImageButton searchBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espy_main);
        ButterKnife.bind(this);

        if (!isGooglePlayServicesAvailable()) {
            finish();
            return;
        }

        espyMapFragment = new EspyMapFragment();
        mapActivity = new MapActivity();

        mapBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewMap = new Intent(EspyMain.this,MapActivity.class);
                startActivity(viewMap);
            }
        });

        listBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EspyMain.this, UserInitalSetActivity.class);
                startActivity(intent);
            }
        });

        searchBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                SharedPreferences info;
//                info = EspyMain.this.getSharedPreferences("PREFS_NAME", 0);
//                SharedPreferences.Editor editor = info.edit();
//
//                editor.putString("zipcode", zipCode);

                Intent intent = new Intent(EspyMain.this, SearchResultsActivity.class);
                startActivity(intent);

            }
        });
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

    private boolean isGooglePlayServicesAvailable() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == resultCode) {
            return true;
        } else {
            Toast.makeText(this,"Google Play services is unavailable",Toast.LENGTH_LONG);
            return false;
        }
    }

}
