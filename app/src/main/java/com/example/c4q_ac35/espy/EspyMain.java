package com.example.c4q_ac35.espy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class EspyMain extends ActionBarActivity {
    private static final String CLIENT_ID ="GHO15NRJ1DFJECCEPOPOC555Y1MKI23LPQQZHG04F2AG3FPJ";

    private static String client_Secret = "4CV4XEO03BPPLXSMOFVOB4KG14SSKQYGH20X3VN1RM5RLBRY";

    EspyMapFragment espyMapFragment;
    MapActivity mapActivity;
    Button mapButton;
    private ImageButton mButtonSearch;
    private EditText mEditTextZipCode;
    String zipCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espy_main);

        espyMapFragment = new EspyMapFragment();
        mapActivity = new MapActivity();
        mapButton = (Button) findViewById(R.id.map_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewMap = new Intent(EspyMain.this,MapActivity.class);
                startActivity(viewMap);
            }
        });
        mButtonSearch = (ImageButton) findViewById(R.id.searchbt);
        mEditTextZipCode = (EditText) findViewById(R.id.zipcode_final);

        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                zipCode = mEditTextZipCode.getText().toString();

                SharedPreferences info;
                info = EspyMain.this.getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor editor = info.edit();

                editor.putString("zipcode", zipCode);

                Intent intent = new Intent(EspyMain.this, SearchResultsActivity.class);
                intent.putExtra("zipcode", zipCode);
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
}
